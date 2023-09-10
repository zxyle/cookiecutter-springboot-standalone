package {{ cookiecutter.basePackage }}.biz.sys.acl;

import {{ cookiecutter.basePackage }}.common.request.BaseRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * IP访问控制 请求类
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AclRequest extends BaseRequest {

    /**
     * IP
     */
    @Length(max = 20, message = "IP长度不能超过20个字符")
    private String ip;

    /**
     * 是否允许访问
     */
    private Boolean allowed;

    /**
     * 截止时间
     */
    @NotNull(message = "截止时间不能为空")
    private LocalDateTime endTime;

    /**
     * 描述信息
     */
    @Length(max = 16, message = "描述信息长度不能超过16个字符")
    @NotBlank(message = "描述信息不能为空")
    private String description;

}