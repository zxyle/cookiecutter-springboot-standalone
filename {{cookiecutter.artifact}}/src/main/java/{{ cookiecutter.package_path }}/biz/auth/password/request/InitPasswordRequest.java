package {{ cookiecutter.basePackage }}.biz.auth.password.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;

/**
 * 用户初始化密码
 */
@Data
public class InitPasswordRequest {

    /**
     * 新密码（长度需要8~32位）
     *
     * @mock lHfxoPrKOaWjSqwN
     */
    @Length(min = 8, max = 32, message = "新密码长度需要8~32位")
    @NotBlank(message = "新密码不能为空")
    private String password;
}
