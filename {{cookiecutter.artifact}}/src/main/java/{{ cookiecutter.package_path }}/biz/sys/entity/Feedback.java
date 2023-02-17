package {{ cookiecutter.basePackage }}.biz.sys.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
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
     * 创建时间
     */
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ExcelProperty("更新时间")
    private LocalDateTime updateTime;

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
