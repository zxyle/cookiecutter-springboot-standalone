package {{ cookiecutter.basePackage }}.biz.sys.captcha.patchca;

import {{ cookiecutter.basePackage }}.biz.sys.setting.SettingService;
import org.patchca.color.ColorFactory;
import org.patchca.color.GradientColorFactory;
import org.patchca.color.RandomColorFactory;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.FilterFactory;
import org.patchca.filter.predefined.*;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.word.RandomWordFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.security.SecureRandom;

/**
 * Patchca验证码配置
 */
@Component
public class PatchcaConfig {

    SettingService setting;

    public PatchcaConfig(SettingService setting) {
        this.setting = setting;
    }

    @Bean
    public ConfigurableCaptchaService configurableCaptchaService() {
        ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
        cs.setWordFactory(randomWordFactory());
        SecureRandom random = new SecureRandom();
        cs.setColorFactory(getColorFactories()[random.nextInt(getColorFactories().length)]);
        cs.setFilterFactory(getFilterFactories()[random.nextInt(getFilterFactories().length)]);
        cs.setHeight(setting.get("captcha.height").getIntValue());
        cs.setWidth(setting.get("captcha.width").getIntValue());
        return cs;
    }

    public RandomWordFactory randomWordFactory() {
        RandomWordFactory wf = new RandomWordFactory();
        wf.setCharacters(setting.get("captcha.chars").getStr());
        wf.setMaxLength(setting.get("captcha.digits").getIntValue());
        wf.setMinLength(setting.get("captcha.digits").getIntValue());
        return wf;
    }

    public ColorFactory[] getColorFactories() {
        return new ColorFactory[]{
                // 自定义颜色
                getColorFactory(),
                // 梯度变化颜色
                new GradientColorFactory(),
                // 随机颜色
                new RandomColorFactory(),
                // 单一颜色
                new SingleColorFactory()
        };

    }

    public FilterFactory[] getFilterFactories() {
        return new FilterFactory[]{
                // 弧线波纹
                new CurvesRippleFilterFactory(getColorFactory()),
                // 大理石波纹
                new MarbleRippleFilterFactory(),
                // 大理石波纹
                new DoubleRippleFilterFactory(),
                // 摇摆波纹
                new WobbleRippleFilterFactory(),
                // 扩散的波纹
                new DiffuseRippleFilterFactory()
        };

    }

    /**
     * 自定义颜色工厂
     *
     * @return 颜色工厂类
     */
    public ColorFactory getColorFactory() {
        return x -> {
            SecureRandom random = new SecureRandom();
            int[] c = {25, 60, 170};
            int i = random.nextInt(c.length);
            for (int fi = 0; fi < c.length; fi++) {

                if (fi == i) {
                    c[fi] = random.nextInt(71);
                } else {
                    c[fi] = random.nextInt(256);
                }
            }
            return new Color(c[0], c[1], c[2]);
        };
    }
}
