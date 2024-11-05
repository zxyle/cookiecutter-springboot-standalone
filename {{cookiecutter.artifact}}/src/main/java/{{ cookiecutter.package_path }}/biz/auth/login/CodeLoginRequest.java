package {{ cookiecutter.basePackage }}.biz.auth.login;

import lombok.Data;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;

/**
 * 邮箱或手机号 + 验证码登录请求
 */
@Data
public class CodeLoginRequest {

    /**
     * 账号（只支持邮箱或手机号）
     *
     * @mock xxx@example.com
     */
    @NotBlank(message = "账号不能为空")
    private String account;

    /**
     * 验证码
     *
     * @mock 123456
     */
    @NotBlank(message = "验证码不能为空")
    private String code;
}
