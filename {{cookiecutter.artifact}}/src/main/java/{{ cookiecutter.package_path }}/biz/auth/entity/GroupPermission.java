package {{ cookiecutter.basePackage }}.biz.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户组权限
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("auth_group_permission")
public class GroupPermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户组ID
     */
    private Long groupId;

    /**
     * 权限ID
     */
    private Long permissionId;

}
