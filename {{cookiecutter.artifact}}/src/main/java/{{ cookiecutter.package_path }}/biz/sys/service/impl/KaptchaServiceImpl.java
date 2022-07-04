package {{ cookiecutter.basePackage }}.biz.sys.service.impl;

import {{ cookiecutter.basePackage }}.biz.sys.service.CaptchaPair;
import {{ cookiecutter.basePackage }}.biz.sys.service.CaptchaService;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class KaptchaServiceImpl implements CaptchaService {

    private final Producer captchaProducer;

    public KaptchaServiceImpl(Producer captchaProducer) {
        this.captchaProducer = captchaProducer;
    }

    @Override
    public CaptchaPair generate() {
        String capText = captchaProducer.createText();
        BufferedImage bi = captchaProducer.createImage(capText);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, "jpg", byteArrayOutputStream);
        } catch (IOException e) {
            log.error("kaptcha error: ", e);
        }
        return new CaptchaPair(byteArrayOutputStream, capText);
    }
}
