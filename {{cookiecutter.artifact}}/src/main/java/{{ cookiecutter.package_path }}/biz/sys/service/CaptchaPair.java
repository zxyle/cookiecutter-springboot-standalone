package {{ cookiecutter.basePackage }}.biz.sys.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Data
@AllArgsConstructor
public class CaptchaPair {

    /**
     * 验证码图片字节数组
     */
    private ByteArrayOutputStream byteArrayOutputStream;

    /**
     * 验证码结果
     */
    private String code;

    /**
     * 获取验证码图片Base64编码
     */
    public String getB64Image() {
        return Base64.getEncoder().encodeToString(getBytes());
    }

    /**
     * 获取验证码图片字节数组
     */
    public byte[] getBytes() {
        return byteArrayOutputStream.toByteArray();
    }
}
