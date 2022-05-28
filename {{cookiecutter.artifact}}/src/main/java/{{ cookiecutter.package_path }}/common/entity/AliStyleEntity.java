package {{ cookiecutter.basePackage }}.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import {{ cookiecutter.basePackage }}.common.constant.DateFormatConsts;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AliStyleEntity extends LiteEntity {

    /**
     * 数据行创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = DateFormatConsts.YYYY_MM_DD_HH_MM_SS, timezone = DateFormatConsts.TIMEZONE)
    private LocalDateTime gmt_create;

    /**
     * 数据行最后更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = DateFormatConsts.YYYY_MM_DD_HH_MM_SS, timezone = DateFormatConsts.TIMEZONE)
    private LocalDateTime gmt_modified;

}
