package {{ cookiecutter.basePackage }}.biz.auth.controller;

import {{ cookiecutter.basePackage }}.biz.auth.service.IPermissionService;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import com.baomidou.mybatisplus.core.metadata.IPage;
import {{ cookiecutter.basePackage }}.biz.auth.entity.RolePermission;
import {{ cookiecutter.basePackage }}.biz.auth.service.IRolePermissionService;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.common.response.PageVO;
import {{ cookiecutter.basePackage }}.common.util.PageRequestUtil;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色权限信息
 */
@RestController
@RequestMapping("/auth")
public class RolePermissionController extends AuthBaseController {

    IRolePermissionService thisService;

    IPermissionService permissionService;

    public RolePermissionController(IRolePermissionService thisService, IPermissionService permissionService) {
        this.thisService = thisService;
        this.permissionService = permissionService;
    }

    /**
     * 分页查询角色权限
     *
     * @param roleId 角色ID
     */
    @Secured(value = "ROLE_admin")
    @GetMapping("/roles/{roleId}/permissions")
    public ApiResponse<PageVO<RolePermission>> list(@Valid PaginationRequest request, @NotNull @PathVariable Long roleId) {
        IPage<RolePermission> page = PageRequestUtil.checkForMp(request);
        IPage<RolePermission> list = thisService.pageRelation(roleId, 0L, page);
        return PageRequestUtil.extractFromMp(list);
    }


    /**
     * 角色新增权限
     *
     * @param roleId 角色ID
     */
    @Secured(value = "ROLE_admin")
    @PostMapping("/roles/{roleId}/permissions")
    public ApiResponse<RolePermission> add(@NotNull @PathVariable Long roleId, @Valid @RequestBody RolePermission entity) {
        Long permissionId = entity.getPermissionId();
        if (permissionId != null) {
            boolean success = thisService.createRelation(roleId, permissionId);
            if (success) {
                List<Long> users = getUsersByRole(roleId);
                users.forEach(userId -> permissionService.refreshPermissions(userId));
                return new ApiResponse<>("新增成功", true);
            }
        }
        return new ApiResponse<>("新增失败", false);
    }

    /**
     * 删除角色权限
     *
     * @param permissionId 权限ID
     * @param roleId       角色ID
     */
    @Secured(value = "ROLE_admin")
    @DeleteMapping("/roles/{roleId}/permissions/{permissionId}")
    public ApiResponse<Object> delete(@NotNull @PathVariable Long permissionId, @NotNull @PathVariable Long roleId) {
        boolean success = thisService.deleteRelation(roleId, permissionId);
        if (success) {
            List<Long> users = getUsersByRole(roleId);
            users.forEach(userId -> permissionService.refreshPermissions(userId));
            return new ApiResponse<>("删除成功");
        }
        return new ApiResponse<>("删除失败", false);
    }

}
