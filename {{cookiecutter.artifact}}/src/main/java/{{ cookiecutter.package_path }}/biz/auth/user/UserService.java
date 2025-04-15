package {{ cookiecutter.basePackage }}.biz.auth.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {{ cookiecutter.basePackage }}.biz.auth.group.Group;
import {{ cookiecutter.basePackage }}.biz.auth.group.GroupService;
import {{ cookiecutter.basePackage }}.biz.auth.mfa.AccountUtil;
import {{ cookiecutter.basePackage }}.biz.auth.permission.Permission;
import {{ cookiecutter.basePackage }}.biz.auth.profile.ProfileService;
import {{ cookiecutter.basePackage }}.biz.auth.role.Role;
import {{ cookiecutter.basePackage }}.biz.auth.user.group.UserGroup;
import {{ cookiecutter.basePackage }}.biz.auth.user.group.UserGroupService;
import {{ cookiecutter.basePackage }}.biz.auth.user.permission.UserPermissionService;
import {{ cookiecutter.basePackage }}.biz.auth.user.role.UserRoleService;
import {{ cookiecutter.basePackage }}.biz.sys.captcha.CaptchaUtil;
import {{ cookiecutter.basePackage }}.common.constant.AuthConst;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户 服务实现类
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "UserCache")
public class UserService extends ServiceImpl<UserMapper, User> {

    final StringRedisTemplate stringRedisTemplate;
    final UserGroupService userGroupService;
    final UserRoleService userRoleService;
    final UserPermissionService userPermissionService;
    final GroupService groupService;
    final ProfileService profileService;

