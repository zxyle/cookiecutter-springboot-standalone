package {{ cookiecutter.basePackage }}.biz.auth.controller;

import {{ cookiecutter.basePackage }}.biz.auth.entity.User;
import {{ cookiecutter.basePackage }}.biz.auth.request.password.ForgetByPhoneRequest;
import {{ cookiecutter.basePackage }}.biz.auth.request.password.ModifyByOldRequest;
import {{ cookiecutter.basePackage }}.biz.auth.service.IUserService;
import {{ cookiecutter.basePackage }}.biz.auth.service.LoginService;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

/**
 * 密码管理
 */
@RestController
@RequestMapping("/auth/password")
@Slf4j
public class PasswordController extends AuthBaseController {

    IUserService userService;

    PasswordEncoder passwordEncoder;

    LoginService loginService;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    public PasswordController(PasswordEncoder passwordEncoder, LoginService loginService, IUserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.loginService = loginService;
        this.userService = userService;
    }

    /**
     * 修改密码
     */
    @PostMapping("/change")
    public ApiResponse<Object> change(@Valid @RequestBody ModifyByOldRequest request) {
        User user = getLoggedInUser();

        if (null != user && passwordEncoder.matches(request.getOldPassword(), user.getPwd())) {
            boolean s1 = userService.changePwd(user.getId(), passwordEncoder.encode(request.getNewPassword()));
            // 退出当前登录状态
            boolean s2 = loginService.logout();
            return new ApiResponse<>(s1 && s2);
        }

        return new ApiResponse<>("修改密码失败", false);
    }

    /**
     * 忘记/找回/重置密码(通过短信验证码)
     */
    @PostMapping("/forget")
    public ApiResponse<Object> forget(@Valid ForgetByPhoneRequest request) {
        boolean success;
        String key = "code:" + request.getMobile();
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(key);
        String code = (String) entries.get("code");
        Long userId = (Long) entries.get("userId");
        if (userId != null && StringUtils.isNotBlank(code) && request.getCode().equalsIgnoreCase(code)) {
            success = userService.changePwd(userId, passwordEncoder.encode(request.getNewPassword()));
            return new ApiResponse<>(success);
        }

        return new ApiResponse<>(false);
    }

}
