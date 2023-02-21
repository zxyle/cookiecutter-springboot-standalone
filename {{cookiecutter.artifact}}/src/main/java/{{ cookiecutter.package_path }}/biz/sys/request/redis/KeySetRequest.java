package {{ cookiecutter.basePackage }}.biz.sys.request.redis;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class KeySetRequest {

    /**
     * key名称
     */
    @NotBlank(message = "key不能为空")
    private String name;

    /**
     * 值
     */
    private String value;

    /**
     * 过期时间，单位秒
     */
    private Long expire;

    /**
     * 类型
     */
    private String type;

}
