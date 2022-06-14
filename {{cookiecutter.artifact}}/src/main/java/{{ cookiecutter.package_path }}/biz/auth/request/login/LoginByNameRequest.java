package {{ cookiecutter.basePackage }}.biz.auth.request.login;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginByNameRequest {
    // TODO 支持邮箱 手机号

    /**
     * 用户名
     */
    @NotBlank
    private String loginName;

    /**
     * 密码
     */
    @NotBlank
    private String password;

    /**
     * 短信或者邮件验证码
     */
    private String code;

    /**
     * 记住我(Y N)
     */
    private String rememberMe;

    /**
     *
     */
    private boolean autoLogin;

    /**
     *
     */
    private String type;

    /**
     *
     */
    private String username;

}
