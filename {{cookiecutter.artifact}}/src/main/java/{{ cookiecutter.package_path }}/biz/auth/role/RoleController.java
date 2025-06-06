package {{ cookiecutter.basePackage }}.biz.auth.role;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.biz.auth.permission.PermissionService;
import {{ cookiecutter.basePackage }}.common.request.auth.ListAuthRequest;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.response.R;
import {{ cookiecutter.basePackage }}.common.validation.Add;
import {{ cookiecutter.basePackage }}.common.validation.Update;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色管理
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RoleController extends AuthBaseController {

    final RoleService thisService;
    final PermissionService permissionService;

    /**
     * 角色列表分页查询
     */
    @LogOperation(name = "角色列表分页查询", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:role:list')")
    @GetMapping("/roles")
    public R<Page<RoleResponse>> list(@Valid ListAuthRequest req) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Role::getId, Role::getCreateTime, Role::getName, Role::getCode, Role::getDescription);
        // 模糊查询
        if (StringUtils.isNotBlank(req.getKeyword())) {
            wrapper.and(i -> i.like(Role::getName, req.getKeyword())
                    .or().like(Role::getCode, req.getKeyword())
                    .or().like(Role::getDescription, req.getKeyword()));
        }
        Page<Role> list = thisService.page(req.getPage(), wrapper);
        List<RoleResponse> roles = list.getRecords().stream()
                .map(role -> thisService.attachRoleInfo(role, req.isFull()))
                .{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};
        Page<RoleResponse> roleResponsePage = new Page<>();
        roleResponsePage.setRecords(roles);
        roleResponsePage.setTotal(list.getTotal());
        return R.ok(roleResponsePage);
    }

    /**
     * 创建角色
     */
    @LogOperation(name = "创建角色", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:role:add')")
    @PostMapping("/roles")
    public R<Role> add(@Validated(Add.class) @RequestBody RoleRequest req) {
        if (thisService.isDuplicate(req.getName(), req.getCode())) {
            return R.fail("角色名称或角色代码已重复");
        }

        Role role = new Role();
        BeanUtils.copyProperties(req, role);
        if (StringUtils.isBlank(role.getCode())) {
            role.setCode(IdUtil.simpleUUID());
        }
        boolean success = thisService.save(role);
        if (success && CollectionUtils.isNotEmpty(req.getPermissionIds())) {
            // 保存角色权限关系
            thisService.updateRelation(role.getId(), req.getPermissionIds());
        }
        return R.ok(role);
    }

    /**
     * 按ID查询角色
     *
     * @param roleId 角色ID
     */
    @LogOperation(name = "按ID查询角色", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:role:get')")
    @GetMapping("/roles/{roleId}")
    public R<RoleResponse> get(@PathVariable Integer roleId) {
        Role role = thisService.findById(roleId);
        if (role == null) {
            return R.fail("角色不存在");
        }

        // 查询角色对应权限关系
        RoleResponse response = thisService.attachRoleInfo(role, true);
        return R.ok(response);
    }

    /**
     * 按ID更新角色
     *
     * @param roleId 角色ID
     */
    @LogOperation(name = "按ID更新角色", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:role:update')")
    @PutMapping("/roles/{roleId}")
    public R<Void> update(@Validated(Update.class) @RequestBody RoleRequest req, @PathVariable Integer roleId) {
        // 更新角色信息
        Role role = new Role();
        BeanUtils.copyProperties(req, role);
        role.setId(roleId);
        boolean success = thisService.updateById(role);

        // 更新角色权限关联关系
        if (success && CollectionUtils.isNotEmpty(req.getPermissionIds())) {
            thisService.updateRelation(roleId, req.getPermissionIds());

            // 刷新持有该角色的用户权限缓存
            List<Integer> users = getUsersByRole(roleId);
            users.forEach(permissionService::refreshPermissions);
        }
        return R.ok("更新角色成功");
    }

    /**
     * 按ID删除角色
     *
     * @param roleId 角色ID
     */
    @LogOperation(name = "按ID删除角色", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:role:delete')")
    @DeleteMapping("/roles/{roleId}")
    public R<Void> delete(@PathVariable Integer roleId) {
        if (thisService.isAlreadyUsed(roleId)) {
            return R.fail("该角色正在使用，无法删除");
        }

        boolean success = thisService.delete(roleId);
        if (success) {
            // 刷新持有该角色的用户权限缓存
            List<Integer> users = getUsersByRole(roleId);
            users.forEach(permissionService::refreshPermissions);
            return R.ok("删除角色成功");
        }

        return R.fail("删除角色失败");
    }
}
