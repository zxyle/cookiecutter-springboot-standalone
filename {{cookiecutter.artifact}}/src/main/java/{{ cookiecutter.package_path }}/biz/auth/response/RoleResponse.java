package {{ cookiecutter.basePackage }}.biz.auth.response;

import {{ cookiecutter.basePackage }}.biz.auth.entity.Permission;
import {{ cookiecutter.basePackage }}.biz.auth.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 角色信息响应
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class RoleResponse extends Role {

    /**
     * 权限信息
     */
    private List<Permission> permissions;
}
