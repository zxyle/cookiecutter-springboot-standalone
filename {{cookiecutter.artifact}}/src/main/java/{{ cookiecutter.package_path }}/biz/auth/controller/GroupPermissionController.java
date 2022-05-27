package {{ cookiecutter.basePackage }}.biz.auth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import {{ cookiecutter.basePackage }}.biz.auth.entity.GroupPermission;
import {{ cookiecutter.basePackage }}.biz.auth.service.IGroupPermissionService;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.common.response.PageVO;
import {{ cookiecutter.basePackage }}.common.util.PageRequestUtil;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户组权限
 */
@RestController
@RequestMapping("/auth")
public class GroupPermissionController {

    IGroupPermissionService thisService;

    public GroupPermissionController(IGroupPermissionService thisService) {
        this.thisService = thisService;
    }

    /**
     * 分页查询用户组权限
     */
    @GetMapping("/groups/{groupId}/permissions")
    public ApiResponse<PageVO<GroupPermission>> list(@Valid PaginationRequest request, @PathVariable Long groupId) {
        IPage<GroupPermission> page = PageRequestUtil.checkForMp(request);
        IPage<GroupPermission> list = thisService.pageRelation(groupId, 0L, page);
        return PageRequestUtil.extractFromMp(list);
    }


    /**
     * 用户组新增权限
     */
    @PostMapping("/groups/{groupId}/permissions")
    public ApiResponse<GroupPermission> add(@PathVariable Long groupId, @Valid @RequestBody GroupPermission entity) {
        Long permissionId = entity.getPermissionId();
        if (permissionId != null) {
            boolean success = thisService.createRelation(groupId, permissionId);
            if (success) {
                return new ApiResponse<>("新增成功", true);
            }
        }
        return new ApiResponse<>("新增失败", false);
    }

    /**
     * 删除用户组权限
     */
    @DeleteMapping("/groups/{groupId}/permissions/{permissionId}")
    public ApiResponse<Object> delete(@PathVariable Long permissionId, @PathVariable Long groupId) {
        boolean success = thisService.deleteRelation(groupId, permissionId);
        if (success) {
            return new ApiResponse<>("删除成功");
        }
        return new ApiResponse<>("删除失败", false);
    }

}
