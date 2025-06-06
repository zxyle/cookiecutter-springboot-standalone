package {{ cookiecutter.basePackage }}.biz.auth.user.request;

import {{ cookiecutter.basePackage }}.common.constant.RegexConst;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;
import {{ cookiecutter.namespace }}.validation.constraints.NotNull;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;
import {{ cookiecutter.namespace }}.validation.constraints.Pattern;
import java.util.Set;

/**
 * 管理员添加用户
 */
@Data
public class AdminAddUserRequest {

    /**
     * 注册账号（只支持输入用户名）
     *
     * @mock jack
     */
    @NotBlank(message = "注册账号不能为空")
    @Length(min = 5, max = 18, message = "注册账号长度为5-18个字符")
    @Pattern(regexp = RegexConst.REGEX_USERNAME, message = "由5-18位字母、数字、下划线组成，必须以字母开头")
    private String account;

    /**
     * 用户密码
     *
     * @mock lHfxoPrKOaWjSqwN
     */
    @NotBlank(message = "用户密码不能为空")
    @Length(min = 8, max = 32, message = "用户密码长度为8-32个字符")
    private String password;

    /**
     * 角色id集合
     */
    private Set<@Positive @NotNull Integer> roleIds;

    /**
     * 用户组id集合
     */
    private Set<@Positive @NotNull Integer> groupIds;

    /**
     * 是否需要修改密码
     *
     * @mock false
     */
    private boolean mustChangePwd;

}
