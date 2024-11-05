package {{ cookiecutter.basePackage }}.biz.auth.role;

import {{ cookiecutter.basePackage }}.common.validation.Add;
import {{ cookiecutter.basePackage }}.common.validation.Update;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;
import {{ cookiecutter.namespace }}.validation.constraints.NotNull;
import {{ cookiecutter.namespace }}.validation.constraints.Null;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;
import java.util.Set;

/**
 * 创建角色请求
 */
@Data
public class RoleRequest {

    /**
     * 角色名称
     *
     * @mock 管理员
     */
    @Length(max = 16, message = "角色名称长度不能超过16个字符", groups = {Add.class, Update.class})
    @NotBlank(message = "角色名称不能为空", groups = {Add.class})
    private String name;

    /**
     * 角色代码
     *
     * @mock admin
     */
    @Length(max = 32, message = "角色代码长度不能超过32个字符", groups = {Add.class})
    @Null(message = "角色代码不能修改", groups = {Update.class})
    private String code;

    /**
     * 描述信息
     *
     * @mock 管理员
     */
    @Length(max = 64, message = "描述信息长度不能超过64个字符", groups = {Add.class, Update.class})
    private String description;

    /**
     * 权限id集合
     */
    private Set<@Positive @NotNull Integer> permissionIds;
}
