package {{ cookiecutter.basePackage }}.biz.auth.app;

import lombok.Data;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;

@Data
public class CheckRequest {

    /**
     * 临时授权码
     */
    @NotBlank(message = "临时授权码不能为空")
    private String authCode;
}
