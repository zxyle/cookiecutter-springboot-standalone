package {{ cookiecutter.basePackage }}.biz.site.checkin;

import {{ cookiecutter.basePackage }}.common.request.BaseRequest;
import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;
import {{ cookiecutter.namespace }}.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

/**
 * 签到 请求类
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CheckinRequest extends BaseRequest {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Integer userId;

    /**
     * 签到日期
     */
    @NotNull(message = "签到日期不能为空")
    private LocalDate checkinDate;

}