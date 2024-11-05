package {{ cookiecutter.basePackage }}.biz.auth.user.register;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;

/**
 * 用户注册请求
 */
@Data
public class RegisterRequest {

    /**
     * 注册账号（支持输入用户名/手机号/邮箱）
     *
     * @mock 13512345678
     */
    @NotBlank(message = "注册账号不能为空")
    @Length(min = 5, max = 64, message = "注册账号长度需要5~64位")
    private String account;

    /**
     * 邮件/短信/图形验证码
     *
     * @mock 123123
     */
    @NotBlank(message = "验证码不能为空")
    private String code;

    /**
     * 密码（长度需要8~32位）
     *
     * @mock lHfxoPrKOaWjSqwN
     */
    @NotBlank(message = "密码不能为空")
    @Length(min = 8, max = 32, message = "密码长度需要8~32位")
    private String password;

    /**
     * 验证码ID（使用用户名注册时，此属性为必填）
     *
     * @mock 1a38695e74b748ae7b48791f8d81531d
     */
    @Length(min = 32, max = 32, message = "验证码ID长度为32个字符")
    private String captchaId;

}
