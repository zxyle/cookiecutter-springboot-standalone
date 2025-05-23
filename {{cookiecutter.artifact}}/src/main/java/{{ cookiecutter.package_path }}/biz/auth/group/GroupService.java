package {{ cookiecutter.basePackage }}.biz.auth.group;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {{ cookiecutter.basePackage }}.common.constant.AuthConst;
import {{ cookiecutter.basePackage }}.biz.auth.group.permission.GroupPermissionService;
import {{ cookiecutter.basePackage }}.biz.auth.group.role.GroupRoleService;
import {{ cookiecutter.basePackage }}.biz.auth.permission.Permission;
import {{ cookiecutter.basePackage }}.biz.auth.user.UserResponse;
import {{ cookiecutter.basePackage }}.biz.auth.role.Role;
import {{ cookiecutter.basePackage }}.biz.auth.user.User;
import {{ cookiecutter.basePackage }}.biz.auth.user.group.UserGroup;
import {{ cookiecutter.basePackage }}.biz.auth.user.group.UserGroupService;
import {{ cookiecutter.basePackage }}.common.entity.LiteEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户组信息 服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "GroupCache")
public class GroupService extends ServiceImpl<GroupMapper, Group> {

    final GroupPermissionService groupPermissionService;
    final GroupRoleService groupRoleService;
    final UserGroupService userGroupService;

    /**
     * 创建用户组
     *
     * @param group 用户组对象
     */
    @Cacheable(key = "#result.id", unless = "#result == null")
    public Group create(Group group) {
        // 判断同级下, 是否有同名用户组
        if (count(group.getParentId(), group.getName()) > 0) {
            log.info("{} 同级同名用户组已存在", group.getName());
            return null;
        }

        // 计算用户组排序
        if (null == group.getSort()) {
            // 排序号 = 最大排序号 + 1
            Integer maxSort = baseMapper.findMaxSortNum(group.getParentId());
            group.setSort(maxSort + 1);
        }
        baseMapper.insert(group);
        return group;
    }

