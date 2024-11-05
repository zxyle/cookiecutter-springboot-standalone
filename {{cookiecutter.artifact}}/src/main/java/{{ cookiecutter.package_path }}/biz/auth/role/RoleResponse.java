package {{ cookiecutter.basePackage }}.biz.auth.role;

import {{ cookiecutter.basePackage }}.biz.auth.permission.Permission;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 角色信息响应
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleResponse extends Role {

    /**
     * 权限信息
     */
    private List<Permission> permissions;
}
