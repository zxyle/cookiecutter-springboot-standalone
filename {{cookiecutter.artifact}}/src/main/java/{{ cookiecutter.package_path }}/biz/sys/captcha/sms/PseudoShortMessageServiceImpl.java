package {{ cookiecutter.basePackage }}.biz.sys.captcha.sms;

import {{ cookiecutter.basePackage }}.biz.sys.setting.SettingService;
import {{ cookiecutter.basePackage }}.biz.sys.verification.Verification;
import {{ cookiecutter.basePackage }}.biz.sys.verification.VerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 短信发送服务伪实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PseudoShortMessageServiceImpl implements ShortMessageService {

    final VerificationService verificationService;
    final SettingService setting;

    /**
     * 发送验证码短信
     *
     * @param recipient 手机号
     * @param code      验证码
     */
    @Async
    @Override
    public void send(String recipient, String code) {
        String appName = setting.get("app.name").getStr();
        String template = setting.get("sms.verification-template").getStr();
        String content = String.format(template, appName, code);
        log.info("发送到 <{}> 短信验证码：{}", recipient, code);

        // 记录短信验证码发送记录
        Verification verification = new Verification();
        verification.setKind("mobile");
        verification.setReceiver(recipient);
        verification.setContent(content);
        // 不保存验证码记录
        // verificationService.save(verification);
    }
}
