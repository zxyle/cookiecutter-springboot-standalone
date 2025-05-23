package {{ cookiecutter.basePackage }}.biz.auth.user.group;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.LiteEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户-用户组关联
 */
@Data
@TableName("auth_user_group")
@EqualsAndHashCode(callSuper = false)
public class UserGroup extends LiteEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户组ID
     */
    private Integer groupId;

    /**
     * 是否是该组管理员
     */
    private Boolean admin;

    public UserGroup(Integer userId, Integer groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }
}
