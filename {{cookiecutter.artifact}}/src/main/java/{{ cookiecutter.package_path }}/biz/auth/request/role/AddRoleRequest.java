package {{ cookiecutter.basePackage }}.biz.auth.request.role;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddRoleRequest {

    /**
     * 角色名称
     */
    @NotBlank
    private String name;

    /**
     * 角色代码
     */
    @NotBlank
    private String code;

    /**
     * 描述信息
     */
    private String description;
}
