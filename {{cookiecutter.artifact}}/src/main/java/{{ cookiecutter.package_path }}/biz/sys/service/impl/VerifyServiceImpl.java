package {{ cookiecutter.basePackage }}.biz.sys.service.impl;

import {{ cookiecutter.basePackage }}.biz.sys.service.VerifyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class VerifyServiceImpl implements VerifyService {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    /**
     * 校验验证码
     *
     * @param captcha   验证码
     * @param captchaId 验证码ID
     * @return 校验结果
     */
    @Override
    public boolean verify(String captcha, String captchaId) {
        if (StringUtils.isEmpty(captcha) || StringUtils.isEmpty(captchaId)) {
            throw new IllegalArgumentException("验证码不能为空");
        }

        String captchaRedis = stringRedisTemplate.opsForValue().get(captchaId);
        if (captchaRedis == null) {
            return false;
        }
        boolean b = captchaRedis.trim().equalsIgnoreCase(captcha);
        if (b) {
            stringRedisTemplate.delete(captchaId);
        }
        return b;
    }
}
