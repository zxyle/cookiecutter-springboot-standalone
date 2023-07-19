package {{ cookiecutter.basePackage }}.biz.sys.request.dict;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


/**
 * 批量查询字典请求
 */
@Data
public class MultiDictTypeRequest {

    /**
     * 使用逗号分隔的多种字典类型
     *
     * @mock "gender,education"
     */
    @Pattern(regexp = "^[a-zA-Z,_]+$", message = "字典类型只能包含字母、逗号和下划线")
    @NotBlank(message = "字典类型不能为空")
    private String types;
}
