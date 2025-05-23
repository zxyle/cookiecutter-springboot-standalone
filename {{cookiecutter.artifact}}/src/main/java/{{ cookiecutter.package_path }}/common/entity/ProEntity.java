package {{ cookiecutter.basePackage }}.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProEntity extends LiteEntity {

    /**
     * 创建者用户ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer createBy;

    /**
     * 更新者用户ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateBy;
}
