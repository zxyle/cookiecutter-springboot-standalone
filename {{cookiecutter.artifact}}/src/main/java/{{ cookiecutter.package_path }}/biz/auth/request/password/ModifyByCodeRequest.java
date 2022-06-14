package {{ cookiecutter.basePackage }}.biz.auth.request.password;

import lombok.Data;

@Data
public class ModifyByCodeRequest {

    /**
     * 短信或者邮件验证码
     */
    private String code;

    /**
     * 新密码
     */
    private String newPassword;

}
