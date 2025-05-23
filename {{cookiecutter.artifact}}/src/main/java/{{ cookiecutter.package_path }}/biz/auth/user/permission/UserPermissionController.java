package {{ cookiecutter.basePackage }}.biz.auth.user.permission;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.biz.auth.permission.Permission;
import {{ cookiecutter.basePackage }}.biz.auth.permission.PermissionService;
import {{ cookiecutter.basePackage }}.common.request.BatchRequest;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.validation.Valid;
import java.util.Set;

/**
 * 用户与权限关系管理
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserPermissionController {

    final UserPermissionService thisService;
    final PermissionService permissionService;
    final UserPermissionMapper thisMapper;

    /**
     * 分页查询用户直接拥有的权限
     *
     * @param userId 用户ID
     */
    @PreAuthorize("@ck.hasPermit('auth:user:get')")
    @GetMapping("/users/{userId}/permissions")
    public R<Page<Permission>> list(@Valid PaginationRequest req, @PathVariable Integer userId) {
        Page<Permission> page = thisMapper.page(req.getPage(), userId, req);
        return R.ok(page);
    }

    /**
     * 用户新增权限
     *
     * @param userId 用户ID
     */
    @PreAuthorize("@ck.hasPermit('auth:user:update')")
    @PostMapping("/users/{userId}/permissions")
    public R<Void> add(@PathVariable Integer userId, @Valid @RequestBody UserPermission entity) {
        boolean success = thisService.createRelation(userId, entity.getPermissionId());
        if (success) {
            permissionService.refreshPermissions(userId);
            return R.ok("用户新增权限成功");
        }
        return R.fail("用户新增权限失败");
    }

    /**
     * 删除用户直接拥有的权限
     *
     * @param userId       用户ID
     * @param permissionId 权限ID
     */
    @PreAuthorize("@ck.hasPermit('auth:user:update')")
    @DeleteMapping("/users/{userId}/permissions/{permissionId}")
    public R<Void> delete(@PathVariable Integer userId, @PathVariable Integer permissionId) {
        boolean success = thisService.deleteRelation(userId, permissionId);
        if (success) {
            permissionService.refreshPermissions(userId);
            return R.ok("删除用户直接拥有的权限成功");
        }
        return R.fail("删除用户直接拥有的权限失败");
    }

    /**
     * 批量移除用户直接拥有的权限
     *
     * @param userId 用户ID
     */
    @PreAuthorize("@ck.hasPermit('auth:user:update')")
    @DeleteMapping("/users/{userId}/permissions")
    public R<Void> deleteBatch(@PathVariable Integer userId, @Valid BatchRequest req) {
        Set<Integer> ids = req.getIds();
        boolean success = ids.stream()
                .allMatch(permissionId -> thisService.deleteRelation(userId, permissionId));

        return success ? R.ok("批量移除用户直接拥有的权限成功") : R.fail("批量移除用户直接拥有的权限失败");
    }

    /**
     * 批量新增用户直接拥有的权限
     *
     * @param userId 用户ID
     */
    @PreAuthorize("@ck.hasPermit('auth:user:update')")
    @PostMapping("/users/{userId}/permissions/batch-add")
    public R<Void> createBatch(@PathVariable Integer userId, @Valid @RequestBody BatchRequest req) {
        Set<Integer> ids = req.getIds();
        boolean success = ids.stream()
                .allMatch(permissionId -> thisService.createRelation(userId, permissionId));

        return success ? R.ok("批量新增用户直接拥有的权限成功") : R.fail("批量新增用户直接拥有的权限失败");
    }

}
