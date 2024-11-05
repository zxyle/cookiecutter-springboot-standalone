package {{ cookiecutter.basePackage }}.biz.sys.dict;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;
import {{ cookiecutter.namespace }}.validation.constraints.Pattern;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;

/**
 * 新增字典请求
 */
@Data
public class AddDictRequest {

    /**
     * 字典名称
     */
    @Length(max = 100, message = "字典名称长度不能超过100个字符")
    @NotBlank(message = "字典名称不能为空")
    private String name;

    /**
     * 字典排序
     */
    @Positive(message = "字典排序必须为正整数")
    private Integer dictSort;

    /**
     * 字典标签
     */
    @Length(max = 100, message = "字典标签长度不能超过100个字符")
    @NotBlank(message = "字典标签不能为空")
    private String label;

    /**
     * 字典键值
     */
    @Length(max = 100, message = "字典键值长度不能超过100个字符")
    @NotBlank(message = "字典键值不能为空")
    private String value;

    /**
     * 字典类型
     */
    @Pattern(regexp = "^[a-zA-Z_]+$", message = "字典类型只能包含字母和下划线")
    @Length(max = 25, message = "字典名称长度不能超过100")
    @NotBlank(message = "字典类型不能为空")
    private String dictType;
}
