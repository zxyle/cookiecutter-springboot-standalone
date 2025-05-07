package {{ cookiecutter.basePackage }}.biz.auth.user.role;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.biz.auth.permission.PermissionService;
import {{ cookiecutter.basePackage }}.biz.auth.role.Role;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.request.BatchRequest;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.validation.Valid;
import java.util.Set;

/**
 * 用户与角色关系管理
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserRoleController extends AuthBaseController {

    final UserRoleService thisService;
    final PermissionService permissionService;
    final UserRoleMapper thisMapper;

    /**
     * 分页查询用户拥有的角色
     *
     * @param userId 用户ID
     */
    @PreAuthorize("@ck.hasPermit('auth:user:get')")
    @GetMapping("/users/{userId}/roles")
    public R<Page<Role>> list(@Valid PaginationRequest req, @PathVariable Integer userId) {
        Page<Role> page = thisMapper.page(req.getPage(), userId, req);
        return R.ok(page);
    }

    /**
     * 用户新增角色
     * TODO 考虑将该接口合并到批量新增接口中，减少维护成本
     *
     * @param userId 用户ID
     */
    @PreAuthorize("@ck.hasPermit('auth:user:update')")
    @PostMapping("/users/{userId}/roles")
    public R<Void> add(@Valid @RequestBody UserRole entity, @PathVariable Integer userId) {
        boolean success = thisService.createRelation(userId, entity.getRoleId());
        if (success) {
            permissionService.refreshPermissions(userId);
            return R.ok("用户新增角色成功");
        }
        return R.fail("用户新增角色失败");
    }

    /**
     * 用户删除角色
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    @PreAuthorize("@ck.hasPermit('auth:user:update')")
    @DeleteMapping("/users/{userId}/roles/{roleId}")
    public R<Void> delete(@PathVariable Integer userId, @PathVariable Integer roleId) {
        boolean success = thisService.deleteRelation(userId, roleId);
        if (success) {
            permissionService.refreshPermissions(userId);
            return R.ok("用户删除角色成功");
        }
        return R.fail("用户删除角色失败");
    }

    /**
     * 批量移除用户拥有的角色
     *
     * @param userId 用户ID
     */
    @PreAuthorize("@ck.hasPermit('auth:user:update')")
    @DeleteMapping("/users/{userId}/roles")
    public R<Void> deleteBatch(@PathVariable Integer userId, @Valid BatchRequest req) {
        Set<Integer> ids = req.getIds();
        boolean success = ids.stream()
                .allMatch(roleId -> thisService.deleteRelation(userId, roleId));

        return success ? R.ok("批量移除用户拥有的角色成功") : R.fail("批量移除用户拥有的角色失败");
    }

    /**
     * 用户批量新增角色
     *
     * @param userId 用户ID
     */
    @PreAuthorize("@ck.hasPermit('auth:user:update')")
    @PostMapping("/users/{userId}/roles/batch-add")
    public R<Void> createBatch(@PathVariable Integer userId, @Valid @RequestBody BatchRequest req) {
        Set<Integer> ids = req.getIds();
        boolean success = ids.stream()
                .allMatch(roleId -> thisService.createRelation(userId, roleId));

        return success ? R.ok("用户批量新增角色成功") : R.fail("用户批量新增角色失败");
    }

}
