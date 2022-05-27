package {{ cookiecutter.basePackage }}.biz.sys.service.config;

import org.patchca.color.ColorFactory;
import org.patchca.color.GradientColorFactory;
import org.patchca.color.RandomColorFactory;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.FilterFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.filter.predefined.DiffuseRippleFilterFactory;
import org.patchca.filter.predefined.DoubleRippleFilterFactory;
import org.patchca.filter.predefined.MarbleRippleFilterFactory;
import org.patchca.filter.predefined.WobbleRippleFilterFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.word.RandomWordFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Random;

@Component
public class PatchcaConfig {

    @Bean
    public ConfigurableCaptchaService configurableCaptchaService() {
        ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
        cs.setWordFactory(randomWordFactory());
        cs.setColorFactory(getColorFactories()[new Random().nextInt(getColorFactories().length)]);
        cs.setFilterFactory(getFilterFactories()[new Random().nextInt(getFilterFactories().length)]);
        cs.setHeight(CaptchaConst.HEIGHT);
        cs.setWidth(CaptchaConst.WIDTH);
        return cs;
    }

    public RandomWordFactory randomWordFactory() {
        RandomWordFactory wf = new RandomWordFactory();
        wf.setCharacters(CaptchaConst.CHARACTERS);
        wf.setMaxLength(CaptchaConst.LENGTH);
        wf.setMinLength(CaptchaConst.LENGTH);
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

    // 自定义颜色工厂
    public ColorFactory getColorFactory() {
        return x -> {
            Random random = new Random();
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
