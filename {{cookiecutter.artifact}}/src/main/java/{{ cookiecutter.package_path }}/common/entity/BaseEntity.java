package {{ cookiecutter.basePackage }}.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import {{ cookiecutter.basePackage }}.common.constant.DateFormatConst;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 基础实体类，包含主键ID、创建时间、更新时间
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(value = {"updateTime", "version", "deleted"})
public class BaseEntity extends LiteEntity {

    /**
     * 数据行创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = DateFormatConst.YYYY_MM_DD_HH_MM_SS, timezone = DateFormatConst.TIMEZONE)
    private LocalDateTime createTime;

    /**
     * 数据行最后更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = DateFormatConst.YYYY_MM_DD_HH_MM_SS, timezone = DateFormatConst.TIMEZONE)
    private LocalDateTime updateTime;

}
