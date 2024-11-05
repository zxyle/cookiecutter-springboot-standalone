package {{ cookiecutter.basePackage }}.common.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import {{ cookiecutter.basePackage }}.common.constant.DateFormatConst;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 只有id、create_time字段的实体类，用于给只新增不修改的表继承
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RecordEntity extends LiteEntity {

    /**
     * 数据行创建时间
     */
    @ExcelIgnore
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = DateFormatConst.YYYY_MM_DD_HH_MM_SS, timezone = DateFormatConst.TIMEZONE)
    private LocalDateTime createTime;
}
