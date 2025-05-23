package {{ cookiecutter.basePackage }}.biz.auth.role;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {{ cookiecutter.basePackage }}.biz.auth.group.role.GroupRoleService;
import {{ cookiecutter.basePackage }}.biz.auth.permission.Permission;
import {{ cookiecutter.basePackage }}.biz.auth.role.permission.RolePermissionService;
import {{ cookiecutter.basePackage }}.biz.auth.user.role.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * 角色信息 服务实现类
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "RoleCache")
public class RoleService extends ServiceImpl<RoleMapper, Role> {

    final GroupRoleService groupRoleService;
    final RolePermissionService rolePermissionService;
    final UserRoleService userRoleService;

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     */
    @CacheEvict(key = "#roleId")
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Integer roleId) {
        boolean s1 = groupRoleService.deleteRelation(0, roleId);
        boolean s2 = rolePermissionService.deleteRelation(roleId, 0);
        boolean s3 = userRoleService.deleteRelation(0, roleId);
        boolean s4 = removeById(roleId);
        return (s1 && s2) && (s3 && s4);
    }

    /**
     * 通过ID查询角色
     *
     * @param roleId 角色ID
     */
    @Cacheable(key = "#roleId", unless = "#result == null")
    public Role findById(Integer roleId) {
        return getById(roleId);
    }

    /**
     * 判断角色是否已经被使用
     *
     * @param roleId 角色ID
     * @return true 已经被使用 false 未被使用
     */
    public boolean isAlreadyUsed(Integer roleId) {
        if (userRoleService.countRelation(null, roleId) > 0) {
            return true;
        }

        return groupRoleService.countRelation(null, roleId) > 0;
    }

    /**
     * 查询角色对应权限关系
     *
     * @param role 角色
     * @return 包含权限关系的角色对象
     */
    public RoleResponse attachRoleInfo(Role role, boolean full) {
        RoleResponse response = new RoleResponse();

        if (full) {
            List<Permission> permissions = rolePermissionService.findPermissionsByRoleId(role.getId());
            response.setPermissions(CollectionUtils.isNotEmpty(permissions) ? permissions : null);
        }

        BeanUtils.copyProperties(role, response);
        return response;
    }

    /**
     * 更新角色权限关系
     *
     * @param roleId        角色id
     * @param permissionIds 权限id集合
     */
    public void updateRelation(Integer roleId, Set<Integer> permissionIds) {
        rolePermissionService.updateRelation(roleId, permissionIds);
    }

    /**
     * 判断角色名称或者编码是否重复
     *
     * @param name 角色名称
     * @param code 角色编码
     * @return true 重复 false 不重复
     */
    public boolean isDuplicate(String name, String code) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(name), Role::getName, name)
                .or().eq(StringUtils.isNotBlank(code), Role::getCode, code);
        return exists(wrapper);
    }
}
