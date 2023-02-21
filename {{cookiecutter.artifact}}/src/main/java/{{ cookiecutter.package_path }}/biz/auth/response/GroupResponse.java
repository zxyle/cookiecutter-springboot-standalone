package {{ cookiecutter.basePackage }}.biz.auth.response;

import {{ cookiecutter.basePackage }}.biz.auth.entity.Group;
import {{ cookiecutter.basePackage }}.biz.auth.entity.Permission;
import {{ cookiecutter.basePackage }}.biz.auth.entity.Role;
import {{ cookiecutter.basePackage }}.biz.auth.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class GroupResponse extends Group {

    /**
     * 用户组下的用户
     */
    private List<User> users;

    /**
     * 权限列表
     */
    private List<Permission> permissions;

    /**
     * 角色列表
     */
    private List<Role> roles;

}
