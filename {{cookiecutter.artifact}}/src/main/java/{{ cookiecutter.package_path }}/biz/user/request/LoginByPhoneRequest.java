package {{ cookiecutter.basePackage }}.biz.user.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

// 手机号+短信验证码登录
@Data
public class LoginByPhoneRequest {

    /**
     * 手机号
     */
    @NotBlank
    private String phone;

    /**
     * 短信验证码
     */
    @NotBlank
    private String code;

}
