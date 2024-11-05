package {{ cookiecutter.basePackage }}.biz.site.checkin;

import {{ cookiecutter.basePackage }}.common.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import {{ cookiecutter.namespace }}.validation.constraints.NotNull;
import {{ cookiecutter.namespace }}.validation.constraints.Past;
import java.time.LocalDate;

/**
 * 补签请求
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PatchCheckinRequest extends BaseRequest {

    /**
     * 补签日期
     */
    @Past(message = "补签日期不能大于当前日期")
    @NotNull(message = "补签日期不能为空")
    private LocalDate checkinDate;
}
