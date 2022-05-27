package {{ cookiecutter.basePackage }}.biz.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {{ cookiecutter.basePackage }}.biz.auth.entity.GroupPermission;
import {{ cookiecutter.basePackage }}.biz.auth.entity.UserGroup;
import {{ cookiecutter.basePackage }}.biz.auth.mapper.GroupPermissionMapper;
import {{ cookiecutter.basePackage }}.biz.auth.service.IGroupPermissionService;
import {{ cookiecutter.basePackage }}.biz.auth.service.IUserGroupService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户组权限 服务实现类
 */
@Service
public class GroupPermissionServiceImpl extends ServiceImpl<GroupPermissionMapper, GroupPermission> implements IGroupPermissionService {

    IUserGroupService userGroupService;

    public GroupPermissionServiceImpl(IUserGroupService userGroupService) {
        this.userGroupService = userGroupService;
    }

    /**
     * 查询用户组拥有的权限列表
     *
     * @param userId 用户ID
     */
    @Override
    public List<String> selectPermissionsByGroup(Long userId) {
        List<String> permissions = new ArrayList<>();
        List<UserGroup> groups = userGroupService.queryRelation(userId, 0L);
        groups.forEach(group -> permissions.addAll(baseMapper.getPermissionNameByGid(group.getGroupId())));
        return permissions;
    }

    /**
     * 删除映射关系
     *
     * @param groupId      用户组ID
     * @param permissionId 权限ID
     */
    @Override
    public boolean deleteRelation(Long groupId, Long permissionId) {
        return remove(buildWrapper(groupId, permissionId));
    }

    /**
     * 查询映射关系
     *
     * @param groupId      用户组ID
     * @param permissionId 权限ID
     */
    @Override
    public List<GroupPermission> queryRelation(Long groupId, Long permissionId) {
        QueryWrapper<GroupPermission> wrapper = buildWrapper(groupId, permissionId);
        wrapper.select("group_id, permission_id");
        return list(wrapper);
    }

    /**
     * 分页查询映射关系
     *
     * @param groupId      用户组ID
     * @param permissionId 权限ID
     * @param iPage        分页对象
     */
    @Override
    public IPage<GroupPermission> pageRelation(Long groupId, Long permissionId, IPage<GroupPermission> iPage) {
        QueryWrapper<GroupPermission> wrapper = buildWrapper(groupId, permissionId);
        wrapper.select("group_id, permission_id");
        return page(iPage, wrapper);
    }

    /**
     * 统计映射关系
     *
     * @param groupId      用户组ID
     * @param permissionId 权限ID
     */
    @Override
    public Integer countRelation(Long groupId, Long permissionId) {
        return count(buildWrapper(groupId, permissionId));
    }

    /**
     * 创建映射关系
     *
     * @param groupId      用户组ID
     * @param permissionId 权限ID
     */
    @Override
    public boolean createRelation(Long groupId, Long permissionId) {
        GroupPermission entity = new GroupPermission();
        entity.setPermissionId(permissionId);
        entity.setGroupId(groupId);
        try {
            save(entity);
        } catch (DuplicateKeyException ignored) {
        }
        return true;
    }

    // 构建wrapper
    public QueryWrapper<GroupPermission> buildWrapper(Long groupId, Long permissionId) {
        QueryWrapper<GroupPermission> wrapper = new QueryWrapper<>();
        wrapper.eq(groupId != 0L, "group_id", groupId);
        wrapper.eq(permissionId != 0L, "permission_id", permissionId);
        return wrapper;
    }
}
