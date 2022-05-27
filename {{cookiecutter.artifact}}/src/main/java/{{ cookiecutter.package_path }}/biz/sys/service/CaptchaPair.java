package {{ cookiecutter.basePackage }}.biz.sys.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CaptchaPair {

    /**
     * base64格式图片
     */
    private String b64Image;

    /**
     * 验证码结果
     */
    private String code;

    /**
     * 验证码字节数组
     */
    private byte[] bytes;
}
