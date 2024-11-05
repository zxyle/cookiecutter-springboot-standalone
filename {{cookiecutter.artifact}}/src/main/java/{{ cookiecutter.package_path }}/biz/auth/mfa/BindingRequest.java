package {{ cookiecutter.basePackage }}.biz.auth.mfa;

import lombok.Data;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;

/**
 * 绑定账号请求
 */
@Data
public class BindingRequest {

    /**
     * 待绑定的手机号或邮箱
     *
     * @mock 13512345678
     */
    @NotBlank(message = "账号不能为空")
    private String account;

    /**
     * 短信或邮箱验证码
     *
     * @mock 123123
     */
    @NotBlank(message = "验证码不能为空")
    private String code;

    /**
     * 已绑定账号
     *
     * @mock 13812345678
     */
    private String oldAccount;

    /**
     * 已绑定账号验证码
     *
     * @mock 123123
     */
    private String oldCode;

}
