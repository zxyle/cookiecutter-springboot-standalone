package {{ cookiecutter.basePackage }}.common.controller;

import {{ cookiecutter.basePackage }}.biz.auth.group.GroupService;
import {{ cookiecutter.basePackage }}.biz.auth.user.User;
import {{ cookiecutter.basePackage }}.biz.auth.user.group.UserGroup;
import {{ cookiecutter.basePackage }}.biz.auth.user.group.UserGroupService;
import {{ cookiecutter.basePackage }}.biz.auth.user.role.UserRole;
import {{ cookiecutter.basePackage }}.biz.auth.user.role.UserRoleService;
import {{ cookiecutter.basePackage }}.config.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限模块通用方法
 */
@Component
public class AuthBaseController {

    public static final String ROLE_PREFIX = "ROLE_";

    @Autowired
    protected GroupService groupService;

    @Autowired
    protected UserGroupService userGroupService;

    @Autowired
    protected UserRoleService userRoleService;

    public LoginUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (LoginUser) authentication.getPrincipal();
    }

    /**
     * 获取当前登录用户信息
     */
    public User getLoggedInUser() {
        return getLoginUser().getUser();
    }

    /**
     * 获取当前登录用户ID
     */
    public Integer getUserId() {
        return getLoggedInUser().getId();
    }

    /**
     * 是否为平台超级管理员
     */
    public boolean isAdmin() {
        User user = getLoggedInUser();
        return user.getAdmin();
    }

    /**
     * 获取当前登录用户名
     */
    public String getCurrentUsername() {
        User user = getLoggedInUser();
        return user.getUsername();
    }

    /**
     * 获取当前登录用户拥有的角色码
     */
    public Set<String> getRoleCodes() {
        LoginUser loginUser = getLoginUser();
        if (loginUser == null) {
            return Collections.emptySet(); // 如果登录用户为空，返回空集合
        }
        return loginUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(s -> s.startsWith(ROLE_PREFIX))
                .map(s -> s.substring(ROLE_PREFIX.length())) // 去掉前缀
                .collect(Collectors.toSet());
    }

    /**
     * 判断当前登录用户是否拥有指定角色
     *
     * @param roleCode 角色码
     * @return 是否拥有
     */
    public boolean hasRole(String roleCode) {
        return getRoleCodes().contains(roleCode);
    }

    /**
     * 获取当前登录用户的权限码
     */
    public Set<String> getPermissionCodes() {
        LoginUser loginUser = getLoginUser();
        if (loginUser == null) {
            return Collections.emptySet();
        }

        return loginUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(s -> !s.startsWith(ROLE_PREFIX))
                .collect(Collectors.toSet());
    }

    /**
     * 判断当前登录用户是否拥有指定权限
     *
     * @param permissionCode 权限码
     * @return 是否拥有
     */
    public boolean hasPermission(String permissionCode) {
        return getPermissionCodes().contains(permissionCode);
    }

    /**
     * 获取当前用户组以及子用户组ID
     */
    public List<Integer> getSubGroupIds() {
        List<Integer> longs = new ArrayList<>();
        for (Integer groupId : getGroupIds()) {
            longs.addAll(groupService.getSubGroups(groupId));
        }
        return longs.stream().distinct().{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};
    }

    /**
     * 判断该用户组是否是其子用户组
     */
    public boolean isSubGroup(Integer groupId) {
        List<Integer> subGroups = getSubGroupIds();
        return subGroups.contains(groupId);
    }

    /**
     * 获取持有指定用户组的用户
     *
     * @param groupId 用户组ID
     */
    public List<Integer> getUsersByGroup(Integer groupId) {
        List<UserGroup> groups = userGroupService.queryRelation(0, groupId);
        return groups.stream().map(UserGroup::getUserId).{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};
    }

    /**
     * 获取持有指定角色的用户
     *
     * @param roleId 角色ID
     * @return 用户ID列表
     */
    public List<Integer> getUsersByRole(Integer roleId) {
        List<UserRole> roles = userRoleService.queryRelation(0, roleId);
        return roles.stream().map(UserRole::getUserId).{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};
    }

    /**
     * 查询用户所在用户组Id
     */
    public List<Integer> getGroupIds() {
        List<UserGroup> groups = userGroupService.queryRelation(getUserId(), 0);
        return groups.stream().map(UserGroup::getGroupId).{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};
    }

    /**
     * 判断用户是否登录 可以改成 is_authenticated
     */
    public boolean isLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof LoginUser)) {
            return false;
        }

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return loginUser.getUser().getId() != null;
    }

}
