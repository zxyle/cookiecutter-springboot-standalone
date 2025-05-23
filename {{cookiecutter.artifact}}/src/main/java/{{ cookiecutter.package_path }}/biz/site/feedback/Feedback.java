package {{ cookiecutter.basePackage }}.biz.site.feedback;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;

/**
 * 意见反馈
 */
@Data
@TableName("site_feedback")
@EqualsAndHashCode(callSuper = false)
public class Feedback extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 联系方式
     */
    @NotBlank(message = "联系方式不能为空")
    @Length(max = 32, message = "联系方式长度不能超过32个字符")
    @ExcelProperty("联系方式")
    private String contacts;

    /**
     * 详细描述
     */
    @Length(max = 2048, message = "详细描述长度不能超过2048个字符")
    @NotBlank(message = "详细描述不能为空")
    @ExcelProperty("详细描述")
    private String description;

}
