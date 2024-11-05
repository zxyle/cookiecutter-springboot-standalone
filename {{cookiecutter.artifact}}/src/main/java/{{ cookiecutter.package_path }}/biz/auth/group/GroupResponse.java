package {{ cookiecutter.basePackage }}.biz.auth.group;

import {{ cookiecutter.basePackage }}.biz.auth.permission.Permission;
import {{ cookiecutter.basePackage }}.biz.auth.user.UserResponse;
import {{ cookiecutter.basePackage }}.biz.auth.role.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户组信息响应
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GroupResponse extends Group {

    /**
     * 用户组下的用户
     */
    private List<UserResponse> users;

    /**
     * 权限列表
     */
    private List<Permission> permissions;

    /**
     * 角色列表
     */
    private List<Role> roles;

}
