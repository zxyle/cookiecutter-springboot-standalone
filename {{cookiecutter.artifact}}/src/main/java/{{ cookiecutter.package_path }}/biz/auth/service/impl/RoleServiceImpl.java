package {{ cookiecutter.basePackage }}.biz.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {{ cookiecutter.basePackage }}.biz.auth.entity.GroupRole;
import {{ cookiecutter.basePackage }}.biz.auth.entity.Role;
import {{ cookiecutter.basePackage }}.biz.auth.entity.UserGroup;
import {{ cookiecutter.basePackage }}.biz.auth.mapper.RoleMapper;
import {{ cookiecutter.basePackage }}.biz.auth.mapper.UserRoleMapper;
import {{ cookiecutter.basePackage }}.biz.auth.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息 服务实现类
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    IUserGroupService userGroupService;

    @Autowired
    IGroupRoleService groupRoleService;

    @Autowired
    IRolePermissionService rolePermissionService;

    @Autowired
    IUserRoleService userRoleService;

    @Autowired
    IRoleService roleService;

    /**
     * 查询用户组对应角色ID
     *
     * @param groupId 用户组ID
     */
    public List<Long> selectRolesByGroup(Long groupId) {
        List<GroupRole> groupRoles = groupRoleService.queryRelation(groupId, 0L);
        return groupRoles.stream().map(GroupRole::getRoleId).collect(Collectors.toList());
    }

    /**
     * 获取用户所有角色名称
     *
     * @param userId 用户ID
     */
    @Cacheable(cacheNames = "roleCache", key = "#userId")
    @Override
    public List<String> getAllRoles(Long userId) {
        List<String> roles = new ArrayList<>(userRoleMapper.selectRoleByUid(userId));

        List<UserGroup> userGroups = userGroupService.queryRelation(userId, 0L);
        userGroups.forEach(ug -> {
            // 查询用户组对应那些角色ID
            List<Long> rolesIds = selectRolesByGroup(ug.getGroupId());
            rolesIds.forEach(roleId -> {
                Role role = roleService.queryById(roleId);
                if (role != null) {
                    roles.add(role.getCode());
                }
            });
        });

        return roles.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     */
    @Transactional
    @Override
    public boolean delete(Long roleId) {
        boolean s1 = groupRoleService.deleteRelation(0L, roleId);
        boolean s2 = rolePermissionService.deleteRelation(roleId, 0L);
        boolean s3 = userRoleService.deleteRelation(0L, roleId);
        boolean s4 = removeById(roleId);
        return (s1 && s2) && (s3 && s4);
    }

    /**
     * 通过ID查询角色
     *
     * @param roleId 角色ID
     */
    @Cacheable(cacheNames = "roleCache", key = "#roleId")
    @Override
    public Role queryById(Long roleId) {
        return getById(roleId);
    }
}