    /**
     * 删除用户及其关联角色、用户组、权限
     *
     * @param userId 用户ID
     */
    @CacheEvict(key = "#userId")
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Integer userId) {
        boolean s1 = userPermissionService.deleteRelation(userId, 0);
        boolean s2 = userRoleService.deleteRelation(userId, 0);
        boolean s3 = userGroupService.deleteRelation(userId, 0);
        boolean s4 = removeById(userId);
        boolean s5 = profileService.delete(userId);
        boolean s6 = kick(userId);
        return (s1 && s2) && (s3 && s4) && (s5 && s6);
    }

    /**
     * 禁用用户
     *
     * @param userId 用户ID
     * @return 禁用用户是否成功
     */
    public boolean disable(Integer userId) {
        User user = new User();
        user.setId(userId);
        user.setEnabled(false);
        boolean success = updateById(user);
        return success && kick(userId);
    }

    /**
     * 启用用户
     *
     * @param userId 用户ID
     * @return 启用用户是否成功
     */
    public boolean enable(Integer userId) {
        User user = new User();
        user.setId(userId);
        user.setEnabled(true);
        return updateById(user);
    }

    /**
     * 下线用户
     *
     * @param userId 用户ID
     * @return 下线用户是否成功
     */
    public boolean kick(Integer userId) {
        String key = AuthConst.KEY_PREFIX + userId;
        Boolean hasKey = stringRedisTemplate.hasKey(key);
        if (hasKey == null || !hasKey) {
            // 用户可能没有登录或登录已过期
            return true;
        }
        Boolean delete = stringRedisTemplate.delete(key);
        return Boolean.TRUE.equals(delete);
    }

    /**
     * 通过账号名查询用户
     *
     * @param account 账号
     */
    // FIXME 存在bug：当用户修改密码后，重新登陆时，会查询到旧密码
    // @Cacheable(cacheNames = "UserCache", key = "#account", unless = "#result == null")
    public User findByAccount(String account) {
        if (!AccountUtil.isMobile(account) && !AccountUtil.isUsername(account) && !AccountUtil.isEmail(account)) {
            throw new IllegalArgumentException("account: " + account + " 账号格式错误.");
        }

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AccountUtil.isUsername(account), User::getUsername, account);
        wrapper.eq(AccountUtil.isEmail(account), User::getEmail, account);
        wrapper.eq(AccountUtil.isMobile(account), User::getMobile, account);
        return getOne(wrapper);
    }

    /**
     * 查询用户拥有的角色、用户组、权限
     *
     * @param user 用户
     * @param full 是否查询完整信息
     */
    public UserResponse attachUserInfo(User user, boolean full) {
        UserResponse userResponse = new UserResponse();
        if (full) {
            List<Group> groups = userGroupService.findGroupsByUserId(user.getId());
            userResponse.setGroups(CollectionUtils.isNotEmpty(groups) ? groups : null);

            List<Role> roles = userRoleService.findRolesByUserId(user.getId());
            userResponse.setRoles(CollectionUtils.isNotEmpty(roles) ? roles : null);

            List<Permission> permissions = userPermissionService.findPermissionsByUserId(user.getId());
            userResponse.setPermissions(CollectionUtils.isNotEmpty(permissions) ? permissions : null);
        }

        BeanUtils.copyProperties(user, userResponse);
        return userResponse;
    }

    /**
     * 更新用户关联的角色、用户组、权限
     *
     * @param userId        用户id
     * @param roleIds       角色id集合
     * @param groupIds      用户组id集合
     * @param permissionIds 权限id集合
     */
    public void updateRelation(Integer userId, Set<Integer> roleIds, Set<Integer> groupIds, Set<Integer> permissionIds) {
        userRoleService.updateRelation(userId, roleIds);
        userGroupService.updateRelation(userId, groupIds);
        userPermissionService.updateRelation(userId, permissionIds);
    }

    /**
     * 创建用户
     *
     * @param account         账号名
     * @param encodedPassword 加密后的密码
     * @return 用户信息
     */
    public User create(String account, String encodedPassword) {
        User user = new User();
        user.setPassword(encodedPassword);
        user.setNickname("用户_" + CaptchaUtil.randAlphabet(6));
        if (AccountUtil.isMobile(account)) {
            user.setMobile(account);
        } else if (AccountUtil.isEmail(account)) {
            user.setEmail(account);
        } else {
            user.setUsername(account);
        }
        return user;
    }

    /**
     * 获取所有有管理权限用户组成员
     *
     * @param userId 用户ID
     */
    public List<Integer> getAllChildren(Integer userId) {
        // 查询用户有管理员权限的用户组
        LambdaQueryWrapper<UserGroup> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(UserGroup::getUserId, UserGroup::getGroupId);
        queryWrapper.eq(UserGroup::getUserId, userId);
        queryWrapper.eq(UserGroup::getAdmin, AuthConst.ENABLED);
        List<UserGroup> userGroups = userGroupService.list(queryWrapper);

        // 查询用户组的所有子用户组
        List<Group> allGroups = new ArrayList<>();
        List<Group> groups = groupService.list();
        for (UserGroup userGroup : userGroups) {
            allGroups.addAll(groupService.getAllChildren(groups, userGroup.getGroupId()));
        }

        // 查询用户组下的所有用户
        LambdaQueryWrapper<UserGroup> userGroupQueryWrapper = new LambdaQueryWrapper<>();
        userGroupQueryWrapper.select(UserGroup::getUserId, UserGroup::getGroupId);
        List<Integer> groupIds = allGroups.stream().distinct().map(Group::getId).{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};
        userGroupQueryWrapper.in(UserGroup::getGroupId, groupIds);
        List<UserGroup> userGroupList = userGroupService.list(userGroupQueryWrapper);
        return userGroupList.stream().map(UserGroup::getUserId).{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};
    }

    /**
     * 锁定用户并退出当前登录状态
     *
     * @param userId 用户ID
     */
    public boolean locked(Integer userId) {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getId, userId);
        wrapper.set(User::getLocked, AuthConst.LOCKED);
        boolean update = update(wrapper);
        boolean kick = kick(userId);
        return update && kick;
    }

    /**
     * 解锁用户
     *
     * @param userId 用户ID
     */
    public boolean unlock(Integer userId) {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getId, userId);
        wrapper.set(User::getLocked, AuthConst.UNLOCKED);
        boolean update = update(wrapper);

        String key = "pwd:change:" + userId;
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))) {
            stringRedisTemplate.delete(key);
        }

        return update;
    }

    /**
     * 带缓存的ID查询
     */
    @Cacheable(key = "#userId", unless = "#result == null")
    public User findById(Integer userId) {
        return getById(userId);
    }

    /**
     * 带缓存的ID更新
     */
    @CacheEvict(key = "#user.id")
    public boolean putById(User user) {
        return updateById(user);
    }
}
