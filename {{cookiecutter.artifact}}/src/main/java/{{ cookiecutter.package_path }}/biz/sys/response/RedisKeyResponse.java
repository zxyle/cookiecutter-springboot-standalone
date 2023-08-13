package {{ cookiecutter.basePackage }}.biz.sys.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.connection.DataType;

/**
 * Redis key响应
 */
@Data
@AllArgsConstructor
public class RedisKeyResponse {

    /**
     * key名称
     */
    private String key;

    /**
     * key值
     */
    private String value;

    /**
     * 过期时间
     */
    private Long expire;

    /**
     * key类型
     */
    private DataType type;

}
