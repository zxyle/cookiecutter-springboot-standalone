package {{ cookiecutter.basePackage }}.biz.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {{ cookiecutter.basePackage }}.biz.auth.constant.AuthConst;
import {{ cookiecutter.basePackage }}.biz.auth.entity.Permission;
import {{ cookiecutter.basePackage }}.biz.auth.entity.UserGroup;
import {{ cookiecutter.basePackage }}.biz.auth.entity.UserRole;
import {{ cookiecutter.basePackage }}.biz.auth.mapper.PermissionMapper;
import {{ cookiecutter.basePackage }}.biz.auth.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 权限信息 服务实现类
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    IGroupRoleService groupRoleService;

    IUserPermissionService userPermissionService;

    IUserRoleService userRoleService;

    IUserGroupService userGroupService;

    IGroupPermissionService groupPermissionService;

    IRolePermissionService rolePermissionService;

    IRoleService roleService;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    public PermissionServiceImpl(IGroupRoleService groupRoleService, IUserPermissionService userPermissionService, IUserRoleService userRoleService, IUserGroupService userGroupService, IGroupPermissionService groupPermissionService, IRolePermissionService rolePermissionService, IRoleService roleService) {
        this.groupRoleService = groupRoleService;
        this.userPermissionService = userPermissionService;
        this.userRoleService = userRoleService;
        this.userGroupService = userGroupService;
        this.groupPermissionService = groupPermissionService;
        this.rolePermissionService = rolePermissionService;
        this.roleService = roleService;
    }

    // 查询角色对应的权限
    public List<String> selectRolesPermission(List<Long> roleIds) {
        List<String> permissions = new ArrayList<>();
        roleIds.forEach(roleId -> permissions.addAll(rolePermissionService.getPermissionNameByRoleId(roleId)));
        return permissions;
    }

    // 查询用户-角色-权限
    public List<String> selectPermissionsByRole(Long userId) {
        List<UserRole> userRoles = userRoleService.queryRelation(userId, 0L);
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        return selectRolesPermission(roleIds);
    }

    // 查询用户-用户组-角色-权限
    public List<String> selectPermissionByGroupRole(Long userId) {
        List<String> permissions = new ArrayList<>();
        List<UserGroup> groups = userGroupService.queryRelation(userId, 0L);
        groups.forEach(group -> permissions.addAll(selectRolesPermission(groupRoleService.selectRoleByGroup(group.getGroupId()))));
        return permissions;
    }


    /**
     * 获取用户所有权限名称
     *
     * @param userId 用户ID
     */
    @Cacheable(cacheNames = "permissionCache", key = "#userId")
    @Override
    public List<String> getAllPermissions(Long userId) {
        List<String> permissions = new ArrayList<>();
        // 用户直接拥有的权限
        permissions.addAll(userPermissionService.selectPermissionNameByUserid(userId));
        // 所在用户组拥有的权限
        permissions.addAll(groupPermissionService.selectPermissionsByGroup(userId));
        // 用户拥有角色所获得权限
        permissions.addAll(selectPermissionsByRole(userId));
        // 所在用户组拥有的角色 所拥有的权限
        permissions.addAll(selectPermissionByGroupRole(userId));
        return permissions.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 删除权限
     *
     * @param permissionId 权限ID
     */
    @Transactional
    @Override
    public boolean delete(Long permissionId) {
        boolean s1 = groupPermissionService.deleteRelation(0L, permissionId);
        boolean s2 = rolePermissionService.deleteRelation(0L, permissionId);
        boolean s3 = userPermissionService.deleteRelation(0L, permissionId);
        boolean s4 = removeById(permissionId);
        return (s1 && s2) && (s3 && s4);
    }

    /**
     * 查询用户所有权限码和用户所有角色码
     *
     * @param userId 用户ID
     */
    @Override
    public List<String> getSecurityPermissions(Long userId) {
        // 查询用户所有权限码
        List<String> permissions = new ArrayList<>(getAllPermissions(userId));

        // 查询用户所有角色码（spring security需要角色已ROLE_开头）
        List<String> roles = roleService.getAllRoles(userId);
        permissions.addAll(roles.stream().map(e -> "ROLE_" + e).collect(Collectors.toList()));
        return permissions;
    }

    /**
     * 重新将新的权限码和角色码加载到redis中
     *
     * @param userId 用户ID
     */
    @Override
    public boolean refreshPermissions(Long userId) {
        String key = "permissions:" + userId;
        Boolean exist = stringRedisTemplate.hasKey(key);
        // 只对已登录用户进行权限刷新
        if (Boolean.TRUE.equals(exist)) {
            // 将权限码和角色码存入redis
            List<String> permissions = getSecurityPermissions(userId);
            stringRedisTemplate.opsForValue().set(key, String.join(AuthConst.DELIMITER, permissions), 1, TimeUnit.DAYS);
            return true;
        }
        return false;
    }

    /**
     * 获取持有该权限的用户
     *
     * @param code 权限码
     */
    @Override
    public List<Long> holdPermission(String code) {
        List<Long> users = new ArrayList<>();

        Set<String> keys = stringRedisTemplate.keys("permissions:");
        if (null != keys) {
            // List<String> strings = stringRedisTemplate.opsForValue().multiGet(keys);
            for (String key : keys) {
                Long userId = Long.parseLong(key.split(":")[1]);
                String value = stringRedisTemplate.opsForValue().get(key);
                if (StringUtils.isNotBlank(value)) {
                    List<String> permissions = Arrays.asList(value.split(AuthConst.DELIMITER));
                    if (permissions.contains(code)) {
                        users.add(userId);
                    }
                }
            }
        }
        return users;
    }
}