package {{ cookiecutter.basePackage }}.biz.auth.login;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;

/**
 * 第三方登录请求
 */
@Data
public class ThirdPartyLoginRequest {

    /**
     * 临时授权码
     *
     * @mock 123456
     */
    @NotBlank(message = "临时授权码不能为空")
    private String code;

    /**
     * 第三方登录提供商
     *
     * @mock wechat、qq、weibo、dingtalk
     */
    @NotBlank(message = "第三方登录提供商不能为空")
    private String provider;

    public String getCode() {
        return StringUtils.trim(code);
    }

    public String getProvider() {
        return StringUtils.trim(provider);
    }
}
