package {{ cookiecutter.basePackage }}.biz.auth.role;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色
 */
@Data
@TableName("auth_role")
@EqualsAndHashCode(callSuper = false)
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色代码
     */
    private String code;

    /**
     * 描述信息
     */
    private String description;

}
