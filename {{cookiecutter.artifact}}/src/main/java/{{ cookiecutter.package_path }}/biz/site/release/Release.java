package {{ cookiecutter.basePackage }}.biz.site.release;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;

/**
 * 发布版本
 */
@Data
@TableName("site_release")
@EqualsAndHashCode(callSuper = false)
public class Release extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 版本号
     */
    @Length(max = 16, message = "版本号长度不能超过16个字符")
    @NotBlank(message = "版本号不能为空")
    private String version;

    /**
     * 版本描述
     */
    @Length(max = 1024, message = "版本描述长度不能超过1024个字符")
    @NotBlank(message = "版本描述不能为空")
    private String description;

}
