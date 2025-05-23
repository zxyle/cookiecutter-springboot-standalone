package {{ cookiecutter.basePackage }}.biz.auth.login;

import cn.hutool.core.util.IdUtil;
import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.biz.auth.mfa.totp.Totp;
import {{ cookiecutter.basePackage }}.biz.auth.user.User;
import {{ cookiecutter.basePackage }}.biz.auth.user.UserService;
import {{ cookiecutter.basePackage }}.biz.sys.captcha.CodeService;
import {{ cookiecutter.basePackage }}.biz.auth.mfa.totp.TotpService;
import {{ cookiecutter.basePackage }}.biz.auth.mfa.totp.Authenticator;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.servlet.http.HttpServletRequest;
import {{ cookiecutter.namespace }}.validation.Valid;
import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;
import java.util.concurrent.TimeUnit;

/**
 * 登录管理
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController extends AuthBaseController {

    final UserService userService;
    final LoginService loginService;
    final CodeService codeService;
    final TotpService totpService;
    final StringRedisTemplate stringRedisTemplate;

    /**
     * 方式一：账号（用户名/邮箱/手机号） + 密码登录
     */
    @PostMapping("/login/account")
    public R<LoginResponse> login(@Valid @RequestBody AccountLoginRequest req, HttpServletRequest servletRequest) {
        log.debug("用户登录：{}", servletRequest.getRequestURI());
        R<LoginResponse> beforeLoginResponse = beforeLogin(req);
        if (beforeLoginResponse != null) {
            return beforeLoginResponse;
        }

        LoginResponse response = loginService.login(req.getAccount(), req.getPassword());

        loginService.afterLogin(response);
        return R.ok(response, "登录成功");
    }

    /**
     * 方式二：邮箱/手机号 + 验证码登录
     */
    @PostMapping("/login/code")
    public R<LoginResponse> codeLogin(@Valid @RequestBody CodeLoginRequest req) {
        // 登录逻辑直接走SmsCodeAuthenticationFilter，无需在这里实现
        log.info("验证码登录: {}", req);
        return R.ok(null);
    }

    /**
     * 方式三：第三方账号登录
     */
    @GetMapping("/login/oauth")
    public R<LoginResponse> oauthLogin(@Valid ThirdPartyLoginRequest req) {
        log.info("三方账号登录, code: {}, provider: {}", req.getCode(), req.getProvider());
        // 该接口占位仅为生成接口文档使用，登录逻辑直接走UscCodeAuthenticationFilter即可，无需在这里实现
        return R.ok(null);
    }

    /**
     * PC端生成二维码（前端1分钟失效）
     */
    @GetMapping("/login/qrcode")
    public R<TokenResponse> qrcode() {
        String qrcode = IdUtil.fastSimpleUUID();
        stringRedisTemplate.opsForValue().set("qrcode:" + qrcode, qrcode, 1, TimeUnit.MINUTES);
        return R.ok(new TokenResponse(qrcode));
    }

    /**
     * 移动端确认扫码
     *
     * @param code 二维码
     */
    @GetMapping("/login/scan/ack")
    public R<Void> ack(@NotBlank String code) {
        User user = getLoggedInUser();
        String key = "scan:" + code;
        stringRedisTemplate.opsForValue().set(key, user.getId().toString(), 10, TimeUnit.MINUTES);
        return R.ok("确认扫码成功");
    }

    /**
     * 方式四：使用移动端扫码登录
     *
     * @param code 二维码
     */
    @GetMapping("/login/scan")
    public R<LoginResponse> scanLogin(@NotBlank String code) {
        // 1. PC网站请求该接口，生成一个随机的UUID，并将该UUID作为参数拼接到二维码的URL中
        // 2. 移动端扫码后，会请求该接口，将二维码中的UUID作为参数传递过来

        String key = "scan:" + code;
        String userId = stringRedisTemplate.opsForValue().get(key);
        if (userId == null) {
            return R.fail("二维码已过期，请刷新页面重试");
        }

        User user = userService.findById(Integer.valueOf(userId));
        if (user == null) {
            return R.fail("用户不存在");
        }

        LoginResponse response = new LoginResponse(user);
        // 3. 服务端根据UUID查询到对应的用户信息，然后将用户信息返回给移动端
        return R.ok(response);
    }

    /**
     * 方式五：使用验证器一次性密码登录
     */
    @PostMapping("/login/totp")
    public R<Void> totpLogin(@Valid @RequestBody CodeLoginRequest req) {
        User user = userService.findByAccount(req.getAccount());
        if (user == null) {
            return R.fail("用户不存在");
        }

        Totp totp = totpService.findByUserId(getUserId());
        if (totp == null) {
            return R.fail("用户未设置TOTP");
        }

        if (!Authenticator.valid(totp.getSecret(), req.getCode())) {
            return R.fail("验证码错误，请重新输入");
        }

        // TODO 登录逻辑
        return R.ok("登录成功");
    }

    /**
     * 方式六：微信小程序手机号授权登录 TODO url可能不合适 因为还有一个微信扫码登录
     */
    @PostMapping("/login/wechat")
    public R<LoginResponse> wechatLogin(@Valid @RequestBody WechatLoginRequest req) {
        // 登录逻辑直接走WechatAuthenticationFilter，无需在这里实现
        log.info("微信小程序手机号授权登录: {}", req);
        return R.ok(null);
    }

    /**
     * 方式七：本机号码一键登录
     */
    @PostMapping("/login/onekey")
    public R<LoginResponse> oneLogin(@Valid @RequestBody OneKeyLoginRequest req) {
        // 根据提供的token, 去服务提供商解密获取到手机号
        return R.ok(null);
    }

    /**
     * 退出登录
     */
    @LogOperation(name = "退出登录", biz = "auth")
    @PostMapping("/logout")
    public R<Void> logout() {
        boolean success = loginService.logout(getUserId());
        return success ? R.ok("退出登录成功") : R.fail("退出登录失败");
    }

    /**
     * 登录前条件判断
     */
    public R<LoginResponse> beforeLogin(AccountLoginRequest req) {
        // 验证码校验
        boolean verify = codeService.verify(req.getCode(), req.getCaptchaId());
        if (!verify) {
            return R.fail("验证码错误，请重新输入");
        }
        return null;
    }

}
