package {{ cookiecutter.basePackage }}.biz.auth.app;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 跳转响应
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedirectResponse {

    /**
     * 带临时授权码的跳转地址
     */
    private String redirectUrl;

    /**
     * 临时授权码
     */
    private String authCode;

    // /**
    //  * 临时授权码过期时间
    //  */
    // private String expireAt;

}
