package {{ cookiecutter.basePackage }}.biz.auth.user.group;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.biz.auth.group.Group;
import {{ cookiecutter.basePackage }}.biz.auth.permission.PermissionService;
import {{ cookiecutter.basePackage }}.common.request.auth.ListAuthRequest;
import {{ cookiecutter.basePackage }}.biz.auth.user.User;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.request.BatchRequest;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.validation.Valid;
import java.util.Set;

/**
 * 用户与用户组关系管理
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserGroupController extends AuthBaseController {

    final UserGroupService thisService;
    final PermissionService permissionService;
    final UserGroupMapper thisMapper;

    /**
     * 分页查询用户所属用户组
     *
     * @param userId 用户ID
     */
    @Secured(value = "ROLE_admin")
    @GetMapping("/users/{userId}/groups")
    public R<Page<Group>> list(@Valid PaginationRequest req, @PathVariable Integer userId) {
        Page<Group> page = thisMapper.page(req.getPage(), userId, req);
        return R.ok(page);
    }

    /**
     * 用户添加到用户组
     *
     * @param userId 用户ID
     */
    @Secured(value = "ROLE_admin")
    @PostMapping("/users/{userId}/groups")
    public R<Void> add(@PathVariable Integer userId, @Valid @RequestBody UserGroup entity) {
        boolean success = thisService.createRelation(userId, entity.getGroupId());
        if (success) {
            permissionService.refreshPermissions(userId);
            return R.ok("用户添加到用户组成功");
        }
        return R.fail("用户添加到用户组失败");
    }

    /**
     * 用户移出用户组
     *
     * @param userId  用户ID
     * @param groupId 用户组ID
     */
    @Secured(value = "ROLE_admin")
    @DeleteMapping("/users/{userId}/groups/{groupId}")
    public R<Void> delete(@PathVariable Integer userId, @PathVariable Integer groupId) {
        boolean success = thisService.deleteRelation(userId, groupId);
        if (success) {
            permissionService.refreshPermissions(userId);
            return R.ok("用户移出用户组成功");
        }
        return R.fail("用户移出用户组失败");
    }

    /**
     * 分页查询用户组下的用户
     *
     * @param groupId 用户组ID
     */
    @PreAuthorize("@ck.hasPermit('auth:group:get')")
    @GetMapping("/groups/{groupId}/users")
    public R<Page<User>> pageUser(@PathVariable Integer groupId, @Valid ListAuthRequest req) {
        // todo 需支持查询子用户组下的用户
        Page<User> list = thisMapper.pageUser(req.getPage(), groupId, req);
        return R.ok(list);
    }

    /**
     * 用户批量移出用户组
     *
     * @param userId 用户ID
     */
    @Secured(value = "ROLE_admin")
    @DeleteMapping("/users/{userId}/groups")
    public R<Void> deleteBatch(@PathVariable Integer userId, @Valid BatchRequest req) {
        Set<Integer> ids = req.getIds();
        boolean success = ids.stream()
                .allMatch(groupId -> thisService.deleteRelation(userId, groupId));

        return success ? R.ok("用户批量移出用户组成功") : R.fail("用户批量移出用户组失败");
    }

    /**
     * 用户批量添加到用户组
     *
     * @param userId 用户ID
     */
    @Secured(value = "ROLE_admin")
    @PostMapping("/users/{userId}/groups/batch-add")
    public R<Void> createBatch(@PathVariable Integer userId, @Valid @RequestBody BatchRequest req) {
        Set<Integer> ids = req.getIds();
        boolean success = ids.stream()
                .allMatch(groupId -> thisService.createRelation(userId, groupId));

        return success ? R.ok("用户批量添加到用户组成功") : R.fail("用户批量添加到用户组失败");
    }

}
