package {{ cookiecutter.basePackage }}.biz.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户-权限关联
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("auth_user_permission")
public class UserPermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 权限ID
     */
    private Long permissionId;

    public UserPermission(Long userId, Long permissionId) {
        this.userId = userId;
        this.permissionId = permissionId;
    }
}
