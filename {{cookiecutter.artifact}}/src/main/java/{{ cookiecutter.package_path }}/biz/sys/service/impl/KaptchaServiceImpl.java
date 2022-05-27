package {{ cookiecutter.basePackage }}.biz.sys.service.impl;

import {{ cookiecutter.basePackage }}.biz.sys.service.CaptchaPair;
import {{ cookiecutter.basePackage }}.biz.sys.service.CaptchaService;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class KaptchaServiceImpl implements CaptchaService {

    @Autowired
    private Producer captchaProducer;

    @Override
    public CaptchaPair generate() {
        String capText = captchaProducer.createText();
        BufferedImage bi = captchaProducer.createImage(capText);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, "jpg", byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String b64Code = Base64.getEncoder().encodeToString(bytes);
        return new CaptchaPair(b64Code, capText, bytes);
    }
}
