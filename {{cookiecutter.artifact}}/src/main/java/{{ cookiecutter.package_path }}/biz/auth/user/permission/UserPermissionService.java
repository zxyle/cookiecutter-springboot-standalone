package {{ cookiecutter.basePackage }}.biz.auth.user.permission;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {{ cookiecutter.basePackage }}.biz.auth.permission.Permission;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * 用户-权限关联 服务实现类
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "UserPermissionCache")
public class UserPermissionService extends ServiceImpl<UserPermissionMapper, UserPermission> {

    /**
     * 删除映射关系
     *
     * @param userId       用户ID
     * @param permissionId 权限ID
     */
    @Caching(evict = {
            @CacheEvict(key = "#userId + ':' + null"),
            @CacheEvict(key = "null + ':' + #permissionId"),
            @CacheEvict(key = "'permissions:' + #userId"),
    })
    public boolean deleteRelation(Integer userId, Integer permissionId) {
        if (countRelation(userId, permissionId) == 0) {
            return true;
        }

        return remove(buildWrapper(userId, permissionId));
    }

    /**
     * 查询映射关系
     *
     * @param userId       用户ID
     * @param permissionId 权限ID
     */
    @Cacheable(key = "#userId + ':' + #permissionId", unless = "#result == null")
    public List<UserPermission> queryRelation(Integer userId, Integer permissionId) {
        LambdaQueryWrapper<UserPermission> wrapper = buildWrapper(userId, permissionId);
        wrapper.select(UserPermission::getUserId, UserPermission::getPermissionId);
        return list(wrapper);
    }

    /**
     * 统计映射关系
     *
     * @param userId       用户ID
     * @param permissionId 权限ID
     */
    public Long countRelation(Integer userId, Integer permissionId) {
        return count(buildWrapper(userId, permissionId));
    }

    /**
     * 创建映射关系
     *
     * @param userId       用户ID
     * @param permissionId 权限ID
     */
    @Caching(evict = {
            @CacheEvict(key = "#userId + ':' + null"),
            @CacheEvict(key = "null + ':' + #permissionId"),
            @CacheEvict(key = "'permissions:' + #userId")
    })
    public boolean createRelation(Integer userId, Integer permissionId) {
        if (countRelation(userId, permissionId) > 0) {
            return true;
        }

        return save(new UserPermission(userId, permissionId));
    }

    /**
     * 更新映射关系
     *
     * @param userId        用户id
     * @param permissionIds 权限id集合
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateRelation(Integer userId, Set<Integer> permissionIds) {
        if (CollectionUtils.isEmpty(permissionIds) || userId == null || userId == 0) {
            return;
        }

        // 删除旧的关联关系
        remove(buildWrapper(userId, null));
        // 创建新的关联关系
        for (Integer permissionId : permissionIds) {
            createRelation(userId, permissionId);
        }
    }

    /**
     * 构建wrapper
     *
     * @param userId       用户ID
     * @param permissionId 权限ID
     * @return wrapper
     */
    private LambdaQueryWrapper<UserPermission> buildWrapper(Integer userId, Integer permissionId) {
        LambdaQueryWrapper<UserPermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(userId != null && userId != 0, UserPermission::getUserId, userId);
        wrapper.eq(permissionId != null && permissionId != 0, UserPermission::getPermissionId, permissionId);
        return wrapper;
    }

    /**
     * 查询用户直接拥有的权限代码列表
     *
     * @param userId 用户ID
     */
    @Cacheable(key = "'permissions:' + #userId", unless = "#result == null")
    public List<Permission> findPermissionsByUserId(Integer userId) {
        return baseMapper.findPermissionsByUserId(userId);
    }
}
