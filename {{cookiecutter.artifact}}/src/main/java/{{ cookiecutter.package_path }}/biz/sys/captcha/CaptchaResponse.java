package {{ cookiecutter.basePackage }}.biz.sys.captcha;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 图形验证码响应
 */
@Data
@AllArgsConstructor
public class CaptchaResponse {

    /**
     * 图形验证码ID
     *
     * @mock 1a38695e74b748ae7b48791f8d81531d
     */
    private String captchaId;

    /**
     * base64编码的图形验证码
     *
     * @mock data:image/png;base64,iVBORw0KGgoAAAANSUh...
     */
    private String captcha;
}
