package {{ cookiecutter.basePackage }}.biz.auth.password.request;

import {{ cookiecutter.basePackage }}.common.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;

/**
 * 忘记/找回密码请求
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ForgetRequest extends BaseRequest {

    /**
     * 注册账号（支持输入手机号/邮箱）
     *
     * @mock 13512345678
     */
    @NotBlank(message = "注册账号不能为空")
    @Length(min = 5, message = "注册账号长度为5个字符以上")
    private String account;

    /**
     * 短信或邮件验证码
     *
     * @mock 123123
     */
    @NotBlank(message = "验证码不能为空")
    private String code;

    /**
     * 新密码（长度需要8~32位）
     *
     * @mock lHfxoPrKOaWjSqwN
     */
    @NotBlank(message = "新密码不能为空")
    @Length(min = 8, max = 32, message = "新密码长度需要8~32位")
    private String newPassword;

}
