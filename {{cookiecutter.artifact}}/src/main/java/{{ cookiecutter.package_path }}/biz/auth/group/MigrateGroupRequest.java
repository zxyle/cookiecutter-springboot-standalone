package {{ cookiecutter.basePackage }}.biz.auth.group;

import lombok.Data;

import {{ cookiecutter.namespace }}.validation.constraints.NotNull;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;

/**
 * 迁移用户组请求
 */
@Data
public class MigrateGroupRequest {

    /**
     * 目标用户组ID
     *
     * @mock 1
     */
    @Positive(message = "目标用户组ID必须为正整数")
    @NotNull(message = "目标用户组ID不能为空")
    private Integer parentId;

    /**
     * 当前用户组ID
     *
     * @mock 2
     */
    @Positive(message = "当前用户组ID必须为正整数")
    @NotNull(message = "当前用户组ID不能为空")
    private Integer currentId;

}
