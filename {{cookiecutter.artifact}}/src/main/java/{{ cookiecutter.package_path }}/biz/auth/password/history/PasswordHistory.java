package {{ cookiecutter.basePackage }}.biz.auth.password.history;

import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 密码历史表
 */
@Data
@TableName("auth_password_history")
@EqualsAndHashCode(callSuper = false)
public class PasswordHistory extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 密码修改类型：初始密码、重置密码、找回密码、修改密码
     */
    private String kind;

    /**
     * 修改人
     */
    private String editedBy;

    /**
     * 修改后密码
     */
    private String afterPwd;

    /**
     * 修改前密码
     */
    private String beforePwd;

}
