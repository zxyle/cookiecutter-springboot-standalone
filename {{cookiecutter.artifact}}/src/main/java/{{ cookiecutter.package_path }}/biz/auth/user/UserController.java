package {{ cookiecutter.basePackage }}.biz.auth.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.biz.auth.user.request.AdminAddUserRequest;
import {{ cookiecutter.basePackage }}.common.request.auth.ListAuthRequest;
import {{ cookiecutter.basePackage }}.biz.auth.user.request.UpdateUserRequest;
import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.biz.sys.setting.SettingService;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController extends AuthBaseController {

    final SettingService setting;
    final PasswordEncoder encoder;
    final UserService thisService;

    /**
     * 分页查询用户列表
     */
    @LogOperation(name = "分页查询用户列表", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:user:list')")
    @GetMapping("/users")
    public R<Page<UserResponse>> list(@Valid ListAuthRequest req) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getId, User::getCreateTime, User::getUsername, User::getNickname, User::getEmail, User::getMobile, User::getEnabled);
        if (StringUtils.isNotBlank(req.getKeyword())) {
            wrapper.and(i -> i.like(User::getUsername, req.getKeyword())
                    .or().like(User::getEmail, req.getKeyword())
                    .or().like(User::getMobile, req.getKeyword())
                    .or().like(User::getNickname, req.getKeyword()));
        }

        // 不能将没有权限的用户信息返回
        List<Integer> members = thisService.getAllChildren(getUserId());
        wrapper.in(User::getId, members);

        wrapper.eq(req.getEnabled() != null, User::getEnabled, req.getEnabled());
        Page<User> page = thisService.page(req.getPage(), wrapper);

        // 增加角色和组信息
        List<UserResponse> userResponses = page.getRecords().stream()
                .map(user -> thisService.attachUserInfo(user, req.isFull()))
                .{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};
        Page<UserResponse> responsePage = new Page<>();
        responsePage.setRecords(userResponses);
        responsePage.setTotal(page.getTotal());
        return R.ok(responsePage);
    }

    /**
     * 使用用户名创建用户
     */
    @LogOperation(name = "使用用户名创建用户", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:user:add')")
    @PostMapping("/users")
    public R<User> add(@Valid @RequestBody AdminAddUserRequest req) {
        if (thisService.findByAccount(req.getAccount()) != null) {
            return R.fail("创建失败，用户名被占用");
        }

        // 构建用户
        User user = thisService.create(req.getAccount(), encoder.encode(req.getPassword()));
        boolean isReset = setting.get("auth.user.reset").isReal();
        user.setMustChangePwd(req.isMustChangePwd() && isReset);
        boolean success = thisService.save(user);
        if (!success) {
            return R.fail("创建用户失败");
        }

        Set<Integer> roleIds = new HashSet<>();
        if (CollectionUtils.isEmpty(req.getRoleIds())) {
            Integer defaultRole = setting.get("auth.user.default-role").getIntValue();
            roleIds.add(defaultRole);
        } else {
            roleIds.addAll(req.getRoleIds());
        }
        // 更新用户角色、用户组关联关系, 防止被授予过高的权限的角色
        thisService.updateRelation(user.getId(), roleIds, req.getGroupIds(), null);
        return R.ok(user);
    }

    /**
     * 更新用户信息
     *
     * @param userId 用户ID
     */
    @LogOperation(name = "更新用户信息", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:user:update')")
    @PutMapping("/users/{userId}")
    public R<Void> update(@PathVariable Integer userId, @Valid @RequestBody UpdateUserRequest req) {
        if (!groupService.isAllowed(getUserId(), userId, null)) {
            return R.fail("没有权限更新用户");
        }

        // 需要防止赋予比操作者更高的权限
        thisService.updateRelation(userId, req.getRoleIds(), req.getGroupIds(), req.getPermissionIds());
        return R.ok("更新用户成功");
    }

    /**
     * 按ID查询用户
     *
     * @param userId 用户ID
     */
    @LogOperation(name = "按ID查询用户", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:user:get')")
    @GetMapping("/users/{userId}")
    public R<UserResponse> get(@PathVariable Integer userId) {
        if (!groupService.isAllowed(getUserId(), userId, null)) {
            return R.fail("没有权限查询该用户");
        }

        User user = thisService.findById(userId);
        if (user != null) {
            return R.ok(thisService.attachUserInfo(user, true));
        }
        return R.fail("用户不存在");
    }

    /**
     * 按ID删除用户
     *
     * @param userId 用户ID
     */
    @LogOperation(name = "按ID删除用户", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:user:delete')")
    @DeleteMapping("/users/{userId}")
    public R<Void> delete(@PathVariable Integer userId) {
        if (userId.equals(getUserId())) {
            return R.fail("不能删除自己");
        }

        // 不能删除其他组的用户
        if (!groupService.isAllowed(getUserId(), userId, null)) {
            return R.fail("没有权限删除该用户");
        }

        boolean success = thisService.delete(userId);
        return success ? R.ok("已成功删除该用户") : R.fail("删除用户失败");
    }

    /**
     * 禁用用户
     *
     * @param userId 用户ID
     */
    @LogOperation(name = "禁用用户", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:user:disable')")
    @PutMapping("/users/{userId}/disable")
    public R<Void> disable(@PathVariable Integer userId) {
        if (userId.equals(getUserId())) {
            return R.fail("不能禁用自己");
        }

        // 不能禁用其他组的用户
        if (!groupService.isAllowed(getUserId(), userId, null)) {
            return R.fail("没有权限禁用该用户");
        }

        boolean success = thisService.disable(userId);
        return success ? R.ok("已成功禁用该用户") : R.fail("禁用用户失败");
    }

    /**
     * 启用用户
     *
     * @param userId 用户ID
     */
    @LogOperation(name = "启用用户", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:user:enable')")
    @PutMapping("/users/{userId}/enable")
    public R<Void> enable(@PathVariable Integer userId) {
        if (!groupService.isAllowed(getUserId(), userId, null)) {
            return R.fail("没有权限启用用户");
        }

        boolean success = thisService.enable(userId);
        return success ? R.ok("已成功启用该用户") : R.fail("启用用户失败");
    }

    /**
     * 下线用户
     *
     * @param userId 用户ID
     */
    @LogOperation(name = "下线用户", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:user:kick')")
    @PutMapping("/users/{userId}/kick")
    public R<Void> kick(@PathVariable Integer userId) {
        if (userId.equals(getUserId())) {
            return R.fail("不能将自己下线");
        }

        if (!groupService.isAllowed(getUserId(), userId, null)) {
            return R.fail("没有权限下线用户");
        }

        boolean success = thisService.kick(userId);
        return success ? R.ok("下线用户成功") : R.fail("下线用户失败");
    }
}
