package {{ cookiecutter.basePackage }}.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import {{ cookiecutter.namespace }}.validation.constraints.NotEmpty;
import {{ cookiecutter.namespace }}.validation.constraints.NotNull;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;
import {{ cookiecutter.namespace }}.validation.constraints.Size;
import java.util.Set;

/**
 * 批量请求
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BatchRequest extends BaseRequest {

    /**
     * 逗号分隔ID列表。最多20个ID
     *
     * @mock 1,2,3
     */
    @NotEmpty(message = "ids不能为空")
    @Size(min = 1, max = 20)
    private Set<@NotNull @Positive Integer> ids;

}

