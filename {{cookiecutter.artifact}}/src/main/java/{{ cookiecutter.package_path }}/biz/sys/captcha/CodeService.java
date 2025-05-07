package {{ cookiecutter.basePackage }}.biz.sys.captcha;

import {{ cookiecutter.basePackage }}.biz.sys.captcha.kaptcha.KaptchaServiceImpl;
import {{ cookiecutter.basePackage }}.biz.sys.captcha.patchca.PatchcaServiceImpl;
import {{ cookiecutter.basePackage }}.biz.sys.setting.SettingService;
import com.google.code.kaptcha.Producer;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 验证码服务
 */
@Service
@RequiredArgsConstructor
public class CodeService {

    final StringRedisTemplate stringRedisTemplate;
    final SettingService settingService;
    final Producer captchaProducer;

    /**
     * 生成验证码
     */
    public CaptchaPair send() {
        CaptchaService captchaService;
        String kind = settingService.get("captcha.kind").getStr();
        switch (kind) {
            case "patchca":
                captchaService = new PatchcaServiceImpl(settingService);
                break;
            case "kaptcha":
            default:
                captchaService = new KaptchaServiceImpl(settingService, captchaProducer);
                break;
        }

        CaptchaPair captchaPair = captchaService.generate();
        String redisKey = settingService.get("captcha.key-prefix").getStr() + captchaPair.getCaptchaId();
        String code = captchaPair.getCode();
        int aliveTime = settingService.get("captcha.alive-time").getIntValue();
        stringRedisTemplate.opsForValue().set(redisKey, code, aliveTime, TimeUnit.MINUTES);
        return captchaPair;
    }

    /**
     * 校验验证码
     *
     * @param captchaId 验证码ID
     * @param code      验证码
     * @return 校验结果 true-通过、 false-不通过
     */
    public boolean verify(String code, String captchaId) {
        if (!settingService.get("captcha.on").isReal()) {
            return true;
        }

        if (StringUtils.isBlank(code) || StringUtils.isBlank(captchaId)) {
            return false;
        }

        String redisKey = settingService.get("captcha.key-prefix").getStr() + captchaId;
        Boolean hasKey = stringRedisTemplate.hasKey(redisKey);
        if (!hasKey) {
            return false;
        }

        String captchaRedis = stringRedisTemplate.opsForValue().get(redisKey);
        if (StringUtils.isBlank(captchaRedis)) {
            return false;
        }

        boolean match = captchaRedis.trim().equalsIgnoreCase(code);
        if (match) {
            stringRedisTemplate.delete(captchaId);
        }
        return match;
    }
}
