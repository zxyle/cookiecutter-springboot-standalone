package {{ cookiecutter.basePackage }}.biz.auth.user.permission;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.LiteEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import {{ cookiecutter.namespace }}.validation.constraints.NotNull;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;

/**
 * 用户-权限关联
 */
@Data
@TableName("auth_user_permission")
@EqualsAndHashCode(callSuper = false)
public class UserPermission extends LiteEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 权限ID
     */
    @Positive(message = "权限ID必须为正数")
    @NotNull(message = "权限ID不能为空")
    private Integer permissionId;

    public UserPermission(Integer userId, Integer permissionId) {
        this.userId = userId;
        this.permissionId = permissionId;
    }
}
