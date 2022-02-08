package {{ cookiecutter.basePackage }}.common.entity;

import {{ cookiecutter.basePackage }}.common.utils.HashidsCombinedSerializer;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"createTime", "updateTime", "version", "deleted"})
public class BaseEntity implements Serializable {

    /**
     * primary key
     */
    @TableId(value = "id", type = IdType.AUTO)
    @JsonSerialize(using = HashidsCombinedSerializer.Serialize.class)
    @JsonDeserialize(using = HashidsCombinedSerializer.Deserialize.class)
    private Long id;

    /**
     * create time of the row
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * last update time of the row
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
