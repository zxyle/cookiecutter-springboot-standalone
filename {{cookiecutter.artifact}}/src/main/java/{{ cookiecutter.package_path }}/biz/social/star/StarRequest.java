package {{ cookiecutter.basePackage }}.biz.social.star;

import lombok.Data;

import {{ cookiecutter.namespace }}.validation.constraints.NotNull;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;

/**
 * 收藏请求
 */
@Data
public class StarRequest {

    /**
     * 资源类型
     */
    @Positive(message = "资源类型必须为正数")
    @NotNull(message = "资源类型不能为null")
    private Integer resType;

    /**
     * 资源ID
     */
    @Positive(message = "资源ID必须为正数")
    @NotNull(message = "资源ID不能为null")
    private Integer resId;
}
