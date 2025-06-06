package {{ cookiecutter.basePackage }}.biz.auth.user.role;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {{ cookiecutter.basePackage }}.biz.auth.role.Role;
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

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 用户和角色关联表 服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "UserRoleCache")
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRole> {

    final StringRedisTemplate stringRedisTemplate;
    private static final String USER_ROLE_KEY = "user:%s:roles";

    /**
     * 删除映射关系
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    @Caching(evict = {
            @CacheEvict(key = "#userId + ':' + null"),
            @CacheEvict(key = "null + ':' + #roleId"),
            @CacheEvict(key = "'roles:' + #userId")
    })
    public boolean deleteRelation(Integer userId, Integer roleId) {
        if (countRelation(userId, roleId) == 0) {
            return true;
        }

        deleteRolesFromRedis(userId, Collections.singletonList(roleId));
        return remove(buildWrapper(userId, roleId));
    }

    /**
     * 查询映射关系
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    @Cacheable(key = "#userId + ':' + #roleId", unless = "#result == null")
    public List<UserRole> queryRelation(Integer userId, Integer roleId) {
        LambdaQueryWrapper<UserRole> wrapper = buildWrapper(userId, roleId);
        wrapper.select(UserRole::getUserId, UserRole::getRoleId);
        return list(wrapper);
    }

    /**
     * 统计映射关系
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    public Long countRelation(Integer userId, Integer roleId) {
        return count(buildWrapper(userId, roleId));
    }

    /**
     * 创建映射关系
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    @Caching(evict = {
            @CacheEvict(key = "#userId + ':' + null"),
            @CacheEvict(key = "null + ':' + #roleId"),
            @CacheEvict(key = "'roles:' + #userId")
    })
    public boolean createRelation(Integer userId, Integer roleId) {
        if (countRelation(userId, roleId) > 0) {
            return true;
        }

        saveRolesToRedis(userId, new ArrayList<>(roleId));
        return save(new UserRole(userId, roleId));
    }

    /**
     * 更新用户角色关系
     *
     * @param userId  用户id
     * @param roleIds 角色id集合
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateRelation(Integer userId, Set<Integer> roleIds) {
        if (CollectionUtils.isEmpty(roleIds) || userId == null || userId == 0) {
            return;
        }

        remove(buildWrapper(userId, null));
        for (Integer roleId : roleIds) {
            createRelation(userId, roleId);
        }
    }

    /**
     * 构建wrapper
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return wrapper
     */
    private LambdaQueryWrapper<UserRole> buildWrapper(Integer userId, Integer roleId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(userId != null && userId != 0, UserRole::getUserId, userId);
        wrapper.eq(roleId != null && roleId != 0, UserRole::getRoleId, roleId);
        return wrapper;
    }

    /**
     * 根据用户ID 查询该用户所拥有的角色信息
     *
     * @param userId 用户ID
     */
    @Cacheable(key = "'roles:' + #userId", unless = "#result == null")
    public List<Role> findRolesByUserId(Integer userId) {
        return baseMapper.findRolesByUserId(userId);
    }

    // 将用户拥有的角色ID存到redis中
    public void saveRolesToRedis(Integer userId, List<Integer> roleIds) {
        String key = String.format(USER_ROLE_KEY, userId);
        stringRedisTemplate.opsForSet().add(key, roleIds.stream().map(String::valueOf).toArray(String[]::new));
        stringRedisTemplate.expire(key, 1, TimeUnit.DAYS);
    }

    // 从redis中删除角色ID
    public void deleteRolesFromRedis(Integer userId, List<Integer> roleIds) {
        String key = String.format(USER_ROLE_KEY, userId);
        stringRedisTemplate.opsForSet().remove(key, roleIds.stream().map(String::valueOf).toArray());
    }
}
