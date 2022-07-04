package {{ cookiecutter.basePackage }}.biz.auth.controller;

import {{ cookiecutter.basePackage }}.biz.auth.entity.Permission;
import {{ cookiecutter.basePackage }}.biz.auth.service.IPermissionService;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 权限信息
 */
@RestController
@RequestMapping("/auth")
public class PermissionController extends AuthBaseController {

    IPermissionService thisService;

    public PermissionController(IPermissionService thisService) {
        this.thisService = thisService;
    }

    /**
     * 查询用户拥有所有权限信息
     */
    @GetMapping("/permissions")
    @PreAuthorize(value = "ck.hasPermit('auth:permissions:list')")
    public ApiResponse<List<String>> list() {
        List<String> permissions = thisService.getAllPermissions(getUserId());
        return new ApiResponse<>(permissions);
    }


    /**
     * 新增权限
     */
    @PostMapping("/permissions")
    @PreAuthorize(value = "ck.hasPermit('auth:permissions:add')")
    public ApiResponse<Permission> add(@Valid @RequestBody Permission entity) {
        boolean success = thisService.save(entity);
        if (success) {
            return new ApiResponse<>(entity);
        }
        return new ApiResponse<>();
    }


    /**
     * 按ID查询权限
     *
     * @param permissionId 权限ID
     */
    @GetMapping("/permissions/{permissionId}")
    @PreAuthorize(value = "ck.hasPermit('auth:permissions:get')")
    public ApiResponse<Permission> get(@NotNull @PathVariable Long permissionId) {
        return new ApiResponse<>(thisService.getById(permissionId));
    }

    /**
     * 按ID更新权限
     *
     * @param permissionId 权限ID
     */
    @PutMapping("/permissions/{permissionId}")
    @PreAuthorize(value = "ck.hasPermit('auth:permissions:update')")
    public ApiResponse<Object> update(@Valid @RequestBody Permission entity, @NotNull @PathVariable Long permissionId) {
        Permission permission = thisService.getById(permissionId);
        entity.setId(permissionId);
        boolean success = thisService.updateById(entity);
        if (success) {
            List<Long> users = thisService.holdPermission(permission.getCode());
            users.forEach(userId -> thisService.refreshPermissions(userId));
            return new ApiResponse<>("更新成功");
        }
        return new ApiResponse<>("更新失败");
    }

    /**
     * 按ID删除权限
     *
     * @param permissionId 权限ID
     */
    @DeleteMapping("/permissions/{permissionId}")
    @PreAuthorize(value = "ck.hasPermit('auth:permissions:delete')")
    public ApiResponse<Object> delete(@NotNull @PathVariable Long permissionId) {
        Permission permission = thisService.getById(permissionId);
        boolean success = thisService.delete(permissionId);
        if (success) {
            List<Long> users = thisService.holdPermission(permission.getCode());
            users.forEach(userId -> thisService.refreshPermissions(userId));
            return new ApiResponse<>("删除成功");
        }
        return new ApiResponse<>("删除失败");
    }

}
