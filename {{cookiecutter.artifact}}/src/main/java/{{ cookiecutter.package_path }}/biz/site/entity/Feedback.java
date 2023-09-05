package {{ cookiecutter.basePackage }}.biz.site.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 意见反馈
 */
@Data
@TableName("site_feedback")
@EqualsAndHashCode(callSuper = true)
public class Feedback extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 联系方式
     */
    @ExcelProperty("联系方式")
    private String contacts;

    /**
     * 详细描述
     */
    @ExcelProperty("详细描述")
    private String description;

}
