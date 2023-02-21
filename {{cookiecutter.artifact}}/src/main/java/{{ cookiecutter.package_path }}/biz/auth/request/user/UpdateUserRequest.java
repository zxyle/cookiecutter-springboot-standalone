package {{ cookiecutter.basePackage }}.biz.auth.request.user;

import lombok.Data;

import java.util.List;

@Data
public class UpdateUserRequest {

    /**
     * 角色列表
     */
    private List<Long> roleIds;

    /**
     * 用户组列表
     */
    private List<Long> groupIds;

    /**
     * 权限列表
     */
    private List<Long> permissionIds;
}
