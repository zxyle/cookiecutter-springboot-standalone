package {{ cookiecutter.basePackage }}.biz.sys.captcha.patchca;

import {{ cookiecutter.basePackage }}.biz.sys.captcha.CaptchaPair;
import {{ cookiecutter.basePackage }}.biz.sys.captcha.CaptchaService;
import {{ cookiecutter.basePackage }}.biz.sys.setting.SettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.patchca.color.GradientColorFactory;
import org.patchca.color.RandomColorFactory;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.*;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Patchca验证码实现
 */
@Slf4j
@RequiredArgsConstructor
public class PatchcaServiceImpl implements CaptchaService {

    final SettingService setting;

    private ConfigurableCaptchaService randomCs() {
        ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
        RandomWordFactory wordFactory = new RandomWordFactory();
        wordFactory.setMaxLength(setting.get("captcha.digits").getIntValue());
        wordFactory.setMinLength(setting.get("captcha.digits").getIntValue());
        wordFactory.setCharacters(setting.get("captcha.chars").getStr());
        cs.setWordFactory(wordFactory);
        cs.setHeight(setting.get("captcha.height").getIntValue());
        cs.setWidth(setting.get("captcha.width").getIntValue());

        switch ((int) (System.currentTimeMillis() % 3)) {
            default:
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
            default:
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
        String captchaId = getCaptchaId();
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            String format = setting.get("captcha.format").getStr();
            String code = EncoderHelper.getChallangeAndWriteImage(randomCs(), format, byteArrayOutputStream);
            return new CaptchaPair(byteArrayOutputStream, code, captchaId);
        } catch (IOException e) {
            log.error("patchca生成失败: ", e);
        }
        return null;
    }
}
