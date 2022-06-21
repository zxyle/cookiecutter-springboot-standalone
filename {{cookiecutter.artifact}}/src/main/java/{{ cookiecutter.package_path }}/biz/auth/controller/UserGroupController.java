package {{ cookiecutter.basePackage }}.biz.auth.controller;

import cn.hutool.core.util.ObjectUtil;
import {{ cookiecutter.basePackage }}.biz.auth.service.IPermissionService;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import com.baomidou.mybatisplus.core.metadata.IPage;
import {{ cookiecutter.basePackage }}.biz.auth.entity.User;
import {{ cookiecutter.basePackage }}.biz.auth.entity.UserGroup;
import {{ cookiecutter.basePackage }}.biz.auth.mapper.UserGroupMapper;
import {{ cookiecutter.basePackage }}.biz.auth.request.user.AddUserRequest;
import {{ cookiecutter.basePackage }}.biz.auth.response.UserVO;
import {{ cookiecutter.basePackage }}.biz.auth.service.IUserGroupService;
import {{ cookiecutter.basePackage }}.biz.auth.service.IUserService;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.common.response.PageVO;
import {{ cookiecutter.basePackage }}.common.util.PageRequestUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户-用户组关联
 */
@RestController
@RequestMapping("/auth")
public class UserGroupController extends AuthBaseController {

    UserGroupMapper thisMapper;

    IUserGroupService thisService;

    IUserService userService;

    IPermissionService permissionService;

    public UserGroupController(UserGroupMapper thisMapper, IUserGroupService thisService, IPermissionService permissionService, IUserService userService) {
        this.thisMapper = thisMapper;
        this.thisService = thisService;
        this.permissionService = permissionService;
        this.userService = userService;
    }

    /**
     * 分页查询用户 所属用户组
     *
     * @param userId 用户ID
     */
    @Secured(value = "ROLE_admin")
    @GetMapping("/users/{userId}/groups")
    public ApiResponse<PageVO<UserGroup>> list(@Valid PaginationRequest request, @NotNull @PathVariable Long userId) {
        IPage<UserGroup> page = PageRequestUtil.checkForMp(request);
        IPage<UserGroup> list = thisService.pageRelation(userId, 0L, page);
        return PageRequestUtil.extractFromMp(list);
    }


    /**
     * 新增用户
     *
     * @param groupId 用户组ID
     */
    @Secured(value = "ROLE_admin")
    @PostMapping("/groups/{groupId}/users")
    public ApiResponse<Object> add(@Valid @RequestBody AddUserRequest request, @NotNull @PathVariable Long groupId) {
        request.setGroupId(groupId);
        User user = userService.addUser(request);
        if (ObjectUtil.isNotNull(user)) {
            return new ApiResponse<>("创建成功");
        }
        return new ApiResponse<>();
    }


    /**
     * 用户删除所属用户组关系
     *
     * @param userId  用户ID
     * @param groupId 用户组ID
     */
    @Secured(value = "ROLE_admin")
    @DeleteMapping("/users/{userId}/groups/{groupId}")
    public ApiResponse<Object> delete(@NotNull @PathVariable Long userId, @NotNull @PathVariable Long groupId) {
        boolean success = thisService.deleteRelation(userId, groupId);
        if (success) {
            permissionService.refreshPermissions(userId);
            return new ApiResponse<>("删除成功");
        }
        return new ApiResponse<>("删除失败", false);
    }


    /**
     * 查询用户组下所有用户
     *
     * @param groupId 用户组ID
     */
    @Secured(value = "ROLE_admin")
    @GetMapping("/groups/{groupId}/users")
    public ApiResponse<List<UserVO>> listUsers(@NotNull @PathVariable Long groupId) {
        List<UserVO> list = null;
        List<User> users = thisMapper.listUsers(groupId);
        if (CollectionUtils.isNotEmpty(users)) {
            list = users.stream().map(user -> {
                UserVO vo = new UserVO();
                BeanUtils.copyProperties(user, vo);
                return vo;
            }).collect(Collectors.toList());
        }
        return new ApiResponse<>(list);
    }

}
