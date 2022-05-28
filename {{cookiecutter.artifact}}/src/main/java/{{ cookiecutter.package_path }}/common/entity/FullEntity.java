package {{ cookiecutter.basePackage }}.common.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"createTime", "updateTime", "version", "deleted"})
public class FullEntity extends BaseEntity {

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

