package {{ cookiecutter.basePackage }}.biz.sys.secret;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 公钥响应
 */
@Data
@AllArgsConstructor
public class SecretKeyResponse {

    /**
     * 公钥ID
     */
    private String keyId;

    /**
     * 公钥
     */
    private String publicKey;
}
