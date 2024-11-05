package {{ cookiecutter.basePackage }}.biz.auth.mfa;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.biz.auth.mfa.totp.Totp;
import {{ cookiecutter.basePackage }}.biz.auth.user.User;
import {{ cookiecutter.basePackage }}.biz.auth.mfa.totp.TotpService;
import {{ cookiecutter.basePackage }}.biz.auth.user.UserService;
import {{ cookiecutter.basePackage }}.biz.sys.captcha.ValidateService;
import {{ cookiecutter.basePackage }}.biz.auth.mfa.totp.Authenticator;
import {{ cookiecutter.basePackage }}.biz.auth.mfa.totp.TotpResponse;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.validation.Valid;
import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;
import java.util.concurrent.TimeUnit;

/**
 * 多因子认证方式管理
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MFAController extends AuthBaseController {

    final UserService userService;
    final ValidateService validateService;
    final TotpService totpService;
    final StringRedisTemplate stringRedisTemplate;

    /**
     * 绑定手机号或邮箱
     *
     * @apiNote 该接口需先请求发送验证码接口
     */
    @LogOperation(name = "绑定手机号或邮箱", biz = "auth")
    @PostMapping("/code/bind")
    public R<Void> codeBind(@Valid @RequestBody BindingRequest req) {
        User user = getLoggedInUser();

        // 校验是否已解绑
        if ((AccountUtil.isEmail(req.getAccount()) && StringUtils.isNotBlank(user.getEmail())) ||
                (AccountUtil.isMobile(req.getAccount()) && StringUtils.isNotBlank(user.getMobile()))) {
            String key = "code:" + req.getOldAccount();
            if (!validateService.validate(key, req.getOldCode())) {
                return R.fail("解绑失败");
            }
        }

        String account = req.getAccount();
        if (userService.findByAccount(account) != null) {
            return R.fail("该账号已绑定其他用户");
        }

        // 获取redis中验证码, 校验是否正确
        String key = "code:" + account;
        if (!validateService.validate(key, req.getCode())) {
            return R.fail("验证码错误");
        }

        // 更新用户信息
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(AccountUtil.isMobile(account), User::getMobile, account);
        wrapper.set(AccountUtil.isEmail(account), User::getEmail, account);
        wrapper.eq(User::getId, getUserId());
        boolean success = userService.update(wrapper);
        return success ? R.ok("绑定成功") : R.fail("绑定失败");
    }

    /**
     * 生成TOTP随机密钥
     */
    @LogOperation(name = "生成TOTP随机密钥", biz = "auth")
    @GetMapping("/totp/generate")
    public R<TotpResponse> generate() {
        String domain = "www.example.com";  // 需修改
        String username = getCurrentUsername();
        TotpResponse response = Authenticator.generate(username, domain);
        String key = "totp:" + getUserId();
        stringRedisTemplate.opsForValue().set(key, response.getSecret(), 3, TimeUnit.MINUTES);
        return R.ok(response);
    }

    /**
     * 绑定TOTP验证器
     *
     * @param code 动态验证码
     */
    @LogOperation(name = "绑定TOTP验证器", biz = "auth")
    @GetMapping("/totp/bind")
    public R<Void> bind(@NotBlank String code) {
        String key = "totp:" + getUserId();
        Totp result = totpService.findByUserId(getUserId());
        if (result != null) {
            return R.ok("用户已绑定");
        }

        String secret = stringRedisTemplate.opsForValue().get(key);
        if (secret == null) {
            return R.fail("请先生成随机密钥");
        }

        if (!Authenticator.valid(secret, code)) {
            return R.fail("验证码错误，请重新输入");
        }

        Totp totp = new Totp(getUserId(), secret);
        Totp inserted = totpService.insert(totp);
        Boolean deleted = stringRedisTemplate.delete(key);
        return (inserted != null && Boolean.TRUE.equals(deleted)) ? R.ok("绑定TOTP成功") : R.fail("绑定TOTP失败");
    }

    /**
     * 解绑TOTP验证器
     *
     * @param code 动态验证码
     */
    @LogOperation(name = "解绑TOTP验证器", biz = "auth")
    @GetMapping("/totp/unbind")
    public R<Void> unbind(@NotBlank String code) {
        Totp result = totpService.findByUserId(getUserId());
        if (result == null) {
            return R.ok("用户未设置");
        }

        String secret = result.getSecret();
        if (!Authenticator.valid(secret, code)) {
            return R.fail("验证码错误，请重新输入");
        }

        boolean deleted = totpService.deleteByUserId(getUserId());
        return deleted ? R.ok("解绑TOTP成功") : R.fail("解绑TOTP失败");
    }
}
