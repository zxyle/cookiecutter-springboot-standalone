package {{ cookiecutter.basePackage }}.biz.sys.captcha.kaptcha;

import {{ cookiecutter.basePackage }}.biz.sys.setting.SettingService;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Kaptcha验证码配置
 */
@Component
public class KaptchaConfig {

    SettingService setting;

    public KaptchaConfig(SettingService setting) {
        this.setting = setting;
    }

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
        properties.setProperty("kaptcha.image.width", String.valueOf(setting.get("captcha.width").getIntValue()));
        properties.setProperty("kaptcha.image.height", String.valueOf(setting.get("captcha.height").getIntValue()));
        properties.setProperty("kaptcha.textproducer.font.size", String.valueOf(setting.get("captcha.font-size").getIntValue()));
        properties.setProperty("kaptcha.session.key", "code");
        properties.setProperty("kaptcha.textproducer.char.length", String.valueOf(setting.get("captcha.digits").getIntValue()));
        properties.setProperty("kaptcha.textproducer.char.string", setting.get("captcha.chars").getStr());
        properties.setProperty("kaptcha.textproducer.font.names", setting.get("captcha.font-family").getStr());
        return new Config(properties);
    }
}
