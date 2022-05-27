package {{ cookiecutter.basePackage }}.biz.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户-用户组关联
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("auth_user_group")
@AllArgsConstructor
public class UserGroup extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户组ID
     */
    private Long groupId;

}
