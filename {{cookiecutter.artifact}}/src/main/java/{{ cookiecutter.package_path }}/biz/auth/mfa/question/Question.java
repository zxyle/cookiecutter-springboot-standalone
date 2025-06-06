package {{ cookiecutter.basePackage }}.biz.auth.mfa.question;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;

/**
 * 安全问题
 */
@Data
@TableName("auth_question")
@EqualsAndHashCode(callSuper = false)
public class Question extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 安全问题
     *
     * @mock 你的出生地是哪里？
     */
    @Length(max = 64, message = "安全问题长度不能超过64个字符")
    @NotBlank(message = "安全问题不能为空")
    private String ask;

}
