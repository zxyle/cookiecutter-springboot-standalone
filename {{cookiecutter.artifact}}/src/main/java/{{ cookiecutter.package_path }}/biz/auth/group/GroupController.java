package {{ cookiecutter.basePackage }}.biz.auth.group;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.common.constant.AuthConst;
import {{ cookiecutter.basePackage }}.biz.auth.user.group.UserGroup;
import {{ cookiecutter.basePackage }}.common.request.auth.ListAuthRequest;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.request.auth.UpdateAuthRequest;
import {{ cookiecutter.basePackage }}.common.response.R;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户组管理
 */
@RestController
@RequestMapping("/auth")
public class GroupController extends AuthBaseController {

    /**
     * 用户组查询
     */
    @LogOperation(name = "用户组查询", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:group:list')")
    @GetMapping("/groups")
    public R<Page<GroupResponse>> list(@Valid ListAuthRequest req) {
        // 查询当前用户，所在用户组和所能管理的用户组
        LambdaQueryWrapper<Group> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Group::getId, groupService.getSubGroups(getUserId()));
        wrapper.like(StringUtils.isNotBlank(req.getKeyword()), Group::getName, req.getKeyword());
        Page<Group> page = groupService.page(req.getPage(), wrapper);
        List<GroupResponse> collect = page.getRecords().stream()
                .map(group -> groupService.attachGroupInfo(group, req.isFull()))
                .{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};
        Page<GroupResponse> responsePage = new Page<>();
        responsePage.setRecords(collect);
        responsePage.setTotal(page.getTotal());
        return R.ok(responsePage);
    }

    /**
     * 获取用户组树状结构
     *
     * @param rootId 根节点ID
     */
    @LogOperation(name = "获取用户组树状结构", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:group:tree')")
    @GetMapping("/groups/tree")
    public R<List<Tree<Integer>>> tree(@RequestParam(defaultValue = "1") Integer rootId) {
        // 查询当前用户，所在用户组作为rootId
        LambdaQueryWrapper<UserGroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserGroup::getUserId, getUserId());
        wrapper.eq(UserGroup::getAdmin, AuthConst.ENABLED);
        List<UserGroup> list = userGroupService.list(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            rootId = list.get(0).getGroupId();
        }

        List<Tree<Integer>> tree = groupService.getTree(rootId);
        return R.ok(tree);
    }

    /**
     * 创建用户组
     */
    @LogOperation(name = "创建用户组", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:group:add')")
    @PostMapping("/groups")
    public R<Group> add(@Valid @RequestBody AddGroupRequest req) {
        if (!groupService.isAllowed(getUserId(), null, req.getParentId())) {
            return R.fail("无权限创建该用户组");
        }

        Group group = new Group();
        BeanUtils.copyProperties(req, group);
        Group result = groupService.create(group);
        if (result != null) {
            groupService.updateRelation(result.getId(), req.getRoleIds(), req.getPermissionIds());
            return R.ok(result);
        }
        return R.fail("创建用户组失败");
    }

    /**
     * 按ID查询用户组
     *
     * @param groupId 用户组ID
     */
    @LogOperation(name = "按ID查询用户组", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:group:get')")
    @GetMapping("/groups/{groupId}")
    public R<GroupResponse> get(@PathVariable Integer groupId) {
        if (!groupService.isAllowed(getUserId(), null, groupId)) {
            return R.fail("无权限查询该用户组");
        }

        Group group = groupService.findById(groupId);
        return R.ok(groupService.attachGroupInfo(group, true));
    }

    /**
     * 按ID更新用户组
     *
     * @param groupId 用户组ID
     */
    @LogOperation(name = "按ID更新用户组", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:group:update')")
    @PutMapping("/groups/{groupId}")
    public R<Group> update(@PathVariable Integer groupId, @Valid @RequestBody UpdateAuthRequest req) {
        if (!groupService.isAllowed(getUserId(), null, groupId)) {
            return R.fail("无权限更新该用户组");
        }

        // 判断同级用户组下是否有名称重复
        Group group = groupService.findById(groupId);
        Long count = groupService.count(group.getParentId(), req.getName());
        if (StringUtils.isNotBlank(req.getName()) && count > 0) {
            return R.fail("同级用户组名称不能重复");
        }

        Group newGroup = new Group();
        BeanUtils.copyProperties(req, newGroup);
        newGroup.setId(groupId);
        groupService.updateById(newGroup);
        groupService.updateRelation(groupId, req.getRoleIds(), req.getPermissionIds());
        return R.ok(groupService.getById(groupId));
    }

    /**
     * 按ID删除用户组
     *
     * @param groupId 用户组ID
     */
    @LogOperation(name = "按ID删除用户组", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:group:delete')")
    @DeleteMapping("/groups/{groupId}")
    public R<Void> delete(@PathVariable Integer groupId) {
        if (!groupService.isAllowed(getUserId(), null, groupId)) {
            return R.fail("无权限删除该用户组");
        }

        // 判断该用户组下是否有子用户组
        if (groupService.isAlreadyUsed(groupId)) {
            return R.fail("该用户组下还存在子用户组");
        }

        boolean success = groupService.delete(groupId);
        return R.result(success);
    }

    /**
     * 移动用户组
     */
    @LogOperation(name = "移动用户组", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:group:migrate')")
    @PostMapping("/groups/migrate")
    public R<Void> migrate(@Valid @RequestBody MigrateGroupRequest req) {
        if (!isSubGroup(req.getParentId()) || !isSubGroup(req.getCurrentId())) {
            return R.fail("无权限移动到该用户组下");
        }

        Group group = new Group();
        group.setId(req.getCurrentId());
        group.setParentId(req.getParentId());
        // 需计算新排序
        boolean success = groupService.updateById(group);
        return R.result(success);
    }
}
