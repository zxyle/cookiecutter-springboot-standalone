package {{ cookiecutter.basePackage }}.biz.auth.user.role;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.LiteEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import {{ cookiecutter.namespace }}.validation.constraints.NotNull;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;

/**
 * 用户角色关联
 */
@Data
@TableName("auth_user_role")
@EqualsAndHashCode(callSuper = false)
public class UserRole extends LiteEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 角色ID
     */
    @Positive(message = "角色ID必须为正数")
    @NotNull(message = "角色ID不能为空")
    private Integer roleId;

    public UserRole(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
