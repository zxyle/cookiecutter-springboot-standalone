package {{ cookiecutter.basePackage }}.biz.auth.role.permission;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.LiteEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import {{ cookiecutter.namespace }}.validation.constraints.NotNull;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;

/**
 * 角色权限关联
 */
@Data
@TableName("auth_role_permission")
@EqualsAndHashCode(callSuper = false)
public class RolePermission extends LiteEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 权限ID
     */
    @Positive(message = "权限ID必须为正数")
    @NotNull(message = "权限ID不能为空")
    private Integer permissionId;

    public RolePermission(Integer roleId, Integer permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }
}
