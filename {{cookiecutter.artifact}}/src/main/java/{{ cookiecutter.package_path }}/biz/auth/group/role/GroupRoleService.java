package {{ cookiecutter.basePackage }}.biz.auth.group.role;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {{ cookiecutter.basePackage }}.biz.auth.role.Role;
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
 * 组角色关联 服务实现类
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "GroupRoleCache")
public class GroupRoleService extends ServiceImpl<GroupRoleMapper, GroupRole> {

    /**
     * 删除映射关系
     *
     * @param groupId 用户组ID
     * @param roleId  角色ID
     */
    @Caching(evict = {
            @CacheEvict(key = "#groupId + ':' + null"),
            @CacheEvict(key = "null + ':' + #roleId"),
            @CacheEvict(key = "'roles:' + #groupId"),
    })
    public boolean deleteRelation(Integer groupId, Integer roleId) {
        if (countRelation(groupId, roleId) == 0) {
            return true;
        }

        return remove(buildWrapper(groupId, roleId));
    }

    /**
     * 查询映射关系
     *
     * @param groupId 用户组ID
     * @param roleId  角色ID
     */
    @Cacheable(key = "#groupId + ':' + #roleId", unless = "#result == null")
    public List<GroupRole> queryRelation(Integer groupId, Integer roleId) {
        LambdaQueryWrapper<GroupRole> wrapper = buildWrapper(groupId, roleId);
        wrapper.select(GroupRole::getGroupId, GroupRole::getRoleId);
        return list(wrapper);
    }

    /**
     * 统计映射关系
     *
     * @param groupId 用户组ID
     * @param roleId  角色ID
     */
    public Long countRelation(Integer groupId, Integer roleId) {
        return count(buildWrapper(groupId, roleId));
    }

    /**
     * 创建映射关系
     *
     * @param groupId 用户组ID
     * @param roleId  角色ID
     */
    @Caching(evict = {
            @CacheEvict(key = "#groupId + ':' + null"),
            @CacheEvict(key = "null + ':' + #roleId"),
            @CacheEvict(key = "'roles:'+#groupId")
    })
    public boolean createRelation(Integer groupId, Integer roleId) {
        if (countRelation(groupId, roleId) > 0) {
            return true;
        }

        return save(new GroupRole(groupId, roleId));
    }

    /**
     * 更新映射关系
     *
     * @param groupId 用户组id
     * @param roleIds 角色id集合
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateRelation(Integer groupId, Set<Integer> roleIds) {
        if (CollectionUtils.isEmpty(roleIds) || groupId == null || groupId == 0) {
            return;
        }

        // 删除旧的关联关系
        deleteRelation(groupId, 0);
        // 创建新的关联关系
        roleIds.forEach(roleId -> createRelation(groupId, roleId));
    }

    /**
     * 构建wrapper
     *
     * @param groupId 用户组ID
     * @param roleId  角色ID
     * @return wrapper
     */
    private LambdaQueryWrapper<GroupRole> buildWrapper(Integer groupId, Integer roleId) {
        LambdaQueryWrapper<GroupRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(groupId != null && groupId != 0, GroupRole::getGroupId, groupId);
        wrapper.eq(roleId != null && roleId != 0, GroupRole::getRoleId, roleId);
        return wrapper;
    }

    /**
     * 根据用户组ID列表查询角色列表
     */
    @Cacheable(key = "'roles:'+#groupId", unless = "#result == null")
    public List<Role> findRolesByGroupId(Integer groupId) {
        return baseMapper.findRolesByGroupId(groupId);
    }

    /**
     * 根据用户组ID列表查询角色列表
     */
    public List<Role> findRolesByGroupIds(List<Integer> groupIds) {
        return baseMapper.findRolesByGroupIds(groupIds);
    }
}
