package {{ cookiecutter.basePackage }}.biz.sys.captcha.kaptcha;

import {{ cookiecutter.basePackage }}.biz.sys.captcha.CaptchaPair;
import {{ cookiecutter.basePackage }}.biz.sys.captcha.CaptchaService;
import {{ cookiecutter.basePackage }}.biz.sys.setting.SettingService;
import com.google.code.kaptcha.Producer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Kaptcha验证码实现
 */
@Slf4j
@RequiredArgsConstructor
public class KaptchaServiceImpl implements CaptchaService {

    final SettingService setting;
    final Producer captchaProducer;

    @Override
    public CaptchaPair generate() {
        String captchaId = getCaptchaId();
        String code = captchaProducer.createText();
        BufferedImage bi = captchaProducer.createImage(code);
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bi, setting.get("captcha.format").getStr(), byteArrayOutputStream);
            return new CaptchaPair(byteArrayOutputStream, code, captchaId);
        } catch (IOException e) {
            log.error("kaptcha生成失败: ", e);
        }
        return null;
    }
}
