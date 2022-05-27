package {{ cookiecutter.basePackage }}.biz.auth.request.group;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteGroupRequest {

    /**
     * 用户组ID
     */
    @NotNull
    private Long groupId;

}
