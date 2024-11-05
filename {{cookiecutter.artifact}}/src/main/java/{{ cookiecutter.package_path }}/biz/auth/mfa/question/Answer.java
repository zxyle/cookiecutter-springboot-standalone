package {{ cookiecutter.basePackage }}.biz.auth.mfa.question;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * 安全问题答案
 */
@Data
@TableName("auth_answer")
@EqualsAndHashCode(callSuper = false)
public class Answer extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 安全问题ID
     */
    private Integer questionId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 安全问题答案md5
     */
    @Length(max = 32, message = "安全问题答案md5长度不能超过32个字符")
    private String secret;

}
