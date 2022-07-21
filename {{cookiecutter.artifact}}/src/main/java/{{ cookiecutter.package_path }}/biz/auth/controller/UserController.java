package {{ cookiecutter.basePackage }}.biz.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import {{ cookiecutter.basePackage }}.biz.auth.entity.Group;
import {{ cookiecutter.basePackage }}.biz.auth.entity.User;
import {{ cookiecutter.basePackage }}.biz.auth.request.ListAuthRequest;
import {{ cookiecutter.basePackage }}.biz.auth.service.IUserService;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.common.response.PageVO;
import {{ cookiecutter.basePackage }}.common.util.PageRequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/auth")
public class UserController {

    IUserService thisService;

    public UserController(IUserService thisService) {
        this.thisService = thisService;
    }

    /**
     * 分页查询用户
     */
    @Secured(value = "ROLE_admin")
    @GetMapping("/users")
    public ApiResponse<PageVO<User>> list(@Valid ListAuthRequest request) {
        // 模糊查询
        QueryWrapper<Group> wrapper = new QueryWrapper<>();
        wrapper.likeRight(StringUtils.isNotBlank(request.getName()), "name", request.getName());
        IPage<User> page = PageRequestUtil.checkForMp(request);
        IPage<User> list = thisService.page(page);
        return PageRequestUtil.extractFromMp(list);
    }


    /**
     * 新增用户
     */
    @Secured(value = "ROLE_admin")
    @PostMapping("/users")
    public ApiResponse<User> add(@Valid @RequestBody User entity) {
        boolean success = thisService.save(entity);
        if (success) {
            return new ApiResponse<>(entity);
        }
        return new ApiResponse<>();
    }


    /**
     * 按ID查询用户
     *
     * @param userId 用户ID
     */
    @Secured(value = "ROLE_admin")
    @GetMapping("/users/{userId}")
    public ApiResponse<User> get(@PathVariable Long userId) {
        return new ApiResponse<>(thisService.getById(userId));
    }

    /**
     * 按ID更新用户
     */
    @Secured(value = "ROLE_admin")
    @PutMapping("/users/{id}")
    public ApiResponse<Object> update(@Valid @RequestBody User entity) {
        boolean success = thisService.updateById(entity);
        if (success) {
            return new ApiResponse<>("更新成功");
        }
        return new ApiResponse<>("更新失败");
    }

    /**
     * 按ID删除用户
     *
     * @param userId 用户ID
     */
    @Secured(value = "ROLE_admin")
    @DeleteMapping("/users/{userId}")
    public ApiResponse<Object> delete(@PathVariable Long userId) {
        boolean success = thisService.delete(userId);
        if (success) {
            return new ApiResponse<>("删除成功", true);
        }
        return new ApiResponse<>("删除失败", false);
    }

}
