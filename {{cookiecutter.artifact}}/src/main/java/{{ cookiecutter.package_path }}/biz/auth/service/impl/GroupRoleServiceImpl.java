package {{ cookiecutter.basePackage }}.biz.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {{ cookiecutter.basePackage }}.biz.auth.entity.GroupRole;
import {{ cookiecutter.basePackage }}.biz.auth.mapper.GroupRoleMapper;
import {{ cookiecutter.basePackage }}.biz.auth.service.IGroupRoleService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 组角色关联 服务实现类
 */
@Service
public class GroupRoleServiceImpl extends ServiceImpl<GroupRoleMapper, GroupRole> implements IGroupRoleService {

    /**
     * 查询用户组角色
     *
     * @param groupId 用户组ID
     */
    @Override
    public List<Long> selectRoleByGroup(Long groupId) {
        List<GroupRole> groupRoles = queryRelation(groupId, 0L);
        return groupRoles.stream().map(GroupRole::getRoleId).collect(Collectors.toList());
    }

    /**
     * 删除映射关系
     *
     * @param groupId 用户组ID
     * @param roleId  角色ID
     */
    @Override
    public boolean deleteRelation(Long groupId, Long roleId) {
        return remove(buildWrapper(groupId, roleId));
    }

    /**
     * 查询映射关系
     *
     * @param groupId 用户组ID
     * @param roleId  角色ID
     */
    @Override
    public List<GroupRole> queryRelation(Long groupId, Long roleId) {
        QueryWrapper<GroupRole> wrapper = buildWrapper(groupId, roleId);
        wrapper.select("group_id, role_id");
        return list(wrapper);
    }

    /**
     * 统计映射关系
     *
     * @param groupId 用户组ID
     * @param roleId  角色ID
     */
    @Override
    public Integer countRelation(Long groupId, Long roleId) {
        return count(buildWrapper(groupId, roleId));
    }

    /**
     * 创建映射关系
     *
     * @param groupId 用户组ID
     * @param roleId  角色ID
     */
    @Override
    public boolean createRelation(Long groupId, Long roleId) {
        GroupRole entity = new GroupRole(groupId, roleId);
        try {
            save(entity);
        } catch (DuplicateKeyException ignored) {
        }
        return true;
    }

    /**
     * 分页查询映射关系
     *
     * @param groupId 用户组ID
     * @param roleId  角色ID
     */
    @Override
    public IPage<GroupRole> pageRelation(Long groupId, Long roleId, IPage<GroupRole> iPage) {
        QueryWrapper<GroupRole> wrapper = buildWrapper(groupId, roleId);
        wrapper.select("group_id, role_id");
        return page(iPage, wrapper);
    }

    // 构建wrapper
    public QueryWrapper<GroupRole> buildWrapper(Long groupId, Long roleId) {
        QueryWrapper<GroupRole> wrapper = new QueryWrapper<>();
        wrapper.eq(groupId != 0L, "group_id", groupId);
        wrapper.eq(roleId != 0L, "role_id", roleId);
        return wrapper;
    }
}
