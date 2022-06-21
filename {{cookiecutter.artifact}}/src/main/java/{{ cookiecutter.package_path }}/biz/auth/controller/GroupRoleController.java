package {{ cookiecutter.basePackage }}.biz.auth.controller;

import {{ cookiecutter.basePackage }}.biz.auth.service.IPermissionService;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import com.baomidou.mybatisplus.core.metadata.IPage;
import {{ cookiecutter.basePackage }}.biz.auth.entity.GroupRole;
import {{ cookiecutter.basePackage }}.biz.auth.service.IGroupRoleService;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.common.response.PageVO;
import {{ cookiecutter.basePackage }}.common.util.PageRequestUtil;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 组角色关联
 */
@RestController
@RequestMapping("/auth")
public class GroupRoleController extends AuthBaseController {

    IGroupRoleService thisService;

    IPermissionService permissionService;

    public GroupRoleController(IGroupRoleService thisService, IPermissionService permissionService) {
        this.thisService = thisService;
        this.permissionService = permissionService;
    }

    /**
     * 分页查询用户组角色
     *
     * @param groupId 用户组ID
     */
    @Secured(value = "ROLE_admin")
    @GetMapping("/groups/{groupId}/roles")
    public ApiResponse<PageVO<GroupRole>> list(@PathVariable Long groupId, @Valid PaginationRequest request) {
        IPage<GroupRole> page = PageRequestUtil.checkForMp(request);
        IPage<GroupRole> list = thisService.pageRelation(groupId, 0L, page);
        // TODO 应该查询的是角色
        return PageRequestUtil.extractFromMp(list);
    }


    /**
     * 用户组新增角色
     *
     * @param groupId 用户组ID
     */
    @Secured(value = "ROLE_admin")
    @PostMapping("/groups/{groupId}/roles")
    public ApiResponse<GroupRole> add(@PathVariable Long groupId, @Valid @RequestBody GroupRole entity) {
        Long roleId = entity.getRoleId();
        if (roleId != null) {
            boolean success = thisService.createRelation(groupId, roleId);
            if (success) {
                List<Long> users = getUsersByGroup(groupId);
                users.forEach(userId -> permissionService.refreshPermissions(userId));
                return new ApiResponse<>("新增成功", true);
            }
        }
        return new ApiResponse<>("新增失败", false);
    }

    /**
     * 用户组删除角色
     *
     * @param groupId 用户组ID
     * @param roleId 角色ID
     */
    @Secured(value = "ROLE_admin")
    @DeleteMapping("/groups/{groupId}/roles/{roleId}")
    public ApiResponse<Object> delete(@PathVariable Long groupId, @PathVariable Long roleId) {
        boolean success = thisService.deleteRelation(groupId, roleId);
        if (success) {
            List<Long> users = getUsersByGroup(groupId);
            users.forEach(userId -> permissionService.refreshPermissions(userId));
            return new ApiResponse<>("删除成功");
        }
        return new ApiResponse<>("删除失败", false);
    }

}
