package {{ cookiecutter.basePackage }}.biz.sys.request.redis;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class KeySetRequest {

    /**
     * key名称
     */
    @NotBlank(message = "key不能为空")
    private String key;

    private String value;

    private Long expire;

    private String type;

}
