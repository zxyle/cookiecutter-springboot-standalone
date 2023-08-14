package {{ cookiecutter.basePackage }}.biz.auth.request.user;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.util.List;

@Data
public class UpdateUserRequest {

    /**
     * 角色列表
     */
    private List<@Positive @NotNull Long> roleIds;

    /**
     * 用户组列表
     */
    private List<@Positive @NotNull Long> groupIds;

    /**
     * 权限列表
     */
    private List<@Positive @NotNull Long> permissionIds;
}
