package {{ cookiecutter.basePackage }}.biz.auth.password.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;

/**
 * 使用验证码方式修改密码
 */
@Data
public class ChangeByCodeRequest {

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
