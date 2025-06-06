package {{ cookiecutter.basePackage }}.biz.auth.group;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户组
 */
@Data
@TableName("auth_group")
@EqualsAndHashCode(callSuper = false)
public class Group extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户组名称
     */
    private String name;

    /**
     * 上级用户组ID
     */
    private Integer parentId;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 描述信息
     */
    private String description;

}
