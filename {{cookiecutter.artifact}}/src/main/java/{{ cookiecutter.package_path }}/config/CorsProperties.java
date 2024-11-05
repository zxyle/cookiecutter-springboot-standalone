package {{ cookiecutter.basePackage }}.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 跨域配置属性类，用于读取配置文件中的跨域配置信息
 * <a href="https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/Access-Control-Allow-Origin">...</a>
 */
@Data
@Component
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {

    /**
     * 允许的请求源, 对应 CORS 请求中的 Access-Control-Allow-Origin 头
     */
    private List<String> allowedOrigins;

    /**
     * 允许的请求方法，对应 CORS 请求中的 Access-Control-Allow-Methods 头
     */
    private List<String> allowedMethods;

    /**
     * 允许的请求头，对应 CORS 请求中的 Access-Control-Allow-Headers 头
     */
    private List<String> allowedHeaders;

    /**
     * 允许暴露的请求头，对应 CORS 请求中的 Access-Control-Expose-Headers 头
     */
    private List<String> exposedHeaders;

    /**
     * 本次预检请求的有效期，单位为秒，在此期间不用发出另一条预检请求，对应 CORS 请求中的 Access-Control-Max-Age 头
     */
    private Long maxAge;

    /**
     * 是否允许携带cookie，对应 CORS 请求中的 Access-Control-Allow-Credentials 头
     */
    private Boolean allowCredentials;
}
