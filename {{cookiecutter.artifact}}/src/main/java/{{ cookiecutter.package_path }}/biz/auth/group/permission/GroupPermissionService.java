package {{ cookiecutter.basePackage }}.biz.auth.group.permission;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {{ cookiecutter.basePackage }}.biz.auth.permission.Permission;
import lombok.RequiredArgsConstructor;
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
 * 用户组权限 服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "GroupPermissionCache")
public class GroupPermissionService extends ServiceImpl<GroupPermissionMapper, GroupPermission> {

    /**
     * 查询用户组拥有的权限列表
     */
    @Cacheable(key = "'permissions:' + #groupId", unless = "#result == null")
    public List<Permission> findPermissionsByGroupId(Integer groupId) {
        return baseMapper.findPermissionsByGroupId(groupId);
    }

    /**
     * 删除映射关系
     *
     * @param groupId      用户组ID
     * @param permissionId 权限ID
     */
    @Caching(evict = {
            @CacheEvict(key = "#groupId + ':' + null"),
            @CacheEvict(key = "null + ':' + #permissionId"),
            @CacheEvict(key = "'permissions:' + #groupId")
    })
    public boolean deleteRelation(Integer groupId, Integer permissionId) {
        if (countRelation(groupId, permissionId) == 0) {
            return true;
        }

        return remove(buildWrapper(groupId, permissionId));
    }

    /**
     * 查询映射关系
     *
     * @param groupId      用户组ID
     * @param permissionId 权限ID
     */
    @Cacheable(key = "#groupId + ':' + #permissionId", unless = "#result == null")
    public List<GroupPermission> queryRelation(Integer groupId, Integer permissionId) {
        LambdaQueryWrapper<GroupPermission> wrapper = buildWrapper(groupId, permissionId);
        wrapper.select(GroupPermission::getGroupId, GroupPermission::getPermissionId);
        return list(wrapper);
    }

    /**
     * 统计映射关系
     *
     * @param groupId      用户组ID
     * @param permissionId 权限ID
     */
    public Long countRelation(Integer groupId, Integer permissionId) {
        return count(buildWrapper(groupId, permissionId));
    }

    /**
     * 创建映射关系
     *
     * @param groupId      用户组ID
     * @param permissionId 权限ID
     */
    @Caching(evict = {
            @CacheEvict(key = "#groupId + ':' + null"),
            @CacheEvict(key = "null + ':' + #permissionId"),
            @CacheEvict(key = "'permissions:' + #groupId")
    })
    public boolean createRelation(Integer groupId, Integer permissionId) {
        if (countRelation(groupId, permissionId) > 0) {
            return true;
        }

        return save(new GroupPermission(groupId, permissionId));
    }

    /**
     * 更新映射关系
     *
     * @param groupId       用户组id
     * @param permissionIds 权限id集合
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateRelation(Integer groupId, Set<Integer> permissionIds) {
        if (CollectionUtils.isEmpty(permissionIds) || groupId == null || groupId == 0) {
            return;
        }
        // 删除旧的关系
        deleteRelation(groupId, null);
        // 创建新的关系
        permissionIds.forEach(permissionId -> createRelation(groupId, permissionId));
    }

    /**
     * 构建wrapper
     *
     * @param groupId      用户组ID
     * @param permissionId 权限ID
     * @return wrapper
     */
    private LambdaQueryWrapper<GroupPermission> buildWrapper(Integer groupId, Integer permissionId) {
        LambdaQueryWrapper<GroupPermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(groupId != null && groupId != 0, GroupPermission::getGroupId, groupId);
        wrapper.eq(permissionId != null && permissionId != 0, GroupPermission::getPermissionId, permissionId);
        return wrapper;
    }
}
