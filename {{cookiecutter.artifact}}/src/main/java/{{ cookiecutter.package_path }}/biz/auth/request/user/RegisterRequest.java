package {{ cookiecutter.basePackage }}.biz.auth.request.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterRequest {

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
     * 手机号
     */
    @NotBlank
    private String mobile;

    /**
     * 图形或短信验证码
     */
    private String code;
}
