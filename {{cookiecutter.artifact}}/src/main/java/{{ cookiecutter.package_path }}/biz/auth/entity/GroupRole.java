package {{ cookiecutter.basePackage }}.biz.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户组角色关联
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("auth_group_role")
public class GroupRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户组ID
     */
    private Long groupId;

    /**
     * 角色ID
     */
    private Long roleId;

    public GroupRole(Long groupId, Long roleId) {
        this.groupId = groupId;
        this.roleId = roleId;
    }
}
