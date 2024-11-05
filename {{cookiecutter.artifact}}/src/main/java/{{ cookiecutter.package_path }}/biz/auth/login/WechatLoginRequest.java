package {{ cookiecutter.basePackage }}.biz.auth.login;

import lombok.Data;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;

/**
 * 微信手机号授权登录请求
 */
@Data
public class WechatLoginRequest {

    /**
     * 登录时获取的 code，可通过前端调用wx.login获取
     */
    @NotBlank(message = "jsCode不能为空")
    private String jsCode;

    /**
     * 包括敏感数据在内的完整用户信息的加密数据
     */
    @NotBlank(message = "encryptedData不能为空")
    private String encryptedData;

    /**
     * 加密算法的初始向量
     */
    @NotBlank(message = "iv不能为空")
    private String iv;

}
