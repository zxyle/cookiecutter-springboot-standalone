package {{ cookiecutter.basePackage }}.biz.auth.user.register;

import {{ cookiecutter.basePackage }}.biz.auth.user.User;
import {{ cookiecutter.basePackage }}.biz.auth.user.request.RandomUsernameRequest;
import {{ cookiecutter.basePackage }}.biz.auth.login.LoginResponse;
import {{ cookiecutter.basePackage }}.biz.auth.user.UserService;
import {{ cookiecutter.basePackage }}.biz.sys.captcha.CodeService;
import {{ cookiecutter.basePackage }}.biz.auth.login.LoginService;
import {{ cookiecutter.basePackage }}.biz.sys.captcha.ValidateService;
import {{ cookiecutter.basePackage }}.biz.auth.mfa.AccountUtil;
import {{ cookiecutter.basePackage }}.biz.sys.setting.SettingService;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.validation.Valid;
import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 注册管理
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/user")
public class RegisterController {

    final LoginService loginService;
    final SettingService setting;
    final ValidateService validateService;
    final CodeService codeService;
    final UserService userService;
    final PasswordEncoder encoder;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public R<LoginResponse> register(@Valid @RequestBody RegisterRequest req) {
        String account = req.getAccount();
        R<LoginResponse> r = beforeRegister(req);
        if (!r.isSuccess()) {
            return r;
        }

        // 创建用户
        User user = userService.create(account, encoder.encode(req.getPassword()));
        boolean success = userService.save(user);
        if (!success) {
            return R.fail("注册账号失败");
        }

        // 赋予默认角色
        Integer defaultRole = setting.get("auth.user.default-role").getIntValue();
        userService.updateRelation(user.getId(), Collections.singleton(defaultRole), null, null);

        // 自动登录
        if (setting.get("auth.user.auto-login").isReal()) {
            LoginResponse loginResponse = loginService.login(account, req.getPassword());
            return R.ok(loginResponse);
        }
        return R.ok("注册账号成功");
    }

    /**
     * 邀请注册
     */
    @PostMapping("/invite")
    public void invite() {
        // 待开发
    }

    /**
     * 检查账号名占用
     *
     * @param account 注册账号|jack
     */
    @PreAuthorize("@ck.hasPermit('auth:user:check')")
    @GetMapping("/check")
    public R<Boolean> check(@NotBlank String account) {
        if (userService.findByAccount(account) == null) {
            return R.ok("可以注册");
        }
        return R.fail("账号名已经被占用，请更换账号名重试");
    }

    /**
     * 随机生成用户名
     */
    @GetMapping("/random")
    @PreAuthorize("@ck.hasPermit('auth:user:random')")
    public R<List<String>> random(@Valid RandomUsernameRequest req) {
        List<String> list = new ArrayList<>();
        // 有待进一步增强该接口
        list.add(req.getPrefix() + "_001");
        return R.ok(list);
    }

    /**
     * 用户注册前判断
     */
    private R<LoginResponse> beforeRegister(RegisterRequest req) {
        String account = req.getAccount();
        // 检查是否开放注册
        if (!setting.get("auth.user.open-registration").isReal()) {
            return R.fail("系统未开放注册");
        }

        // 校验验证码是否正确
        if (AccountUtil.isUsername(account)) {
            if (!codeService.verify(req.getCode(), req.getCaptchaId())) {
                return R.fail("验证码可能错误或过期");
            }
        } else {
            String key = "code:" + account;
            if (!validateService.validate(key, req.getCode())) {
                return R.fail("验证码可能错误或过期");
            }
        }

        // 检查账号是否已被占用
        if (userService.findByAccount(account) != null) {
            return R.fail("账号已被占用");
        }

        return R.ok("注册账号成功");
    }
}
