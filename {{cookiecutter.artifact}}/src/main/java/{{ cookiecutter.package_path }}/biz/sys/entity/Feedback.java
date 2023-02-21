package {{ cookiecutter.basePackage }}.biz.sys.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 意见反馈
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_feedback")
public class Feedback extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 联系邮箱
     */
    @ExcelProperty("联系邮箱")
    private String email;

    /**
     * 联系电话
     */
    @ExcelProperty("联系电话")
    private String tel;

    /**
     * 详细描述
     */
    @ExcelProperty("详细描述")
    private String description;

}
