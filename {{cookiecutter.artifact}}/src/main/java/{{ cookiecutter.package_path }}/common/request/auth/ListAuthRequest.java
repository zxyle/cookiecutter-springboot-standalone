package {{ cookiecutter.basePackage }}.common.request.auth;

import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

@Data
@EqualsAndHashCode(callSuper = false)
public class ListAuthRequest extends PaginationRequest {

    /**
     * 账号可用 1-启用 0-禁用
     */
    @Range(min = 0, max = 1, message = "账号可用 1-启用 0-禁用")
    private Integer enabled;

    /**
     * 是否返回拥有的角色、用户、用户组、权限信息
     */
    private boolean full;

}
