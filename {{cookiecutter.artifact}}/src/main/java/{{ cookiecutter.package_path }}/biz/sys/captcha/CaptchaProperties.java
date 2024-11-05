package {{ cookiecutter.basePackage }}.biz.sys.captcha;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "captcha")
public class CaptchaProperties {

    /**
     * 登录是否开启图形验证码
     */
    private boolean on;

    /**
     * 验证码存活时间（单位：分钟）
     */
    private Integer aliveTime;

    /**
     * 验证码位数/长度
     */
    private Integer digits;

    /**
     * 主账号类型 mobile-手机号、email-邮箱、username-用户名
     */
    private String principal;

    /**
     * 两次验证码请求间隔时间（单位：秒）
     */
    private Integer between;

    /**
     * 重试登录次数
     */
    private Integer retryTimes;

    /**
     * 验证码字符集(一般去掉1 l L 0 o O 易混淆字符)
     */
    private String chars;

    /**
     * 高度像素
     */
    private Integer height;

    /**
     * 宽度像素
     */
    private Integer width;

    /**
     * 字体大小
     */
    private Integer fontSize;

    /**
     * 字体
     */
    private String fontFamily;

    /**
     * 验证码种类 kaptcha、patchca
     */
    private String kind;

    /**
     * 图片格式（jpg、png）
     */
    private String format;

    /**
     * 验证码存储前缀
     */
    private String keyPrefix;

}
