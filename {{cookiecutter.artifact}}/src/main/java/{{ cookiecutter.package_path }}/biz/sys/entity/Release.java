package {{ cookiecutter.basePackage }}.biz.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 发布版本
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_release")
public class Release extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 版本号
     */
    private String version;

    /**
     * 版本描述
     */
    private String description;

}
