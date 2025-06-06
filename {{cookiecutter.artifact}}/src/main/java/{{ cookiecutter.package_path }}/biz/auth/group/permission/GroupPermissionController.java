package {{ cookiecutter.basePackage }}.biz.auth.group.permission;

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
 * 用户组与权限关系管理
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class GroupPermissionController extends AuthBaseController {

    final GroupPermissionService thisService;
    final PermissionService permissionService;
    final GroupPermissionMapper thisMapper;

    /**
     * 分页查询用户组所拥有的权限
     *
     * @param groupId 用户组ID
     */
    @PreAuthorize("@ck.hasPermit('auth:group:get')")
    @GetMapping("/groups/{groupId}/permissions")
    public R<Page<Permission>> list(@Valid PaginationRequest req, @PathVariable Integer groupId) {
        Page<Permission> page = thisMapper.page(req.getPage(), groupId, req);
        return R.ok(page);
    }

    /**
     * 用户组新增权限
     *
     * @param groupId 用户组ID
     */
    @PreAuthorize("@ck.hasPermit('auth:group:update')")
    @PostMapping("/groups/{groupId}/permissions")
    public R<Void> add(@PathVariable Integer groupId, @Valid @RequestBody GroupPermission entity) {
        boolean success = thisService.createRelation(groupId, entity.getPermissionId());
        if (success) {
            List<Integer> users = getUsersByGroup(groupId);
            users.forEach(permissionService::refreshPermissions);
            return R.ok("用户组新增权限成功");
        }
        return R.fail("用户组新增权限失败");
    }

    /**
     * 移除用户组拥有的权限
     *
     * @param permissionId 权限ID
     * @param groupId      用户组ID
     */
    @PreAuthorize("@ck.hasPermit('auth:group:update')")
    @DeleteMapping("/groups/{groupId}/permissions/{permissionId}")
    public R<Void> delete(@PathVariable Integer permissionId, @PathVariable Integer groupId) {
        boolean success = thisService.deleteRelation(groupId, permissionId);
        if (success) {
            List<Integer> users = getUsersByGroup(groupId);
            users.forEach(permissionService::refreshPermissions);
            return R.ok("移除用户组拥有的权限成功");
        }
        return R.fail("移除用户组拥有的权限失败");
    }

    /**
     * 批量移除用户组拥有的权限
     *
     * @param groupId 用户组ID
     */
    @PreAuthorize("@ck.hasPermit('auth:group:update')")
    @DeleteMapping("/groups/{groupId}/permissions")
    public R<Void> deleteBatch(@PathVariable Integer groupId, @Valid BatchRequest req) {
        Set<Integer> ids = req.getIds();
        boolean success = ids.stream()
                .allMatch(permissionId -> thisService.deleteRelation(groupId, permissionId));

        return success ? R.ok("批量移除用户组拥有的权限成功") : R.fail("批量移除用户组拥有的权限失败");
    }

    /**
     * 用户组批量新增权限
     *
     * @param groupId 用户组ID
     */
    @PreAuthorize("@ck.hasPermit('auth:group:update')")
    @PostMapping("/groups/{groupId}/permissions/batch-add")
    public R<Void> createBatch(@PathVariable Integer groupId, @Valid @RequestBody BatchRequest req) {
        Set<Integer> ids = req.getIds();
        boolean success = ids.stream()
                .allMatch(permissionId -> thisService.createRelation(groupId, permissionId));

        return success ? R.ok("批量新增用户组拥有的权限成功") : R.fail("批量新增用户组拥有的权限失败");
    }

}
