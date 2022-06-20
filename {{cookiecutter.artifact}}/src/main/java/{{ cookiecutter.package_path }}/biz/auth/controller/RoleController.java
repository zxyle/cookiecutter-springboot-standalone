package {{ cookiecutter.basePackage }}.biz.auth.controller;

import {{ cookiecutter.basePackage }}.biz.auth.service.IPermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import {{ cookiecutter.basePackage }}.biz.auth.entity.Role;
import {{ cookiecutter.basePackage }}.biz.auth.request.ListAuthRequest;
import {{ cookiecutter.basePackage }}.biz.auth.request.role.AddRoleRequest;
import {{ cookiecutter.basePackage }}.biz.auth.service.IRoleService;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.common.response.PageVO;
import {{ cookiecutter.basePackage }}.common.util.PageRequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色信息
 */
@RestController
@RequestMapping("/auth")
public class RoleController extends AuthBaseController {

    IRoleService thisService;

    IPermissionService permissionService;

    public RoleController(IRoleService thisService, IPermissionService permissionService) {
        this.thisService = thisService;
        this.permissionService = permissionService;
    }


    /**
     * 角色列表查询
     */
    @GetMapping("/roles")
    @PreAuthorize(value = "hasAuthority('roles-list')")
    public ApiResponse<PageVO<Role>> list(@Valid ListAuthRequest request) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        // 模糊查询
        wrapper.likeRight(StringUtils.isNotBlank(request.getName()), "name", request.getName());
        IPage<Role> page = PageRequestUtil.checkForMp(request);
        IPage<Role> list = thisService.page(page, wrapper);
        return PageRequestUtil.extractFromMp(list);
    }


    /**
     * 新增角色
     */
    @PostMapping("/roles")
    @PreAuthorize(value = "hasAuthority('roles-add')")
    public ApiResponse<Role> add(@Valid @RequestBody AddRoleRequest request) {
        Role role = new Role();
        BeanUtils.copyProperties(request, role);
        boolean success = thisService.save(role);
        return new ApiResponse<>(success);
    }


    /**
     * 按ID查询角色
     */
    @GetMapping("/roles/{roleId}")
    @PreAuthorize(value = "hasAuthority('roles-get')")
    public ApiResponse<Role> get(@NotNull @PathVariable Long roleId) {
        return new ApiResponse<>(thisService.queryById(roleId));
    }

    /**
     * 按ID更新角色
     */
    @PutMapping("/roles/{roleId}")
    @PreAuthorize(value = "hasAuthority('roles-update')")
    public ApiResponse<Object> update(@Valid @RequestBody Role entity, @NotNull @PathVariable Long roleId) {
        entity.setId(roleId);
        boolean success = thisService.updateById(entity);
        // 刷新持有该角色的用户权限缓存（改为异步操作）
        List<Long> users = getUsersByRole(roleId);
        users.forEach(userId -> permissionService.refreshPermissions(userId));
        return new ApiResponse<>(success);
    }

    /**
     * 按ID删除角色
     */
    @DeleteMapping("/roles/{roleId}")
    @PreAuthorize(value = "hasAuthority('roles-delete')")
    public ApiResponse<Object> delete(@NotNull @PathVariable Long roleId) {
        boolean success = thisService.delete(roleId);
        List<Long> users = getUsersByRole(roleId);
        users.forEach(userId -> permissionService.refreshPermissions(userId));
        return new ApiResponse<>(success);
    }

}
