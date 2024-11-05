package {{ cookiecutter.basePackage }}.biz.sys.captcha;

import java.util.UUID;

/**
 * 图形验证码服务
 */
public interface CaptchaService {

    /**
     * 创建验证码
     *
     * @return 返回由验证码图片字节数组、验证码结果、验证码ID组成的对象
     */
    CaptchaPair generate();

    /**
     * 生成验证码ID
     */
    default String getCaptchaId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }
}
