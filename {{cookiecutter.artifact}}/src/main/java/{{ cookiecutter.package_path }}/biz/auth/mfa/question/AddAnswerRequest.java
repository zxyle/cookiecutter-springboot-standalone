package {{ cookiecutter.basePackage }}.biz.auth.mfa.question;

import {{ cookiecutter.basePackage }}.common.validation.Add;
import {{ cookiecutter.basePackage }}.common.validation.Update;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import {{ cookiecutter.namespace }}.validation.Valid;
import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;
import {{ cookiecutter.namespace }}.validation.constraints.NotEmpty;
import {{ cookiecutter.namespace }}.validation.constraints.NotNull;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;
import java.util.List;

/**
 * 新增安全问题答案请求
 */
@Data
public class AddAnswerRequest {

    /**
     * 安全问题答案列表
     */
    @NotEmpty(message = "安全问题答案列表不能为空", groups = {Add.class})
    private List<@Valid AnswerRequest> answers;

    /**
     * 短信或邮件验证码
     *
     * @mock 123123
     */
    @NotBlank(message = "验证码不能为空", groups = {Update.class})
    private String code;

    @Getter
    @Setter
    public static class AnswerRequest {

        /**
         * 问题id
         */
        @Positive(message = "问题id必须为正整数")
        @NotNull(message = "问题id不能为空")
        private Integer questionId;

        /**
         * 答案
         */
        @NotBlank(message = "答案不能为空")
        private String answer;
    }

}
