package {{ cookiecutter.basePackage }}.biz.sys.captcha;

import {{ cookiecutter.basePackage }}.biz.sys.setting.SettingService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * 短信邮件验证码服务
 */
@Service
@RequiredArgsConstructor
public class ValidateService {

    final StringRedisTemplate stringRedisTemplate;
    final SettingService settingService;

    /**
     * 校验验证码
     *
     * @param key  验证码key
     * @param code 用户输入的验证码
     */
    public boolean validate(String key, String code) {
        // 检查参数是否为空
        if (StringUtils.isBlank(key) || StringUtils.isBlank(code)) {
            return false;
        }

        // 检查key是否存在
        Boolean hasKey = stringRedisTemplate.hasKey(key);
        if (Boolean.FALSE.equals(hasKey)) {
            return false;
        }

        // 检查验证码是否正确
        String truthCode = (String) stringRedisTemplate.opsForHash().get(key, "code");
        if (StringUtils.isBlank(truthCode) || !truthCode.equalsIgnoreCase(code)) {
            return false;
        }

        // 删除验证码
        stringRedisTemplate.delete(key);
        return true;
    }

    public String send(String account) {
        String key = "code:" + account;
        // 生成随机数字
        String code = CaptchaUtil.randNumber(settingService.get("captcha.digits").getIntValue());
        stringRedisTemplate.opsForHash().put(key, "account", account);
        stringRedisTemplate.opsForHash().put(key, "code", code);
        int aliveTime = settingService.get("captcha.alive-time").getIntValue();
        stringRedisTemplate.expire(key, Duration.ofMinutes(aliveTime));
        return code;
    }
}
