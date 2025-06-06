package {{ cookiecutter.basePackage }}.biz.auth.user.group;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import {{ cookiecutter.basePackage }}.biz.auth.group.Group;
import {{ cookiecutter.basePackage }}.biz.auth.user.User;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * 用户-用户组关联 服务实现类
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "UserGroupCache")
public class UserGroupService extends ServiceImpl<UserGroupMapper, UserGroup> {

    /**
     * 删除映射关系
     *
     * @param userId  用户ID
     * @param groupId 用户组ID
     */
    @Caching(evict = {
            @CacheEvict(key = "#userId + ':' + null"),
            @CacheEvict(key = "null + ':' + #groupId"),
            @CacheEvict(key = "'users:group' + #groupId"),
            @CacheEvict(key = "'groups:user' + #userId")
    })
    public boolean deleteRelation(Integer userId, Integer groupId) {
        if (countRelation(userId, groupId) == 0) {
            return true;
        }

        return remove(buildWrapper(userId, groupId));
    }

    /**
     * 查询映射关系
     *
     * @param userId  用户ID
     * @param groupId 用户组ID
     */
    @Cacheable(key = "#userId + ':' + #groupId", unless = "#result == null")
    public List<UserGroup> queryRelation(Integer userId, Integer groupId) {
        LambdaQueryWrapper<UserGroup> wrapper = buildWrapper(userId, groupId);
        wrapper.select(UserGroup::getUserId, UserGroup::getGroupId);
        return list(wrapper);
    }

    /**
     * 统计映射关系
     *
     * @param userId  用户ID
     * @param groupId 用户组ID
     */
    public Long countRelation(Integer userId, Integer groupId) {
        return count(buildWrapper(userId, groupId));
    }

    /**
     * 创建映射关系
     *
     * @param userId  用户ID
     * @param groupId 用户组ID
     */
    @Caching(evict = {
            @CacheEvict(key = "#userId + ':' + null"),
            @CacheEvict(key = "null + ':' + #groupId"),
            @CacheEvict(key = "'users:group' + #groupId"),
            @CacheEvict(key = "'groups:user' + #userId")
    })
    public boolean createRelation(Integer userId, Integer groupId) {
        if (countRelation(userId, groupId) > 0) {
            return true;
        }

        return save(new UserGroup(userId, groupId));
    }

    /**
     * 更新映射关系
     *
     * @param userId   用户id
     * @param groupIds 用户组id集合
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateRelation(Integer userId, Set<Integer> groupIds) {
        if (CollectionUtils.isEmpty(groupIds) || userId == null || userId == 0) {
            return;
        }

        remove(buildWrapper(userId, null));
        for (Integer groupId : groupIds) {
            createRelation(userId, groupId);
        }
    }

    /**
     * 构建wrapper
     *
     * @param userId  用户ID
     * @param groupId 用户组ID
     * @return wrapper
     */
    private LambdaQueryWrapper<UserGroup> buildWrapper(Integer userId, Integer groupId) {
        LambdaQueryWrapper<UserGroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(userId != null && userId != 0, UserGroup::getUserId, userId);
        wrapper.eq(groupId != null && groupId != 0, UserGroup::getGroupId, groupId);
        return wrapper;
    }

    /**
     * 查询用户所属的用户组
     *
     * @param userId 用户ID
     */
    @Cacheable(key = "'groups:user' + #userId", unless = "#result == null")
    public List<Group> findGroupsByUserId(Integer userId) {
        return baseMapper.listGroups(userId);
    }

    /**
     * 查询用户组下的用户
     *
     * @param groupId 用户组ID
     */
    @Cacheable(key = "'users:group' + #groupId", unless = "#result == null")
    public List<User> findUsersByGroupId(Integer groupId) {
        return baseMapper.listUsers(groupId);
    }
}
