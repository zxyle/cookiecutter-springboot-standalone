package {{ cookiecutter.basePackage }}.biz.user.request;

import {{ cookiecutter.basePackage }}.common.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginRequest extends BaseRequest {

    /**
     * 用户名
     *
     * @mock admin
     * @since v1.0
     */
    @NotNull
    private String username;

    /**
     * 密码
     *
     * @mock 12345678
     * @since v1.0
     */
    private String password;
}
