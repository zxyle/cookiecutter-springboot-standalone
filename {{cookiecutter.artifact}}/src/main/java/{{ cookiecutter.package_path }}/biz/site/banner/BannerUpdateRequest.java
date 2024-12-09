package {{ cookiecutter.basePackage }}.biz.site.banner;

import {{ cookiecutter.basePackage }}.common.request.BaseRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import {{ cookiecutter.namespace }}.validation.constraints.NotNull;

/**
 * Banner请求类
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BannerUpdateRequest extends BaseRequest {

    /**
     * 名称
     *
     * @mock 
     */
    @Length(max = 100, message = "名称长度不能超过100个字符")
    private String name;

    /**
     * 标题
     *
     * @mock 
     */
    @Length(max = 100, message = "标题长度不能超过100个字符")
    private String title;

    /**
     * 描述
     *
     * @mock 
     */
    @Length(max = 65535, message = "描述长度不能超过65535个字符")
    private String description;

    /**
     * 图片路径或URL
     *
     * @mock 
     */
    @Length(max = 512, message = "图片路径或URL长度不能超过512个字符")
    private String imageUrl;

    /**
     * 链接
     *
     * @mock 
     */
    @Length(max = 512, message = "链接长度不能超过512个字符")
    private String linkUrl;

    /**
     * 展示顺序，越小越靠前
     *
     * @mock 0
     */
    private Integer sortOrder;

    /**
     * 状态 (1:启用, 0:禁用)
     *
     * @mock 0
     */
    @NotNull(message = "状态 (1:启用, 0:禁用)不能为null")
    private Integer status;

    /**
     * 展示位置
     *
     * @mock 
     */
    @Length(max = 100, message = "展示位置长度不能超过100个字符")
    private String position;

}