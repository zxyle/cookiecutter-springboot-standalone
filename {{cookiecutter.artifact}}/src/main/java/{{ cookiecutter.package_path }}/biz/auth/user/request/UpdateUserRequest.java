package {{ cookiecutter.basePackage }}.biz.auth.user.request;

import lombok.Data;
import {{ cookiecutter.namespace }}.validation.constraints.NotNull;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;

import java.util.Set;

/**
 * 更新用户请求
 */
@Data
public class UpdateUserRequest {

    /**
     * 角色id集合
     */
    private Set<@Positive @NotNull Integer> roleIds;

    /**
     * 用户组id集合
     */
    private Set<@Positive @NotNull Integer> groupIds;

    /**
     * 权限id集合
     */
    private Set<@Positive @NotNull Integer> permissionIds;
}
