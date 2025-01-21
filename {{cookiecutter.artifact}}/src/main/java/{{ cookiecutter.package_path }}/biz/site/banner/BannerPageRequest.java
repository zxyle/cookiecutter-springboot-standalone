package {{ cookiecutter.basePackage }}.biz.site.banner;

import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 * 轮播图 分页请求类
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BannerPageRequest extends PaginationRequest {

    /**
     * 状态 (1:启用, 0:禁用)
     *
     * @mock 0
     */
    @Range(min = 0, max = 1, message = "状态必须在0-1之间")
    private Integer status;

    /**
     * 展示位置
     *
     * @mock
     */
    @Length(max = 100, message = "展示位置长度不能超过100个字符")
    private String position;

}