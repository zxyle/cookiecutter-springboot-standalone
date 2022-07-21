package {{ cookiecutter.basePackage }}.biz.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {{ cookiecutter.basePackage }}.biz.auth.entity.Group;
import {{ cookiecutter.basePackage }}.biz.auth.mapper.GroupMapper;
import {{ cookiecutter.basePackage }}.biz.auth.service.IGroupPermissionService;
import {{ cookiecutter.basePackage }}.biz.auth.service.IGroupRoleService;
import {{ cookiecutter.basePackage }}.biz.auth.service.IGroupService;
import {{ cookiecutter.basePackage }}.biz.auth.service.IUserGroupService;
import {{ cookiecutter.basePackage }}.biz.sys.response.AntdTree2;
import {{ cookiecutter.basePackage }}.common.util.AreaNode;
import {{ cookiecutter.basePackage }}.common.util.TreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户组信息 服务实现类
 */
@Service
@Slf4j
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements IGroupService {

    IGroupPermissionService groupPermissionService;

    IGroupRoleService groupRoleService;

    IUserGroupService userGroupService;

    public GroupServiceImpl(IGroupPermissionService groupPermissionService, IGroupRoleService groupRoleService, IUserGroupService userGroupService) {
        this.groupPermissionService = groupPermissionService;
        this.groupRoleService = groupRoleService;
        this.userGroupService = userGroupService;
    }

    /**
     * 创建用户组
     *
     * @param subGroups 子用户组ID
     * @param group     用户组对象
     */
    @Override
    public Group create(List<String> subGroups, Group group) {
        if (!subGroups.contains(String.valueOf(group.getParentId()))) {
            log.info("无法在该用户组下创建子用户组");
            return null;
        }

        // 判断同级下, 是否有同名用户组
        boolean exist = exist(group.getName(), group.getParentId());
        if (exist) {
            log.info("同名用户组已存在");
            return null;
        }

        // 获取用户组排序
        if (null == group.getSortNo()) {
            Integer count = count(group.getParentId());
            group.setSortNo(count + 1);
        }
        baseMapper.insert(group);
        return group;
    }

    /**
     * 判断用户组是否存在
     *
     * @param name     用户组名称
     * @param parentId 上级用户组ID
     * @return 是否存在
     */
    @Override
    public boolean exist(String name, Long parentId) {
        QueryWrapper<Group> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        wrapper.eq("name", name);
        Integer count = baseMapper.selectCount(wrapper);
        return count != 0;
    }

    /**
     * 获取该用户组下 子用户组数量
     *
     * @param parentId 上级用户组ID
     * @return 数量
     */
    @Override
    public Integer count(Long parentId) {
        QueryWrapper<Group> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        return baseMapper.selectCount(wrapper);
    }

    /**
     * 获取该用户组下所有子用户组ID（包括自身）
     */
    @Cacheable(cacheNames = "subGroupsCache", key = "#rootGroupId")
    @Override
    public List<String> getSubGroups(Long rootGroupId) {
        AntdTree2 tree = getSubGroupTree(rootGroupId);
        List<String> subGroups = TreeUtil.getAll(tree);
        subGroups.add(String.valueOf(rootGroupId));
        return subGroups;
    }

    /**
     * 获取该用户组下所有子用户组树
     */
    @Cacheable(cacheNames = "groupCache", key = "#rootGroupId")
    @Override
    public AntdTree2 getSubGroupTree(Long rootGroupId) {
        Group group = baseMapper.selectById(rootGroupId);
        String rootId = String.valueOf(rootGroupId);
        String rootName = group.getName();
        String rootKey = String.valueOf(rootGroupId);

        QueryWrapper<Group> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name", "parent_id");
        List<Group> groups = baseMapper.selectList(queryWrapper);
        List<AreaNode> nodes = groups.stream()
                .map(node -> new AreaNode(node.getName(), String.valueOf(node.getParentId()), String.valueOf(node.getId())))
                .collect(Collectors.toList());

        AntdTree2 tree = new AntdTree2();
        List<AntdTree2> listTree = TreeUtil.createTree(nodes, rootId);
        tree.setValue(rootKey);
        tree.setLabel(rootName);
        tree.setChildren(listTree);
        return tree;
    }

    /**
     * 删除用户组
     *
     * @param groupId 用户组ID
     */
    @Transactional
    @Override
    public boolean delete(Long groupId) {
        boolean s1 = groupPermissionService.deleteRelation(groupId, 0L);
        boolean s2 = groupRoleService.deleteRelation(groupId, 0L);
        boolean s3 = userGroupService.deleteRelation(0L, groupId);
        boolean s4 = removeById(groupId);
        return (s1 && s2) && (s3 && s4);
    }
}
