package {{ cookiecutter.basePackage }}.biz.auth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import {{ cookiecutter.basePackage }}.biz.auth.entity.UserRole;
import {{ cookiecutter.basePackage }}.biz.auth.service.IUserRoleService;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.common.response.PageVO;
import {{ cookiecutter.basePackage }}.common.util.PageRequestUtil;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户和角色关联表
 */
@RestController
@RequestMapping("/auth")
public class UserRoleController {

    IUserRoleService thisService;

    public UserRoleController(IUserRoleService thisService) {
        this.thisService = thisService;
    }

    /**
     * 分页查询用户的角色信息
     */
    @GetMapping("/users/{userId}/roles")
    public ApiResponse<PageVO<UserRole>> list(@Valid PaginationRequest request, @PathVariable Long userId) {
        IPage<UserRole> page = PageRequestUtil.checkForMp(request);
        IPage<UserRole> list = thisService.pageRelation(userId, 0L, page);
        return PageRequestUtil.extractFromMp(list);
    }


    /**
     * 用户新增角色
     */
    @PostMapping("/users/{userId}/roles")
    public ApiResponse<UserRole> add(@Valid @RequestBody UserRole entity, @PathVariable Long userId) {
        Long roleId = entity.getRoleId();
        if (roleId != null) {
            thisService.createRelation(userId, roleId);
            return new ApiResponse<>("新增成功", true);
        }
        return new ApiResponse<>("新增失败", false);
    }

    /**
     * 用户删除角色
     */
    @DeleteMapping("/users/{userId}/roles/{roleId}")
    public ApiResponse<Object> delete(@PathVariable Long userId, @PathVariable Long roleId) {
        boolean success = thisService.deleteRelation(userId, roleId);
        if (success) {
            return new ApiResponse<>("删除成功");
        }
        return new ApiResponse<>("删除失败", false);
    }

}
