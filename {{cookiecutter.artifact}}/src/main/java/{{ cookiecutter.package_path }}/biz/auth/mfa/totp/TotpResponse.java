package {{ cookiecutter.basePackage }}.biz.auth.mfa.totp;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TOTP密钥响应
 */
@Data
@AllArgsConstructor
public class TotpResponse {

    /**
     * 二维码链接
     */
    private String url;

    /**
     * 账号名
     */
    private String account;

    /**
     * 服务名
     */
    private String issuer;

    /**
     * 密钥
     */
    private String secret;
}
