package {{ cookiecutter.basePackage }}.biz.auth.request.password;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 使用旧密码方式修改密码
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyByOldRequest {

    /**
     * 旧密码
     *
     * @mock z8kE1dYBHPzlyqq1
     */
    @NotBlank
    private String oldPassword;

    /**
     * 新密码（长度需要8~32位）
     *
     * @mock lHfxoPrKOaWjSqwN
     */
    @NotBlank
    @Length(min = 8, max = 32)
    private String newPassword;

}
