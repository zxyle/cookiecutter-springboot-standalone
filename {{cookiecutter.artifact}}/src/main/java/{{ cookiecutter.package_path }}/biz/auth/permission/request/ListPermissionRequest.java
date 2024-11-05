package {{ cookiecutter.basePackage }}.biz.auth.permission.request;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class ListPermissionRequest {

    /**
     * 权限类型（1：页面/路由，2：接口/功能，3：按钮/组件）
     */
    @Range(min = 1, max = 3, message = "权限类型为1~3之间")
    private Integer kind;
}
