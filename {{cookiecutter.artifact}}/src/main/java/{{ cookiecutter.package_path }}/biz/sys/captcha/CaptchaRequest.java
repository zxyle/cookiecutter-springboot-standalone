package {{ cookiecutter.basePackage }}.biz.sys.captcha;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;
import {{ cookiecutter.namespace }}.validation.constraints.NotNull;

/**
 * 验证码请求，解决前端无法获取captchaId的问题
 */
@Data
public class CaptchaRequest {

    /**
     * 验证码ID（前端随机产生uuid传入，无中横线）
     */
    @NotBlank(message = "验证码ID不能为空")
    @Length(min = 32, max = 32, message = "验证码ID长度不能超过32个字符")
    private String captchaId;

    /**
     * 随机浮点数（前端随机产生传入,可以使用Math.random()）
     */
    @NotNull(message = "随机浮点数不能为空")
    private Double random;
}
