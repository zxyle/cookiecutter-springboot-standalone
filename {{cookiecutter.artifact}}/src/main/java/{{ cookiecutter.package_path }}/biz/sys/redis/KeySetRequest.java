package {{ cookiecutter.basePackage }}.biz.sys.redis;

import lombok.Data;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;

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
    @NotBlank(message = "value不能为空")
    private String value;

    /**
     * 过期时间，单位秒
     */
    @Positive(message = "过期时间必须大于0")
    private Long expire;

    /**
     * 类型
     */
    @NotBlank(message = "redis数据类型不能为空")
    private String type;

}
