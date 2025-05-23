package {{ cookiecutter.basePackage }}.biz.auth.group.role;

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
import java.util.List;
import java.util.Set;

/**
 * 用户组与角色关系管理
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class GroupRoleController extends AuthBaseController {

    final GroupRoleService thisService;
    final PermissionService permissionService;
    final GroupRoleMapper thisMapper;

    /**
     * 分页查询用户组拥有的角色
     *
     * @param groupId 用户组ID
     */
    @PreAuthorize("@ck.hasPermit('auth:group:get')")
    @GetMapping("/groups/{groupId}/roles")
    public R<Page<Role>> pageRole(@PathVariable Integer groupId, @Valid PaginationRequest req) {
        Page<Role> page = thisMapper.page(req.getPage(), groupId, req);
        return R.ok(page);
    }

    /**
     * 用户组新增角色
     *
     * @param groupId 用户组ID
     */
    @PreAuthorize("@ck.hasPermit('auth:group:update')")
    @PostMapping("/groups/{groupId}/roles")
    public R<Void> add(@PathVariable Integer groupId, @Valid @RequestBody GroupRole entity) {
        boolean success = thisService.createRelation(groupId, entity.getRoleId());
        if (success) {
            List<Integer> users = getUsersByGroup(groupId);
            users.forEach(permissionService::refreshPermissions);
            return R.ok("用户组新增角色成功");
        }
        return R.fail("用户组新增角色失败");
    }

    /**
     * 用户组移除角色
     *
     * @param groupId 用户组ID
     * @param roleId  角色ID
     */
    @PreAuthorize("@ck.hasPermit('auth:group:update')")
    @DeleteMapping("/groups/{groupId}/roles/{roleId}")
    public R<Void> delete(@PathVariable Integer groupId, @PathVariable Integer roleId) {
        boolean success = thisService.deleteRelation(groupId, roleId);
        if (success) {
            List<Integer> users = getUsersByGroup(groupId);
            users.forEach(permissionService::refreshPermissions);
            return R.ok("用户组移除角色成功");
        }
        return R.fail("用户组移除角色失败");
    }

    /**
     * 批量移除用户组拥有的角色
     *
     * @param groupId 用户组ID
     */
    @PreAuthorize("@ck.hasPermit('auth:group:update')")
    @DeleteMapping("/groups/{groupId}/roles")
    public R<Void> deleteBatch(@PathVariable Integer groupId, @Valid BatchRequest req) {
        Set<Integer> ids = req.getIds();
        boolean success = ids.stream()
                .allMatch(roleId -> thisService.deleteRelation(groupId, roleId));

        return success ? R.ok("批量移除用户组拥有的角色成功") : R.fail("批量移除用户组拥有的角色失败");
    }

    /**
     * 用户组批量新增角色
     *
     * @param groupId 用户组ID
     */
    @PreAuthorize("@ck.hasPermit('auth:group:update')")
    @PostMapping("/groups/{groupId}/roles/batch-add")
    public R<Void> createBatch(@PathVariable Integer groupId, @Valid @RequestBody BatchRequest req) {
        Set<Integer> ids = req.getIds();
        boolean success = ids.stream()
                .allMatch(roleId -> thisService.createRelation(groupId, roleId));

        return success ? R.ok("批量新增用户组拥有的角色成功") : R.fail("批量新增用户组拥有的角色失败");
    }

}
