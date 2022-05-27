package {{ cookiecutter.basePackage }}.biz.auth.request.group;

import lombok.Data;

@Data
public class UpdateGroupRequest {

    /**
     * 用户组名称
     *
     * @mock 前端开发组
     */
    private String name;

    /**
     * 描述信息
     */
    private String description;

}