    /**
     * 获取该用户组下 子用户组数量
     *
     * @param parentId 上级用户组ID
     * @param name     用户组名称
     * @return 数量
     */
    public Long count(Integer parentId, String name) {
        LambdaQueryWrapper<Group> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Group::getParentId, parentId);
        wrapper.eq(StringUtils.isNotBlank(name), Group::getName, name);
        return baseMapper.selectCount(wrapper);
    }

    /**
     * 获取该用户组下所有子用户组ID（包括自身）
     */
    @Cacheable(cacheNames = "subGroupsCache", key = "#rootGroupId", unless = "#result == null")
    public List<Integer> getSubGroups(Integer rootGroupId) {
        List<Group> groups = getAllChildren(null, rootGroupId);
        return groups.stream().map(LiteEntity::getId).distinct().{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};
    }

    /**
     * 删除用户组及关联关系
     *
     * @param groupId 用户组ID
     */
    @CacheEvict(key = "#groupId")
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Integer groupId) {
        boolean s1 = groupPermissionService.deleteRelation(groupId, 0);
        boolean s2 = groupRoleService.deleteRelation(groupId, 0);
        boolean s3 = userGroupService.deleteRelation(0, groupId);
        boolean s4 = removeById(groupId);
        return (s1 && s2) && (s3 && s4);
    }

    /**
     * 获取用户组树
     *
     * @param rootId 根节点ID
     */
    public List<Tree<Integer>> getTree(Integer rootId) {
        // 查询所有数据
        LambdaQueryWrapper<Group> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Group::getId, Group::getCreateTime, Group::getParentId, Group::getName, Group::getSort);
        wrapper.eq(Group::getParentId, rootId);
        List<Group> list = list(wrapper);

        TreeNodeConfig config = new TreeNodeConfig();
        config.setNameKey("name");
        config.setIdKey("id");
        config.setWeightKey("sort");
        return cn.hutool.core.lang.tree.TreeUtil.build(list, rootId, config, (object, tree) -> {
            tree.setId(object.getId());// 必填属性
            tree.setParentId(object.getParentId());// 必填属性
            tree.setName(object.getName());
            tree.putExtra("sort", object.getSort());
            tree.putExtra("createTime", object.getCreateTime());
        });
    }

    /**
     * 获取所有子用户组（包含自身）
     *
     * @param groups  用户组列表
     * @param groupId 根节点ID
     */
    public List<Group> getAllChildren(List<Group> groups, Integer groupId) {
        if (CollectionUtils.isEmpty(groups)){
            LambdaQueryWrapper<Group> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(Group::getId, Group::getParentId);
            groups = list(wrapper);
        }

        List<Group> children = new ArrayList<>();
        for (Group group : groups) {
            if (group.getParentId().equals(groupId)) {
                children.add(group);
                children.addAll(getAllChildren(groups, group.getId()));
            }
        }

        // 添加自身
        Group group = new Group();
        group.setId(groupId);
        children.add(group);
        return children;
    }

    public boolean isAllowed(Integer actionUserId, Integer acceptUserId, Integer acceptGroupId) {
        // 查询当前用户有管理员权限的用户组
        LambdaQueryWrapper<UserGroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(UserGroup::getUserId, UserGroup::getGroupId);
        wrapper.eq(UserGroup::getUserId, actionUserId);
        wrapper.eq(UserGroup::getAdmin, AuthConst.ENABLED);
        List<UserGroup> userGroups = userGroupService.list(wrapper);
        if (CollectionUtils.isEmpty(userGroups)) {
            return false;
        }

        List<Group> allGroups = new ArrayList<>();
        LambdaQueryWrapper<Group> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.select(Group::getId, Group::getParentId);
        List<Group> groups = list(wrapper1);
        // 获取所有子用户组
        for (UserGroup userGroup : userGroups) {
            allGroups.addAll(getAllChildren(groups, userGroup.getGroupId()));
        }

        // 去重
        List<Integer> allGroupIds = allGroups.stream()
                .map(Group::getId).distinct().{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};

        if (acceptUserId != null) {
            // 查询该用户所在的用户组
            List<UserGroup> userGroups1 = userGroupService.queryRelation(acceptUserId, null);
            if (CollectionUtils.isEmpty(userGroups1)) {
                return false;
            }

            // 判断该用户是否在这些用户组下
            for (UserGroup userGroup : userGroups1) {
                if (allGroupIds.contains(userGroup.getGroupId())) {
                    return true;
                }
            }
        }

        if (acceptGroupId != null) {
            // 判断parentId是否在其中
            return allGroupIds.contains(acceptGroupId);
        }
        return false;
    }

    /**
     * 更新用户组关联关系
     *
     * @param groupId       用户组id
     * @param roleIds       角色id集合
     * @param permissionIds 权限id集合
     */
    public void updateRelation(Integer groupId, Set<Integer> roleIds, Set<Integer> permissionIds) {
        groupPermissionService.updateRelation(groupId, permissionIds);
        groupRoleService.updateRelation(groupId, roleIds);
    }

    /**
     * 获取用户组详细信息
     *
     * @param group 用户组对象
     */
    public GroupResponse attachGroupInfo(Group group, boolean full) {
        GroupResponse response = new GroupResponse();
        BeanUtils.copyProperties(group, response);

        if (full) {
            List<Permission> permissions = groupPermissionService.findPermissionsByGroupId(group.getId());
            response.setPermissions(CollectionUtils.isNotEmpty(permissions) ? permissions : null);

            List<Role> roles = groupRoleService.findRolesByGroupId(group.getId());
            response.setRoles(CollectionUtils.isNotEmpty(roles) ? roles : null);

            List<User> users = userGroupService.findUsersByGroupId(group.getId());
            // 对用户信息进行脱敏处理
            List<UserResponse> collect = users.stream()
                    .map(UserResponse::new).{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};
            response.setUsers(CollectionUtils.isNotEmpty(collect) ? collect : null);
        }

        return response;
    }

    /**
     * 判断用户组是否已经被使用
     *
     * @param groupId 用户组ID
     */
    public boolean isAlreadyUsed(Integer groupId) {
        if (userGroupService.countRelation(0, groupId) > 0) {
            return true;
        }

        return count(groupId, null) > 0;
    }

    /**
     * 按ID查询（查询结果不为null则缓存）
     */
    @Cacheable(key = "#id", unless = "#result == null")
    public Group findById(Integer id) {
        return getById(id);
    }
}
