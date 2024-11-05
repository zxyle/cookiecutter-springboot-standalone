package {{ cookiecutter.basePackage }}.biz.auth.mfa.totp;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * TOTP（基于时间的一次性密码）
 */
@Data
@AllArgsConstructor
@TableName("auth_totp")
@EqualsAndHashCode(callSuper = false)
public class Totp extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 密钥
     */
    @Length(max = 32, message = "密钥长度不能超过32个字符")
    private String secret;

}
