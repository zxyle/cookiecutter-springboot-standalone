package {{ cookiecutter.basePackage }}.biz.auth.login;

import {{ cookiecutter.basePackage }}.common.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;

/**
 * 使用注册账号(用户名/邮箱/手机号) + 密码方式登录
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AccountLoginRequest extends BaseRequest {

    /**
     * 注册账号（支持用户名/邮箱/手机号）
     *
     * @mock 13512345678
     */
    @NotBlank(message = "注册账号不能为空")
    @Length(min = 5, message = "注册账号长度为5个字符以上")
    private String account;

    /**
     * 密码
     *
     * @mock 12345678
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 验证码ID
     *
     * @mock 1a38695e
     */
    @Length(min = 8, max = 8, message = "验证码ID长度为8个字符")
    private String captchaId;

    /**
     * 短信/邮件/图形验证码结果
     *
     * @mock 123123
     */
    @Length(min = 4, max = 6, message = "验证码结果长度为4-6个字符")
    private String code;

}
