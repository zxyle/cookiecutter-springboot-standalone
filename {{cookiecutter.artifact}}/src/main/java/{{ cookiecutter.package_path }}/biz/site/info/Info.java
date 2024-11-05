package {{ cookiecutter.basePackage }}.biz.site.info;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统信息
 */
@Data
@TableName("site_info")
@EqualsAndHashCode(callSuper = false)
public class Info extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 键
     */
    private String paramKey;

    /**
     * 值
     */
    private String paramValue;

    /**
     * 描述信息
     */
    private String description;

}
