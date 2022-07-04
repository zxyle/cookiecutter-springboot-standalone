package {{ cookiecutter.basePackage }}.biz.sys.service.impl;

import {{ cookiecutter.basePackage }}.biz.sys.service.CaptchaPair;
import {{ cookiecutter.basePackage }}.biz.sys.service.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.patchca.color.GradientColorFactory;
import org.patchca.color.RandomColorFactory;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.*;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@Service
@Slf4j
public class PatchcaServiceImpl implements CaptchaService {

    public ConfigurableCaptchaService randomCs() {
        ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
        switch ((int) (System.currentTimeMillis() % 3)) {
            case 0:
                cs.setColorFactory(new SingleColorFactory(new Color(55, 246, 77)));
                break;
            case 1:
                cs.setColorFactory(new GradientColorFactory());
                break;
            case 2:
                cs.setColorFactory(new RandomColorFactory());
                break;
        }

        switch ((int) (System.currentTimeMillis() % 5)) {
            case 0:
                cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
                break;
            case 1:
                cs.setFilterFactory(new MarbleRippleFilterFactory());
                break;
            case 2:
                cs.setFilterFactory(new DoubleRippleFilterFactory());
                break;
            case 3:
                cs.setFilterFactory(new WobbleRippleFilterFactory());
                break;
            case 4:
                cs.setFilterFactory(new DiffuseRippleFilterFactory());
                break;
        }
        return cs;
    }

    @Override
    public CaptchaPair generate() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String token = null;
        try {
            token = EncoderHelper.getChallangeAndWriteImage(randomCs(), "png", byteArrayOutputStream);
        } catch (IOException e) {
            log.error("patchca error: ", e);
        }
        return new CaptchaPair(byteArrayOutputStream, token);
    }
}
