package {{ cookiecutter.basePackage }}.biz.social.like;

import lombok.Data;

import {{ cookiecutter.namespace }}.validation.constraints.NotNull;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;

/**
 * 点赞和取消点赞请求
 */
@Data
public class LikeRequest {

    /**
     * 资源类型
     *
     * @mock 2
     */
    @Positive(message = "资源类型必须为正数")
    @NotNull(message = "资源类型不能为null")
    private Integer resType;

    /**
     * 资源ID
     *
     * @mock 1
     */
    @Positive(message = "资源ID必须为正数")
    @NotNull(message = "资源ID不能为null")
    private Integer resId;

}
