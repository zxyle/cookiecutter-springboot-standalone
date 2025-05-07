package {{ cookiecutter.basePackage }}.biz.auth.role.permission;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {{ cookiecutter.basePackage }}.biz.auth.permission.Permission;
import {{ cookiecutter.basePackage }}.biz.auth.permission.PermissionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色权限信息 服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "RolePermissionCache")
public class RolePermissionService extends ServiceImpl<RolePermissionMapper, RolePermission> {

    final StringRedisTemplate stringRedisTemplate;
    final PermissionMapper permissionMapper;
    private static final String ROLE_PERMISSION_KEY = "role:%s:permissions";

    /**
     * 删除映射关系
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     */
    @Caching(evict = {
            @CacheEvict(key = "#roleId + ':' + null"),
            @CacheEvict(key = "null + ':' + #permissionId")
    })
    public boolean deleteRelation(Integer roleId, Integer permissionId) {
        if (countRelation(roleId, permissionId) == 0) {
            return true;
        }

        deletePermissionsFromRedis(roleId, Collections.singletonList(permissionId));
        return remove(buildWrapper(roleId, permissionId));
    }

    /**
     * 查询映射关系
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     */
    @Cacheable(key = "#roleId + ':' + #permissionId", unless = "#result == null")
    public List<RolePermission> queryRelation(Integer roleId, Integer permissionId) {
        LambdaQueryWrapper<RolePermission> wrapper = buildWrapper(roleId, permissionId);
        wrapper.select(RolePermission::getRoleId, RolePermission::getPermissionId);
        return list(wrapper);
    }

    /**
     * 统计映射关系
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     */
    public Long countRelation(Integer roleId, Integer permissionId) {
        return count(buildWrapper(roleId, permissionId));
    }

    /**
     * 创建映射关系
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     */
    @Caching(evict = {
            @CacheEvict(key = "#roleId + ':' + null"),
            @CacheEvict(key = "null + ':' + #permissionId")
    })
    public boolean createRelation(Integer roleId, Integer permissionId) {
        if (countRelation(roleId, permissionId) > 0) {
            return true;
        }

        savePermissionsToRedis(roleId, Collections.singletonList(permissionId));
        return save(new RolePermission(roleId, permissionId));
    }

    /**
     * 更新映射关系
     *
     * @param roleId        角色id
     * @param permissionIds 权限id集合
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRelation(Integer roleId, Set<Integer> permissionIds) {
        if (CollectionUtils.isEmpty(permissionIds) || roleId == null || roleId == 0) {
            return false;
        }

        // 删除已有的映射关系
        boolean del = deleteRelation(roleId, null);

        // 创建新的映射关系
        boolean success = permissionIds.stream()
                .allMatch(permissionId -> createRelation(roleId, permissionId));
        return del && success;
    }

    /**
     * 构建wrapper
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     * @return wrapper
     */
    private LambdaQueryWrapper<RolePermission> buildWrapper(Integer roleId, Integer permissionId) {
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(roleId != null && roleId != 0, RolePermission::getRoleId, roleId);
        wrapper.eq(permissionId != null && permissionId != 0, RolePermission::getPermissionId, permissionId);
        return wrapper;
    }

    /**
     * 查询角色所拥有的权限列表
     *
     * @param roleId 角色ID
     */
    public List<Permission> findPermissionsByRoleId(Integer roleId) {
        return baseMapper.findPermissionsByRoleId(roleId);
    }

    // 将权限存到redis中
    public void savePermissionsToRedis(Integer roleId, List<Integer> permissionIds) {
        List<String> code = findPermissionCodeByIds(permissionIds);
        String key = String.format(ROLE_PERMISSION_KEY, roleId);
        stringRedisTemplate.opsForSet().add(key, code.stream().map(String::valueOf).toArray(String[]::new));
    }

    public void savePermissionCodesToRedis(Integer roleId, List<String> permissionCodes) {
        String key = String.format(ROLE_PERMISSION_KEY, roleId);
        stringRedisTemplate.opsForSet().add(key, permissionCodes.stream().map(String::valueOf).toArray(String[]::new));
    }

    // 从redis中删除权限
    public void deletePermissionsFromRedis(Integer roleId, List<Integer> permissionIds) {
        List<String> code = findPermissionCodeByIds(permissionIds);
        String key = String.format(ROLE_PERMISSION_KEY, roleId);
        stringRedisTemplate.opsForSet().remove(key, code.toArray());
    }

    public List<String> findPermissionCodeByIds(List<Integer> permissionIds) {
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Permission::getCode);
        wrapper.in(Permission::getId, permissionIds);
        List<Permission> permissions = permissionMapper.selectList(wrapper);
        return permissions.stream().map(Permission::getCode).collect(Collectors.toList());
    }

    public void deleteKeysByRoleId(Integer roleId) {
        String key = String.format(ROLE_PERMISSION_KEY, roleId);
        stringRedisTemplate.delete(key);
    }
}
