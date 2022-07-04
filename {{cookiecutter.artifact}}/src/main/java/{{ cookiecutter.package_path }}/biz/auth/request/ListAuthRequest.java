package {{ cookiecutter.basePackage }}.biz.auth.request;

import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ListAuthRequest extends PaginationRequest {

    /**
     * 用户名、用户组名、角色名(支持模糊查询)
     */
    private String name;

    /**
     * 状态(1-正常 0-停用)
     */
    private Integer state;

    // 创建时间

    // code
}
