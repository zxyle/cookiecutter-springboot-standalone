package {{ cookiecutter.basePackage }}.biz.auth.password.request;

import {{ cookiecutter.basePackage }}.common.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;

/**
 * 使用旧密码方式修改密码
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ChangeByOldRequest extends BaseRequest {

    /**
     * 旧密码
     *
     * @mock z8kE1dYBHPzlyqq1
     */
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    /**
     * 新密码（长度需要8~32位）
     *
     * @mock lHfxoPrKOaWjSqwN
     */
    @NotBlank(message = "新密码不能为空")
    @Length(min = 8, max = 32, message = "新密码长度需要8~32位")
    private String newPassword;

}
