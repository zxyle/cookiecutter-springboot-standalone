package {{ cookiecutter.basePackage }}.biz.auth.group.role;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.LiteEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import {{ cookiecutter.namespace }}.validation.constraints.NotNull;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;

/**
 * 用户组角色关联
 */
@Data
@TableName("auth_group_role")
@EqualsAndHashCode(callSuper = false)
public class GroupRole extends LiteEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户组ID
     */
    private Integer groupId;

    /**
     * 角色ID
     */
    @Positive(message = "角色ID必须为正数")
    @NotNull(message = "角色ID不能为空")
    private Integer roleId;

    public GroupRole(Integer groupId, Integer roleId) {
        this.groupId = groupId;
        this.roleId = roleId;
    }
}
