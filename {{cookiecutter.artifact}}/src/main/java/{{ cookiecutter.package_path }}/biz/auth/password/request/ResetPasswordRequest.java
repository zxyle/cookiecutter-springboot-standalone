package {{ cookiecutter.basePackage }}.biz.auth.password.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import {{ cookiecutter.namespace }}.validation.constraints.NotNull;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;

/**
 * 重置密码请求
 */
@Data
public class ResetPasswordRequest {

    /**
     * 用户ID
     *
     * @mock 10
     */
    @Positive(message = "用户ID必须为正整数")
    @NotNull(message = "用户ID不能为空")
    private Integer userId;

    /**
     * 新密码（不输入则系统随机生成密码）
     *
     * @mock lHfxoPrKOaWjSqwN
     */
    @Length(min = 8, max = 32, message = "新密码长度需要8~32位")
    private String password;

}
