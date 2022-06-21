package {{ cookiecutter.basePackage }}.biz.auth.controller;

import {{ cookiecutter.basePackage }}.biz.auth.service.IPermissionService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import {{ cookiecutter.basePackage }}.biz.auth.entity.UserPermission;
import {{ cookiecutter.basePackage }}.biz.auth.service.IUserPermissionService;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.common.response.PageVO;
import {{ cookiecutter.basePackage }}.common.util.PageRequestUtil;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 用户-权限关联
 */
@RestController
@RequestMapping("/auth")
public class UserPermissionController {

    IUserPermissionService thisService;

    IPermissionService permissionService;

    public UserPermissionController(IUserPermissionService thisService, IPermissionService permissionService) {
        this.thisService = thisService;
        this.permissionService = permissionService;
    }

    /**
     * 分页查询用户权限
     *
     * @param userId 用户ID
     */
    @Secured(value = "ROLE_admin")
    @GetMapping("/users/{userId}/permissions")
    public ApiResponse<PageVO<UserPermission>> list(@Valid PaginationRequest request, @NotNull @PathVariable Long userId) {
        IPage<UserPermission> page = PageRequestUtil.checkForMp(request);
        IPage<UserPermission> list = thisService.pageRelation(userId, 0L, page);
        return PageRequestUtil.extractFromMp(list);
    }


    /**
     * 用户新增权限
     *
     * @param userId 用户ID
     */
    @Secured(value = "ROLE_admin")
    @PostMapping("/users/{userId}/permissions")
    public ApiResponse<UserPermission> add(@NotNull @PathVariable Long userId, @Valid @RequestBody UserPermission entity) {
        Long permissionId = entity.getPermissionId();
        if (permissionId != null) {
            boolean success = thisService.createRelation(userId, permissionId);
            if (success) {
                permissionService.refreshPermissions(userId);
                return new ApiResponse<>("新增成功", true);
            }
        }
        return new ApiResponse<>("新增失败", false);
    }

    /**
     * 删除用户权限
     *
     * @param userId 用户ID
     * @param permissionId 权限ID
     */
    @Secured(value = "ROLE_admin")
    @DeleteMapping("/users/{userId}/permissions/{permissionId}")
    public ApiResponse<Object> delete(@NotNull @PathVariable Long userId, @NotNull @PathVariable Long permissionId) {
        boolean success = thisService.deleteRelation(userId, permissionId);
        if (success) {
            permissionService.refreshPermissions(userId);
            return new ApiResponse<>("删除成功");
        }
        return new ApiResponse<>("删除失败", false);
    }

}
