package {{ cookiecutter.basePackage }}.biz.auth.password.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 重置密码响应
 */
@Data
@AllArgsConstructor
public class ResetPasswordResponse {

    /**
     * 新密码
     *
     * @mock lHfxoPrKOaWjSqwN
     */
    private String newPassword;
}
