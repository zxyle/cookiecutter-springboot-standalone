package {{ cookiecutter.basePackage }}.biz.auth.request.password;

import {{ cookiecutter.basePackage }}.common.request.BaseRequest;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class ForgetByPhoneRequest extends BaseRequest {

    /**
     * 手机号
     *
     * @mock 13111111111
     */
    @NotBlank
    private String mobile;

    /**
     * 短信验证码
     *
     * @mock 123456
     */
    @NotBlank
    private String code;

    /**
     * 新密码（长度需要8~32位）
     *
     * @mock lHfxoPrKOaWjSqwN
     */
    @NotBlank
    @Length(min = 8, max = 32)
    private String newPassword;
}
