package {{ cookiecutter.basePackage }}.common.entity;

import {{ cookiecutter.basePackage }}.common.util.HashidsCombinedSerializer;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
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
public class FullEntity implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @JsonSerialize(using = HashidsCombinedSerializer.Serialize.class)
    @JsonDeserialize(using = HashidsCombinedSerializer.Deserialize.class)
    private Long id;

    /**
     * 数据行创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 数据行最后更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 版本号(乐观锁注解)
     */
    @Version
    private Integer version;

    /**
     * 逻辑删除 (0-正常 1-已删除)
     */
    @TableLogic
    private Integer deleted;

}
