package {{ cookiecutter.basePackage }}.biz.site.rank;

import {{ cookiecutter.basePackage }}.common.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;
import {{ cookiecutter.namespace }}.validation.constraints.NotNull;

/**
 * 获取排行榜请求
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TopRequest extends BaseRequest {

    /**
     * 业务名称
     *
     * @mock article
     */
    @NotBlank(message = "业务名称不能为空")
    private String biz;

    /**
     * 获取数量
     *
     * @mock 10
     */
    @Range(min = 1, max = 50, message = "获取数量必须在1-50之间")
    @NotNull(message = "获取数量不能为空")
    private Integer size = 10;

    /**
     * 排序，默认降序
     *
     * @mock true
     */
    private Boolean desc = true;
}
