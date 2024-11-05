package {{ cookiecutter.basePackage }}.biz.auth.app;

import {{ cookiecutter.basePackage }}.common.validation.Add;
import {{ cookiecutter.basePackage }}.common.validation.Update;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;
import {{ cookiecutter.namespace }}.validation.constraints.Pattern;

/**
 * 新增应用请求
 */
@Data
public class AppRequest {

    /**
     * 应用中文名称
     *
     * @mock 风险预警系统
     */
    @Length(max = 32, message = "应用名称长度不能超过32个字符", groups = {Add.class, Update.class})
    @NotBlank(message = "应用名称不能为空", groups = Add.class)
    private String name;

    /**
     * 应用描述
     *
     * @mock 风险预警系统
     */
    @Length(max = 255, message = "应用描述长度不能超过255个字符", groups = {Add.class, Update.class})
    private String description;

    /**
     * 应用标识
     *
     * @mock risk
     */
    @Pattern(regexp = "^[a-z]+$", message = "应用标识只能由小写字母组成", groups = Add.class)
    @Length(max = 16, message = "应用标识长度不能超过16个字符", groups = Add.class)
    @NotBlank(message = "应用标识不能为空", groups = Add.class)
    private String appKey;

    /**
     * 回调地址
     */
    @URL(message = "回调地址格式不正确", groups = {Add.class, Update.class})
    @Length(max = 255, message = "回调地址长度不能超过255个字符", groups = {Add.class, Update.class})
    @NotBlank(message = "回调地址不能为空", groups = Add.class)
    private String redirectUrl;

    /**
     * 应用logo
     */
    @Length(max = 255, message = "应用logo长度不能超过255个字符", groups = {Add.class, Update.class})
    private String logo;

}
