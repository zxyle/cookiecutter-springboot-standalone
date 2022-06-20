package {{ cookiecutter.basePackage }}.biz.auth.controller;

import {{ cookiecutter.basePackage }}.biz.auth.service.IPermissionService;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import com.baomidou.mybatisplus.core.metadata.IPage;
import {{ cookiecutter.basePackage }}.biz.auth.entity.UserRole;
import {{ cookiecutter.basePackage }}.biz.auth.service.IUserRoleService;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.common.response.PageVO;
import {{ cookiecutter.basePackage }}.common.util.PageRequestUtil;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 用户和角色关联表
 */
@RestController
@RequestMapping("/auth")
public class UserRoleController extends AuthBaseController {

    IUserRoleService thisService;

    IPermissionService permissionService;

    public UserRoleController(IUserRoleService thisService, IPermissionService permissionService) {
        this.thisService = thisService;
        this.permissionService = permissionService;
    }

    /**
     * 分页查询用户的角色信息
     */
    @Secured(value = "ROLE_admin")
    @GetMapping("/users/{userId}/roles")
    public ApiResponse<PageVO<UserRole>> list(@Valid PaginationRequest request, @NotNull @PathVariable Long userId) {
        IPage<UserRole> page = PageRequestUtil.checkForMp(request);
        IPage<UserRole> list = thisService.pageRelation(userId, 0L, page);
        return PageRequestUtil.extractFromMp(list);
    }


    /**
     * 用户新增角色
     */
    @Secured(value = "ROLE_admin")
    @PostMapping("/users/{userId}/roles")
    public ApiResponse<UserRole> add(@Valid @RequestBody UserRole entity, @NotNull @PathVariable Long userId) {
        Long roleId = entity.getRoleId();
        if (roleId != null) {
            thisService.createRelation(userId, roleId);
            permissionService.refreshPermissions(userId);
            return new ApiResponse<>("新增成功", true);
        }
        return new ApiResponse<>("新增失败", false);
    }

    /**
     * 用户删除角色
     */
    @Secured(value = "ROLE_admin")
    @DeleteMapping("/users/{userId}/roles/{roleId}")
    public ApiResponse<Object> delete(@NotNull @PathVariable Long userId, @NotNull @PathVariable Long roleId) {
        boolean success = thisService.deleteRelation(userId, roleId);
        if (success) {
            permissionService.refreshPermissions(userId);
            return new ApiResponse<>("删除成功");
        }
        return new ApiResponse<>("删除失败", false);
    }

}
