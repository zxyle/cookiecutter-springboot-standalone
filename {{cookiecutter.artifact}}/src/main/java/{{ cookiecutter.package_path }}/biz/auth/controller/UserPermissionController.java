package {{ cookiecutter.basePackage }}.biz.auth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import {{ cookiecutter.basePackage }}.biz.auth.entity.UserPermission;
import {{ cookiecutter.basePackage }}.biz.auth.service.IUserPermissionService;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.common.response.PageVO;
import {{ cookiecutter.basePackage }}.common.util.PageRequestUtil;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户-权限关联
 */
@RestController
@RequestMapping("/auth")
public class UserPermissionController {

    IUserPermissionService thisService;

    public UserPermissionController(IUserPermissionService thisService) {
        this.thisService = thisService;
    }

    /**
     * 分页查询用户权限
     */
    @GetMapping("/users/{userId}/permissions")
    public ApiResponse<PageVO<UserPermission>> list(@Valid PaginationRequest request, @PathVariable Long userId) {
        IPage<UserPermission> page = PageRequestUtil.checkForMp(request);
        IPage<UserPermission> list = thisService.pageRelation(userId, 0L, page);
        return PageRequestUtil.extractFromMp(list);
    }


    /**
     * 用户新增权限
     */
    @PostMapping("/users/{userId}/permissions")
    public ApiResponse<UserPermission> add(@PathVariable Long userId, @Valid @RequestBody UserPermission entity) {
        Long permissionId = entity.getPermissionId();
        if (permissionId != null) {
            boolean success = thisService.createRelation(userId, permissionId);
            if (success) {
                return new ApiResponse<>("新增成功", true);
            }
        }
        return new ApiResponse<>("新增失败", false);
    }

    /**
     * 删除用户权限
     */
    @DeleteMapping("/users/{userId}/permissions/{permissionId}")
    public ApiResponse<Object> delete(@PathVariable Long userId, @PathVariable Long permissionId) {
        boolean success = thisService.deleteRelation(userId, permissionId);
        if (success) {
            return new ApiResponse<>("删除成功");
        }
        return new ApiResponse<>("删除失败", false);
    }

}
