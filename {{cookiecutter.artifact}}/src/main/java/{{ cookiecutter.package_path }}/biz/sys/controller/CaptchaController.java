package {{ cookiecutter.basePackage }}.biz.sys.controller;

import {{ cookiecutter.basePackage }}.biz.sys.service.CaptchaPair;
import {{ cookiecutter.basePackage }}.biz.sys.service.CaptchaService;
import {{ cookiecutter.basePackage }}.biz.sys.util.CaptchaUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 图形验证码
 */
@RestController
@RequestMapping("/sys/captcha")
@Validated
public class CaptchaController {

    // 其他实现类 kaptchaServiceImpl
    @Autowired
    @Qualifier("patchcaServiceImpl")
    CaptchaService captchaService;

    @Autowired
    HttpSession httpSession;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 生成base64格式图形验证码
     */
    @GetMapping("/generate")
    public ResponseEntity<String> generate() {
        String type = "captcha";
        if (isLocked(type)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        String key = getCaptchaKey();
        CaptchaPair captchaPair = captchaService.generate();
        stringRedisTemplate.opsForValue().set(key, captchaPair.getCode(), 60, TimeUnit.SECONDS);
        httpSession.setAttribute("needVerify", true);
        locked(type, 1);
        return ResponseEntity.ok(captchaPair.getB64Image());
    }


    /**
     * 生成图形验证码
     */
    @GetMapping("/captchaImage")
    public void get(HttpServletResponse response) throws IOException {
        // 设置响应头
        response.setHeader("Pragma", "no-cache");
        // 设置响应头
        response.setHeader("Cache-Control", "no-cache");
        // 在代理服务器端防止缓冲
        response.setDateHeader("Expires", 0);
        // 设置响应内容类型
        response.setContentType("image/jpeg");
        String key = getCaptchaKey();
        CaptchaPair captchaPair = captchaService.generate();
        stringRedisTemplate.opsForValue().set(key, captchaPair.getCode(), 60, TimeUnit.SECONDS);
        response.getOutputStream().write(captchaPair.getBytes());
        response.getOutputStream().flush();
    }


    /**
     * 校验短信、图形验证码
     *
     * @param code 用户输入结果
     */
    @GetMapping("/verify")
    public boolean verify(@NotBlank String code) {
        String key = getCaptchaKey();
        String correctResult = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(correctResult) &&
                code.trim().equalsIgnoreCase(correctResult)) {
            httpSession.setAttribute("needVerify", false);
            stringRedisTemplate.delete(key);
            return true;
        }

        return false;
    }

    /**
     * 发送短信验证码
     *
     * @param phone 手机号
     */
    @GetMapping("/send")
    public ResponseEntity<Boolean> send(@NotBlank @Length(max = 11, min = 11) String phone) {
        String type = "sms";
        if (isLocked(type)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        String key = getCaptchaKey();

        // 生成随机数字
        String code = CaptchaUtil.randCode(6);

        // 写入redis
        stringRedisTemplate.opsForValue().set(key, code, 15, TimeUnit.MINUTES);

        // 调用发送短信接口, 写入到消息队列中
        locked(type, 60);
        return ResponseEntity.ok(true);
    }

    // 防刷限制
    public void locked(String type, int seconds) {
        String lockKey = getLockKey(type);
        String code = "1";
        stringRedisTemplate.opsForValue().set(lockKey, code, seconds, TimeUnit.SECONDS);
    }

    public boolean isLocked(String type) {
        String lockKey = getLockKey(type);
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(lockKey));
    }

    public String getLockKey(String type) {
        String sessionId = httpSession.getId();
        return "lock:".concat(sessionId).concat(":").concat(type);
    }

    public String getCaptchaKey() {
        String sessionId = httpSession.getId();
        return "captcha:".concat(sessionId);
    }

}
