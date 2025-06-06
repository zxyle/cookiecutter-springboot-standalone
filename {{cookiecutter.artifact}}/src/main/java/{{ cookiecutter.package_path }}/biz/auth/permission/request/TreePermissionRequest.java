package {{ cookiecutter.basePackage }}.biz.auth.permission.request;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import {{ cookiecutter.namespace }}.validation.constraints.Positive;

@Data
public class TreePermissionRequest {

    /**
     * 权限类型（1：页面/路由，2：接口/功能，3：按钮/组件）
     *
     * @mock 1
     */
    @Range(min = 1, max = 3, message = "权限类型为1~3之间")
    private Integer kind;

    /**
     * 根节点权限ID
     *
     * @mock 1
     */
    @Positive(message = "根节点权限ID必须为正数")
    private Integer rootPermissionId;

    public Integer getRootPermissionId() {
        return rootPermissionId == null ? 1 : rootPermissionId;
    }
}
