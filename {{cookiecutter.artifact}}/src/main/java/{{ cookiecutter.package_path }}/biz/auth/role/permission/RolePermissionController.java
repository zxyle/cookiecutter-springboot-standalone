package {{ cookiecutter.basePackage }}.biz.auth.role.permission;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.biz.auth.permission.Permission;
import {{ cookiecutter.basePackage }}.biz.auth.permission.PermissionService;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.request.BatchRequest;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * 角色与权限关系管理
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class RolePermissionController extends AuthBaseController {

    final RolePermissionService thisService;
    final PermissionService permissionService;
    final RolePermissionMapper thisMapper;

    /**
     * 分页查询角色拥有的权限
     *
     * @param roleId 角色ID
     */
    @PreAuthorize("@ck.hasPermit('auth:role:get')")
    @GetMapping("/roles/{roleId}/permissions")
    public R<Page<Permission>> list(@Valid PaginationRequest req, @PathVariable Integer roleId) {
        Page<Permission> page = thisMapper.page(req.getPage(), roleId, req);
        return R.ok(page);
    }

    /**
     * 角色新增权限
     *
     * @param roleId 角色ID
     */
    @PreAuthorize("@ck.hasPermit('auth:role:update')")
    @PostMapping("/roles/{roleId}/permissions")
    public R<Void> add(@PathVariable Integer roleId, @Valid @RequestBody RolePermission entity) {
        boolean success = thisService.createRelation(roleId, entity.getPermissionId());
        return success? R.ok("角色新增权限成功") : R.fail("角色新增权限失败");
    }

    /**
     * 角色移除权限
     *
     * @param permissionId 权限ID
     * @param roleId       角色ID
     */
    @PreAuthorize("@ck.hasPermit('auth:role:update')")
    @DeleteMapping("/roles/{roleId}/permissions/{permissionId}")
    public R<Void> delete(@PathVariable Integer permissionId, @PathVariable Integer roleId) {
        boolean success = thisService.deleteRelation(roleId, permissionId);
        return success ? R.ok("角色移除权限成功") : R.fail("角色移除权限失败");
    }

    /**
     * 批量移除角色拥有的权限
     *
     * @param roleId 角色ID
     */
    @PreAuthorize("@ck.hasPermit('auth:role:update')")
    @DeleteMapping("/roles/{roleId}/permissions")
    public R<Void> deleteBatch(@PathVariable Integer roleId, @Valid BatchRequest req) {
        Set<Integer> ids = req.getIds();
        boolean success = ids.stream()
                .allMatch(permissionId -> thisService.deleteRelation(roleId, permissionId));

        return success ? R.ok("批量移除角色拥有的权限成功") : R.fail("批量移除角色拥有的权限失败");
    }

    /**
     * 用户组批量新增角色
     *
     * @param roleId 角色ID
     */
    @PreAuthorize("@ck.hasPermit('auth:role:update')")
    @PostMapping("/roles/{roleId}/permissions/batch-add")
    public R<Void> createBatch(@PathVariable Integer roleId, @Valid @RequestBody BatchRequest req) {
        Set<Integer> ids = req.getIds();
        boolean success = ids.stream()
                .allMatch(permissionId -> thisService.createRelation(roleId, permissionId));

        return success ? R.ok("批量新增用户组拥有的角色成功") : R.fail("批量新增用户组拥有的角色失败");
    }

}
