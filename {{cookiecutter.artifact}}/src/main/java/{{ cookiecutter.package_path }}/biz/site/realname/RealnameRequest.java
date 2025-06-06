package {{ cookiecutter.basePackage }}.biz.site.realname;

import {{ cookiecutter.basePackage }}.common.request.BaseRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;
import {{ cookiecutter.namespace }}.validation.constraints.NotNull;

/**
 * 实名认证 请求类
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class RealnameRequest extends BaseRequest {

    /**
     * 姓名
     */
    @Length(max = 32, message = "姓名长度不能超过32个字符")
    @NotBlank(message = "姓名不能为空")
    private String name;

    /**
     * 证件类型
     */
    @NotNull(message = "证件类型不能为空")
    private IdTypeEnum idType;

    /**
     * 证件号码
     */
    @Length(max = 32, message = "证件号码长度不能超过32个字符")
    @NotBlank(message = "证件号码不能为空")
    private String idNum;

}