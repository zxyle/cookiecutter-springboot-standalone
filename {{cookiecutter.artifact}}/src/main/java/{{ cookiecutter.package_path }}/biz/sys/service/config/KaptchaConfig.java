package {{ cookiecutter.basePackage }}.biz.sys.service.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class KaptchaConfig {

    @Bean
    public Producer captchaProducer() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(getPropertiesConfig());
        return defaultKaptcha;
    }

    public Config getPropertiesConfig() {
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "yes");
        properties.setProperty("kaptcha.border.color", "105,179,90");
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        properties.setProperty("kaptcha.image.width", String.valueOf(CaptchaConst.WIDTH));
        properties.setProperty("kaptcha.image.height", String.valueOf(CaptchaConst.HEIGHT));
        properties.setProperty("kaptcha.textproducer.font.size", String.valueOf(CaptchaConst.FONT_SIZE));
        properties.setProperty("kaptcha.session.key", "code");
        properties.setProperty("kaptcha.textproducer.char.length", String.valueOf(CaptchaConst.LENGTH));
        properties.setProperty("kaptcha.textproducer.char.string", CaptchaConst.CHARACTERS);
        properties.setProperty("kaptcha.textproducer.font.names", CaptchaConst.FONT_NAMES);
        return new Config(properties);
    }
}
