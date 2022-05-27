package {{ cookiecutter.basePackage }}.biz.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {{ cookiecutter.basePackage }}.biz.auth.entity.Permission;
import {{ cookiecutter.basePackage }}.biz.auth.entity.UserGroup;
import {{ cookiecutter.basePackage }}.biz.auth.entity.UserRole;
import {{ cookiecutter.basePackage }}.biz.auth.mapper.PermissionMapper;
import {{ cookiecutter.basePackage }}.biz.auth.mapper.RolePermissionMapper;
import {{ cookiecutter.basePackage }}.biz.auth.mapper.UserPermissionMapper;
import {{ cookiecutter.basePackage }}.biz.auth.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限信息 服务实现类
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Autowired
    IGroupRoleService groupRoleService;

    @Autowired
    UserPermissionMapper userPermissionMapper;

    @Autowired
    IUserPermissionService userPermissionService;

    @Autowired
    IUserRoleService userRoleService;

    @Autowired
    IUserGroupService userGroupService;

    @Autowired
    IGroupPermissionService groupPermissionService;

    @Autowired
    IRolePermissionService rolePermissionService;

    // 查询角色对应的权限
    public List<String> selectRolesPermission(List<Long> roleIds) {
        List<String> permissions = new ArrayList<>();
        roleIds.forEach(rid -> permissions.addAll(rolePermissionMapper.getPermissionNameByRid(rid)));
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
        permissions.addAll(userPermissionMapper.selectPermissionNameByUid(userId));
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
}
