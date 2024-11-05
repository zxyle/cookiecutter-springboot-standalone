package {{ cookiecutter.basePackage }}.common.request;

import {{ cookiecutter.namespace }}.validation.constraints.NotNull;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 主键ID请求
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IdRequest extends BaseRequest {

    /**
     * 主键ID
     */
    @Positive
    @NotNull(message = "ID不能为空")
    private Integer id;

}
