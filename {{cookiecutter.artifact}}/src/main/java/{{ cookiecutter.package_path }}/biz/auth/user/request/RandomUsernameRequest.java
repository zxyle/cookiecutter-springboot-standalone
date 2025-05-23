package {{ cookiecutter.basePackage }}.biz.auth.user.request;

import {{ cookiecutter.basePackage }}.common.constant.RegexConst;
import lombok.Data;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;
import {{ cookiecutter.namespace }}.validation.constraints.Pattern;

/**
 * 随机用户名请求
 */
@Data
public class RandomUsernameRequest {

    /**
     * 用户名前缀
     *
     * @mock jack
     */
    @NotBlank(message = "用户名前缀不能为空")
    @Pattern(regexp = RegexConst.REGEX_USERNAME, message = "用户名前缀格式不正确")
    private String prefix;
}
