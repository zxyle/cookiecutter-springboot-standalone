package {{ cookiecutter.basePackage }}.biz.sys.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CaptchaResponse {

    /**
     * 图形验证码ID
     */
    private String captchaId;

    /**
     * 图形验证码Base64编码
     */
    private String captcha;
}
