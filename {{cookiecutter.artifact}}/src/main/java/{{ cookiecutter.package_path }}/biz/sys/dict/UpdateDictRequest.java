package {{ cookiecutter.basePackage }}.biz.sys.dict;

import {{ cookiecutter.basePackage }}.common.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import {{ cookiecutter.namespace }}.validation.constraints.Positive;

/**
 * 更新字典请求
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateDictRequest extends BaseRequest {

    /**
     * 字典标签
     */
    @Length(max = 100, message = "字典标签长度不能超过100个字符")
    private String label;

    /**
     * 字典排序
     */
    @Positive(message = "字典排序必须为正整数")
    private Integer dictSort;

    /**
     * 字典键值
     */
    @Length(max = 100, message = "字典键值长度不能超过100个字符")
    private String value;
}
