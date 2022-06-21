package {{ cookiecutter.basePackage }}.biz.auth.controller;

import {{ cookiecutter.basePackage }}.biz.auth.service.IPermissionService;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import com.baomidou.mybatisplus.core.metadata.IPage;
import {{ cookiecutter.basePackage }}.biz.auth.entity.GroupPermission;
import {{ cookiecutter.basePackage }}.biz.auth.service.IGroupPermissionService;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.common.response.PageVO;
import {{ cookiecutter.basePackage }}.common.util.PageRequestUtil;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户组权限
 */
@RestController
@RequestMapping("/auth")
public class GroupPermissionController extends AuthBaseController {

    IGroupPermissionService thisService;

    IPermissionService permissionService;

    public GroupPermissionController(IGroupPermissionService thisService, IPermissionService permissionService) {
        this.thisService = thisService;
        this.permissionService = permissionService;
    }

    /**
     * 分页查询用户组权限
     *
     * @param groupId 用户组ID
     */
    @Secured(value = "ROLE_admin")
    @GetMapping("/groups/{groupId}/permissions")
    public ApiResponse<PageVO<GroupPermission>> list(@Valid PaginationRequest request, @PathVariable Long groupId) {
        IPage<GroupPermission> page = PageRequestUtil.checkForMp(request);
        IPage<GroupPermission> list = thisService.pageRelation(groupId, 0L, page);
        return PageRequestUtil.extractFromMp(list);
    }


    /**
     * 用户组新增权限
     *
     * @param groupId 用户组ID
     */
    @Secured(value = "ROLE_admin")
    @PostMapping("/groups/{groupId}/permissions")
    public ApiResponse<GroupPermission> add(@PathVariable Long groupId, @Valid @RequestBody GroupPermission entity) {
        Long permissionId = entity.getPermissionId();
        if (permissionId != null) {
            boolean success = thisService.createRelation(groupId, permissionId);
            if (success) {
                List<Long> users = getUsersByGroup(groupId);
                users.forEach(userId -> permissionService.refreshPermissions(userId));
                return new ApiResponse<>("新增成功", true);
            }
        }
        return new ApiResponse<>("新增失败", false);
    }

    /**
     * 删除用户组权限
     *
     * @param permissionId 权限ID
     * @param groupId      用户组ID
     */
    @Secured(value = "ROLE_admin")
    @DeleteMapping("/groups/{groupId}/permissions/{permissionId}")
    public ApiResponse<Object> delete(@PathVariable Long permissionId, @PathVariable Long groupId) {
        boolean success = thisService.deleteRelation(groupId, permissionId);
        if (success) {
            List<Long> users = getUsersByGroup(groupId);
            users.forEach(userId -> permissionService.refreshPermissions(userId));
            return new ApiResponse<>("删除成功");
        }
        return new ApiResponse<>("删除失败", false);
    }

}
