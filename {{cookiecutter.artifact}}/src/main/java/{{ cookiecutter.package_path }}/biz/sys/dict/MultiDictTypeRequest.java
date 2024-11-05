package {{ cookiecutter.basePackage }}.biz.sys.dict;

import lombok.Data;

import {{ cookiecutter.namespace }}.validation.constraints.NotEmpty;
import {{ cookiecutter.namespace }}.validation.constraints.Pattern;
import {{ cookiecutter.namespace }}.validation.constraints.Size;
import java.util.Set;


/**
 * 批量查询字典请求
 */
@Data
public class MultiDictTypeRequest {

    /**
     * 多种字典类型使用逗号分隔
     *
     * @mock gender,education
     */
    @NotEmpty(message = "字典类型集合不能为空")
    @Size(min = 1, message = "字典类型集合至少包含一个元素")
    private Set<@Pattern(regexp = "^[a-zA-Z_]+$", message = "字典类型只能包含字母和下划线") String> types;
}
