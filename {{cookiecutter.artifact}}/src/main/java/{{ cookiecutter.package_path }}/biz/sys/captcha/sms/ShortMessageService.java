package {{ cookiecutter.basePackage }}.biz.sys.captcha.sms;

/**
 * 短信验证码发送服务
 */
public interface ShortMessageService {

    /**
     * 验证码短信
     *
     * @param recipient 手机号
     * @param code      验证码
     */
    void send(String recipient, String code);
}
