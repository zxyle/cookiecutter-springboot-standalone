package {{ cookiecutter.basePackage }}.biz.sys.captcha;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;

/**
 * 发送短信或邮件验证码
 */
@Data
public class SendCodeRequest {

    /**
     * 注册账号（支持输入手机号、邮箱）
     *
     * @mock 13512345678
     */
    @NotBlank(message = "注册账号不能为空")
    @Length(min = 5, message = "注册账号长度为5个字符以上")
    private String account;

    /**
     * 验证码ID
     *
     * @mock 1a38695e74b748ae7b48791f8d81531d
     */
    @NotBlank(message = "验证码ID不能为空")
    @Length(min = 32, max = 32, message = "验证码ID长度为32个字符")
    private String captchaId;

    /**
     * 图形验证码答案
     *
     * @mock 123123
     */
    @NotBlank(message = "图形验证码不能为空")
    private String code;

}
