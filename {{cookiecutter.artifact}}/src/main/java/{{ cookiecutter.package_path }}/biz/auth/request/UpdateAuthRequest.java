package {{ cookiecutter.basePackage }}.biz.auth.request;

import {{ cookiecutter.basePackage }}.common.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateAuthRequest extends BaseRequest {

    /**
     * 权限、角色、用户组名称
     *
     * @mock 更新项目
     */
    private String name;

    /**
     * 描述信息
     */
    private String description;
}
