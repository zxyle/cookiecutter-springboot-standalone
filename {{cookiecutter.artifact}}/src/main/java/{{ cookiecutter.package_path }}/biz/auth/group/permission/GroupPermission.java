package {{ cookiecutter.basePackage }}.biz.auth.group.permission;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.LiteEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import {{ cookiecutter.namespace }}.validation.constraints.NotNull;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;

/**
 * 用户组权限
 */
@Data
@TableName("auth_group_permission")
@EqualsAndHashCode(callSuper = false)
public class GroupPermission extends LiteEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户组ID
     */
    private Integer groupId;

    /**
     * 权限ID
     */
    @Positive(message = "权限ID必须为正数")
    @NotNull(message = "权限ID不能为空")
    private Integer permissionId;

    public GroupPermission(Integer groupId, Integer permissionId) {
        this.groupId = groupId;
        this.permissionId = permissionId;
    }

}
