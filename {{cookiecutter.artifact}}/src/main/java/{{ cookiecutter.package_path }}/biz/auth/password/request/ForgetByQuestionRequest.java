package {{ cookiecutter.basePackage }}.biz.auth.password.request;


import {{ cookiecutter.basePackage }}.biz.auth.mfa.question.AddAnswerRequest;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import {{ cookiecutter.namespace }}.validation.Valid;
import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;
import java.util.List;

/**
 * 通过密保忘记/找回密码请求
 */
@Data
public class ForgetByQuestionRequest {

    /**
     * 注册账号
     *
     * @mock 13512345678
     */
    @NotBlank(message = "注册账号不能为空")
    @Length(min = 5, message = "注册账号长度为5个字符以上")
    private String account;

    /**
     * 问题答案
     */
    private List<AddAnswerRequest.@Valid AnswerRequest> answers;

    /**
     * 新密码（长度需要8~32位）
     *
     * @mock lHfxoPrKOaWjSqwN
     */
    @NotBlank(message = "新密码不能为空")
    @Length(min = 8, max = 32, message = "新密码长度需要8~32位")
    private String newPassword;
}
