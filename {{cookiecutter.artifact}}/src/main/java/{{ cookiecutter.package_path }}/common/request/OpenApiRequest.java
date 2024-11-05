package {{ cookiecutter.basePackage }}.common.request;

import lombok.Data;

/**
 * 开放接口请求
 */
@Data
public class OpenApiRequest {

    /**
     * 请求毫秒时间戳
     *
     * @mock 1644541518652
     */
    private String timestamp;

    /**
     * 签名
     */
    private String sign;

    /**
     * 应用ID
     */
    private String appId;
}
