package {{ cookiecutter.basePackage }}.biz.user.request.password;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
     */
    @NotBlank
    private String oldPassword;

    /**
     * 新密码
     */
    @NotBlank
    private String newPassword;

}
