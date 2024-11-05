package {{ cookiecutter.basePackage }}.common.request.auth;

import {{ cookiecutter.namespace }}.validation.constraints.NotNull;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;

import {{ cookiecutter.basePackage }}.common.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.util.Set;


@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateAuthRequest extends BaseRequest {

    /**
     * 权限、角色、用户组名称
     *
     * @mock 更新项目
     */
    @Length(max = 16, message = "名称长度不能超过16个字符")
    private String name;

    /**
     * 描述信息
     */
    @Length(max = 32, message = "描述信息不能超过32个字符")
    private String description;

    /**
     * 权限id集合
     */
    private Set<@Positive @NotNull Integer> permissionIds;

    /**
     * 角色id集合
     */
    private Set<@Positive @NotNull Integer> roleIds;
}
