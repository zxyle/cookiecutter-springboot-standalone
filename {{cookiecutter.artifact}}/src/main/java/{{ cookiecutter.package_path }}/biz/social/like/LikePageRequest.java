package {{ cookiecutter.basePackage }}.biz.social.like;

import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import {{ cookiecutter.namespace }}.validation.constraints.NotNull;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;

/**
 * 点赞分页请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LikePageRequest extends PaginationRequest {

    /**
     * 资源类型
     *
     * @mock 2
     */
    @Positive(message = "资源类型必须为正数")
    @NotNull(message = "资源类型不能为null")
    private Integer resType;

    /**
     * 评论ID
     *
     * @mock 1
     */
    private Integer commentId;

}
