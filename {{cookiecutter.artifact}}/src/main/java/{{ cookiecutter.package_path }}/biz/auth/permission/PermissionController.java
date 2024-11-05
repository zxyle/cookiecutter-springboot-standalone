package {{ cookiecutter.basePackage }}.biz.auth.permission;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import {{ cookiecutter.basePackage }}.biz.auth.permission.request.AddPermissionRequest;
import {{ cookiecutter.basePackage }}.biz.auth.permission.request.TreePermissionRequest;
import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.validation.Valid;
import java.util.List;

/**
 * 权限管理
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class PermissionController extends AuthBaseController {

    final PermissionService thisService;

    /**
     * 获取权限树
     *
     * @apiNote 1. 该接口只有管理员才能访问 2. 该接口返回name-节点名、id-节点值、children-子节点、path-路由、sort-排序等字段
     */
    @LogOperation(name = "获取权限树", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:permission:tree')")
    @GetMapping("/permissions/tree")
    public R<List<Tree<Integer>>> tree(@Valid TreePermissionRequest req) {
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(req.getKind() != null, Permission::getKind, req.getKind());
        List<Permission> list = thisService.list(wrapper);
        List<Tree<Integer>> tree = thisService.getTree(list, req.getRootPermissionId());
        return R.ok(tree);
    }

    /**
     * 查询用户拥有所有权限信息
     */
    @LogOperation(name = "查询用户拥有所有权限信息", biz = "auth")
    @GetMapping("/permissions")
    @PreAuthorize("@ck.hasPermit('auth:permission:list')")
    public R<List<Permission>> list() {
        List<Permission> permissions = thisService.getAllPermissions(getUserId(), getGroupIds());
        return R.ok(permissions);
    }

    /**
     * 新增权限
     */
    @LogOperation(name = "新增权限", biz = "auth")
    @PostMapping("/permissions")
    @PreAuthorize("@ck.hasPermit('auth:permission:add')")
    public R<Permission> add(@Valid @RequestBody AddPermissionRequest req) {
        Permission entity = new Permission();
        BeanUtils.copyProperties(req, entity);

        boolean success = thisService.create(entity);
        return success ? R.ok(entity) : R.fail("新增权限失败");
    }

    /**
     * 按ID查询权限
     *
     * @param permissionId 权限ID
     */
    @LogOperation(name = "按ID查询权限", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:permission:get')")
    @GetMapping("/permissions/{permissionId}")
    public R<Permission> get(@PathVariable Integer permissionId) {
        Permission entity = thisService.getById(permissionId);
        return entity == null ? R.fail("权限不存在") : R.ok(entity);
    }

    /**
     * 按ID更新权限
     *
     * @param permissionId 权限ID
     */
    @LogOperation(name = "按ID更新权限", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:permission:update')")
    @PutMapping("/permissions/{permissionId}")
    public R<Void> update(@Valid @RequestBody Permission entity, @PathVariable Integer permissionId) {
        Permission permission = thisService.getById(permissionId);
        entity.setId(permissionId);
        boolean success = thisService.updateById(entity);
        if (success) {
            List<Integer> userIds = thisService.holdPermission(permission.getCode());
            userIds.forEach(thisService::refreshPermissions);
            return R.ok("更新权限成功");
        }
        return R.fail("更新权限失败");
    }

    /**
     * 按ID删除权限
     *
     * @param permissionId 权限ID
     */
    @LogOperation(name = "按ID删除权限", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:permission:delete')")
    @DeleteMapping("/permissions/{permissionId}")
    public R<Void> delete(@PathVariable Integer permissionId) {
        if (thisService.isAlreadyUsed(permissionId)) {
            return R.fail("删除失败，该权限正在使用");
        }

        Permission permission = thisService.getById(permissionId);
        boolean success = thisService.delete(permissionId);
        if (success) {
            // 所有持有该权限的用户，都刷新权限
            List<Integer> userIds = thisService.holdPermission(permission.getCode());
            userIds.forEach(thisService::refreshPermissions);
            return R.ok("删除权限成功");
        }
        return R.fail("删除权限失败");
    }
}
