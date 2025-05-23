package {{ cookiecutter.basePackage }}.biz.sys.captcha;

import {{ cookiecutter.basePackage }}.biz.auth.mfa.AccountUtil;
import {{ cookiecutter.basePackage }}.biz.sys.captcha.sms.ShortMessageService;
import {{ cookiecutter.basePackage }}.biz.sys.setting.SettingService;
import {{ cookiecutter.basePackage }}.common.aspect.ApiPolicy;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import {{ cookiecutter.basePackage }}.common.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import {{ cookiecutter.namespace }}.servlet.http.HttpServletRequest;
import {{ cookiecutter.namespace }}.servlet.http.HttpServletResponse;
import {{ cookiecutter.namespace }}.validation.Valid;
import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码管理
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/captcha")
public class CaptchaController {

    final StringRedisTemplate stringRedisTemplate;
    final SettingService setting;
    final EmailCodeService emailService;
    final CodeService codeService;
    final ValidateService validateService;
    final ShortMessageService shortMessageService;

    /**
     * 生成base64编码图形验证码
     */
    @ApiPolicy(anon = true, noRes = true)
    @GetMapping("/generate")
    public R<CaptchaResponse> generate() {
        CaptchaPair captchaPair = codeService.generate();
        CaptchaResponse response = new CaptchaResponse(captchaPair.getCaptchaId(), captchaPair.getB64Image());
        log.info("已生成验证码: {}", captchaPair);
        return R.ok(response);
    }

    /**
     * 生成图形验证码
     */
    @ApiPolicy(anon = true, noRes = true)
    @GetMapping("/captchaImage")
    public void get(HttpServletResponse response) throws IOException {
        // 在代理服务器端防止缓冲
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        CaptchaPair captchaPair = codeService.generate();
        response.setHeader("captchaId", captchaPair.getCaptchaId());
        response.getOutputStream().write(captchaPair.getBytes());
        response.getOutputStream().flush();
        log.info("已生成验证码: {}", captchaPair);
    }

    /**
     * 校验短信、图形验证码
     *
     * @param code      用户输入结果
     * @param captchaId 图形验证码id
     */
    @GetMapping("/verify")
    public R<Boolean> verify(@NotBlank String code, @NotBlank String captchaId) {
        boolean verify = codeService.verify(code, captchaId);
        return R.result(verify);
    }

    // 安全隐患: 如果一个人频繁使用别人邮箱或手机号, 限定一个ip地址一天只能发送10次验证码

    /**
     * 发送短信或邮件验证码
     *
     * @apiNote 1. 验证码60秒有效期内不再发送 2. 需先校验图形验证码
     */
    @GetMapping("/send")
    public R<Boolean> send(@Valid SendCodeRequest req, HttpServletRequest servletRequest) {
        String account = req.getAccount();

        // 验证码60秒有效期内不再发送
        String key = "code:" + account;
        Long expire = stringRedisTemplate.getExpire(key);
        if (stringRedisTemplate.hasKey(key) && expire > 60) {
            return R.ok("验证码仍在有效期内");
        }

        if (!codeService.verify(req.getCode(), req.getCaptchaId())) {
            return R.fail("图形验证码错误");
        }

        if (isLocked(account)) {
            return R.fail("请求验证码频繁");
        }

        String code = validateService.send(account);
        if (AccountUtil.isEmail(account)) {
            // 调用邮件发送方法
            emailService.sendVerificationCode(account, code);
        }

        if (AccountUtil.isMobile(account)) {
            // 调用发送短信接口, 写入到消息队列中
            shortMessageService.send(account, code);
        }

        String ipAddr = IpUtil.getIpAddr(servletRequest);
        locked(account, setting.get("captcha.between").getIntValue(), ipAddr);
        return R.ok("验证码发送成功");
    }

    // 防止验证码被滥用
    public void locked(String account, Integer between, String ipAddr) {
        String key = String.format("locked:%s:%s", ipAddr, account);
        stringRedisTemplate.opsForValue().set(key, "lock", between, TimeUnit.SECONDS);
    }

    public boolean isLocked(String account) {
        String key = "locked:" + account;
        return stringRedisTemplate.hasKey(key);
    }

}
