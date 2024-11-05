package {{ cookiecutter.basePackage }}.biz.auth.profile;

import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.biz.auth.user.User;
import {{ cookiecutter.basePackage }}.biz.auth.user.UserService;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.validation.Valid;

/**
 * 用户资料管理
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class ProfileController extends AuthBaseController {

    final ProfileService thisService;
    final UserService userService;

    /**
     * 获取当前用户资料
     */
    // @LogOperation(name = "获取当前用户资料", biz = "auth")
    // @PreAuthorize("@ck.hasPermit('auth:profile:get')")
    @GetMapping("/profile")
    public R<Profile> get() {
        Integer userId = getUserId();
        return R.ok(thisService.findByUserId(userId));
    }

    /**
     * 更新当前用户资料
     */
    // @LogOperation(name = "更新当前用户资料", biz = "auth")
    // @PreAuthorize("@ck.hasPermit('auth:profile:update')")
    @PostMapping("/profile")
    public R<Profile> update(@Valid @RequestBody Profile entity) {
        Integer userId = getUserId();
        entity.setUserId(userId);
        Profile profile = thisService.updateProfile(entity);

        // 更新auth_user表中的nickname
        if (StringUtils.isNotBlank(entity.getNickname())) {
            User user = new User();
            user.setId(userId);
            user.setNickname(entity.getNickname());
            boolean result = userService.putById(user);
            if (!result) {
                log.error("更新auth_user表中的nickname失败，userId: {}", userId);
            }
        }
        return R.ok(profile);
    }
}
