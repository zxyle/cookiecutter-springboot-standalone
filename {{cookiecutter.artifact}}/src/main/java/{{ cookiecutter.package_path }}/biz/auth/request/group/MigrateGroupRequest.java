package {{ cookiecutter.basePackage }}.biz.auth.request.group;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用户组迁移请求体
 */
@Data
public class MigrateGroupRequest {

    /**
     * 新用户组ID
     */
    @NotNull
    private Long parentId;

    /**
     * 当前用户组ID
     */
    @NotNull
    private Long currentId;

}
