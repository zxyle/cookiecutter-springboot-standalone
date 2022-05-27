package {{ cookiecutter.basePackage }}.biz.auth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import {{ cookiecutter.basePackage }}.biz.auth.entity.RolePermission;
import {{ cookiecutter.basePackage }}.biz.auth.service.IRolePermissionService;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.common.response.PageVO;
import {{ cookiecutter.basePackage }}.common.util.PageRequestUtil;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 角色权限信息
 */
@RestController
@RequestMapping("/auth")
public class RolePermissionController {

    IRolePermissionService thisService;

    public RolePermissionController(IRolePermissionService thisService) {
        this.thisService = thisService;
    }

    /**
     * 分页查询角色权限
     */
    @GetMapping("/roles/{roleId}/permissions")
    public ApiResponse<PageVO<RolePermission>> list(@Valid PaginationRequest request, @PathVariable Long roleId) {
        IPage<RolePermission> page = PageRequestUtil.checkForMp(request);
        IPage<RolePermission> list = thisService.pageRelation(roleId, 0L, page);
        return PageRequestUtil.extractFromMp(list);
    }


    /**
     * 角色新增权限
     */
    @PostMapping("/roles/{roleId}/permissions")
    public ApiResponse<RolePermission> add(@PathVariable Long roleId, @Valid @RequestBody RolePermission entity) {
        Long permissionId = entity.getPermissionId();
        if (permissionId != null) {
            boolean success = thisService.createRelation(roleId, permissionId);
            if (success) {
                return new ApiResponse<>("新增成功", true);
            }
        }
        return new ApiResponse<>("新增失败", false);
    }

    /**
     * 删除角色权限
     */
    @DeleteMapping("/roles/{roleId}/permissions/{permissionId}")
    public ApiResponse<Object> delete(@PathVariable Long permissionId, @PathVariable Long roleId) {
        boolean success = thisService.deleteRelation(roleId, permissionId);
        if (success) {
            return new ApiResponse<>("删除成功");
        }
        return new ApiResponse<>("删除失败", false);
    }

}
