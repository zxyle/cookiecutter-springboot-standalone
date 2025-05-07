package {{ cookiecutter.basePackage }}.biz.auth.login;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;

/**
 * 本机号码一键登录请求
 */
@Data
public class OneKeyLoginRequest {

    /**
     * token
     */
    @NotBlank(message = "token不能为空")
    private String token;

    /**
     * 服务提供商
     */
    @NotBlank(message = "服务提供商不能为空")
    private String provider;

    public String getToken() {
        return StringUtils.trim(token);
    }

    public String getProvider() {
        return StringUtils.trim(provider);
    }
}
