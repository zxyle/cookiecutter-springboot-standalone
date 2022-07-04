package {{ cookiecutter.basePackage }}.biz.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import {{ cookiecutter.basePackage }}.biz.auth.entity.Group;
import {{ cookiecutter.basePackage }}.biz.auth.request.UpdateAuthRequest;
import {{ cookiecutter.basePackage }}.biz.auth.request.group.AddGroupRequest;
import {{ cookiecutter.basePackage }}.biz.auth.request.ListAuthRequest;
import {{ cookiecutter.basePackage }}.biz.auth.request.group.MigrateGroupRequest;
import {{ cookiecutter.basePackage }}.biz.auth.service.IGroupService;
import {{ cookiecutter.basePackage }}.biz.auth.service.IUserGroupService;
import {{ cookiecutter.basePackage }}.biz.sys.response.AntdTree2;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.common.response.PageVO;
import {{ cookiecutter.basePackage }}.common.util.PageRequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 用户组管理
 */
@RestController
@RequestMapping("/auth")
public class GroupController extends AuthBaseController {

    IUserGroupService userGroupService;

    IGroupService thisService;

    public GroupController(IGroupService thisService, IUserGroupService userGroupService) {
        this.thisService = thisService;
        this.userGroupService = userGroupService;
    }

    /**
     * 用户组查询
     */
    @PreAuthorize(value = "ck.hasPermit('auth:groups:list')")
    @GetMapping("/groups")
    public ApiResponse<PageVO<Group>> list(@Valid ListAuthRequest request) {
        // 模糊查询
        QueryWrapper<Group> wrapper = new QueryWrapper<>();
        wrapper.likeRight(StringUtils.isNotBlank(request.getName()), "name", request.getName());
        IPage<Group> page = PageRequestUtil.checkForMp(request);
        IPage<Group> list = thisService.page(page, wrapper);
        return PageRequestUtil.extractFromMp(list);
    }

    /**
     * 获取用户组树状结构
     */
    @PreAuthorize(value = "ck.hasPermit('auth:groups:tree')")
    @GetMapping("/groups/tree")
    public ApiResponse<AntdTree2> tree() {
        Long groupId = getCurrentGroup();
        AntdTree2 tree = thisService.getSubGroupTree(groupId);
        return new ApiResponse<>(tree);
    }

    /**
     * 新增用户组
     */
    @PreAuthorize(value = "ck.hasPermit('auth:groups:add')")
    @PostMapping("/groups")
    public ApiResponse<Object> add(@Valid @RequestBody AddGroupRequest request) {
        ApiResponse<Object> response = new ApiResponse<>();
        Group group = new Group();
        BeanUtils.copyProperties(request, group);

        // 防止将用户组创建到别的级别下
        List<String> subGroups = getSubGroupIds();
        Group result = thisService.create(subGroups, group);
        if (!ObjectUtils.isEmpty(result)) {
            response.setSuccess(true);
            response.setMessage("创建用户组成功!");
            return response;
        }
        response.setMessage("创建用户组失败!");
        return response;
    }

    /**
     * 按ID查询用户组
     *
     * @param groupId 用户组ID
     */
    @PreAuthorize(value = "ck.hasPermit('auth:groups:get')")
    @GetMapping("/groups/{groupId}")
    public ApiResponse<Group> get(@NotEmpty @PathVariable Long groupId) {
        // 判断用户是否具有访问权限
        if (isSubGroup(groupId)) {
            return new ApiResponse<>(thisService.getById(groupId));
        }
        return new ApiResponse<>("无权限查看该用户组信息");
    }

    /**
     * 按ID更新用户组
     *
     * @param groupId 用户组ID
     */
    @PreAuthorize(value = "ck.hasPermit('auth:groups:update')")
    @PutMapping("/groups/{groupId}")
    public ApiResponse<Group> update(@PathVariable Long groupId, @Valid @RequestBody UpdateAuthRequest request) {
        Group group = new Group();
        BeanUtils.copyProperties(request, group);
        if (isSubGroup(groupId)) {
            group.setId(groupId);
            thisService.updateById(group);
            return new ApiResponse<>(thisService.getById(groupId));
        }
        return new ApiResponse<>("无权限修改此用户组信息");
    }

    /**
     * 按ID删除用户组
     *
     * @param groupId 用户组ID
     */
    @PreAuthorize(value = "ck.hasPermit('auth:groups:delete')")
    @DeleteMapping("/groups/{groupId}")
    public ApiResponse<Object> delete(@PathVariable Long groupId) {
        ApiResponse<Object> response = new ApiResponse<>();

        if (!isSubGroup(groupId)) {
            response.setMessage("无权限删除");
            return response;
        }

        // 判断该用户组下是否有子用户组
        Integer groupNum = thisService.count(groupId);

        // 判断该用户组下是否有用户
        Integer userNum = userGroupService.countRelation(0L, groupId);
        if ((groupNum + userNum) > 0) {
            response.setMessage("该用户组下还存在子用户组或用户");
            return response;
        }

        boolean success = thisService.delete(groupId);
        // TODO 需要清空tree接口的缓存
        response.setSuccess(success);
        if (success) {
            response.setMessage("删除用户组成功!");
        } else
            response.setMessage("删除用户组失败!");

        return response;
    }

    /**
     * 用户组迁移
     */
    @PreAuthorize(value = "ck.hasPermit('auth:groups:migrate')")
    @PostMapping("/groups/migrate")
    public ApiResponse<Object> migrate(@Valid @RequestBody MigrateGroupRequest request) {
        ApiResponse<Object> response = new ApiResponse<>();

        if (!isSubGroup(request.getParentId()) || !isSubGroup(request.getCurrentId())) {
            response.setSuccess(false);
            response.setMessage("无权限移动到该用户组");
            return response;
        }

        Group group = new Group();
        group.setId(request.getCurrentId());
        group.setParentId(request.getParentId());
        boolean success = thisService.updateById(group);
        response.setSuccess(success);
        response.setMessage("移动用户组成功.");
        return response;
    }

}
