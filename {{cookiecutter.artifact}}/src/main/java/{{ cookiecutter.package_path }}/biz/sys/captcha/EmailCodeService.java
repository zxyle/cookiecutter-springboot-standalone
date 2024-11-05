package {{ cookiecutter.basePackage }}.biz.sys.captcha;

import {{ cookiecutter.basePackage }}.biz.sys.verification.Verification;
import {{ cookiecutter.basePackage }}.biz.sys.setting.SettingService;
import {{ cookiecutter.basePackage }}.biz.sys.verification.VerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import {{ cookiecutter.namespace }}.mail.internet.MimeMessage;

/**
 * 邮件验证码服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailCodeService {

    final JavaMailSender javaMailSender;
    final SettingService setting;
    final VerificationService verificationService;

    /**
     * 发送邮件验证码
     *
     * @param recipient 接收者邮箱
     * @param code      验证码
     */
    @Async
    public void sendVerificationCode(String recipient, String code) {
        String appName = setting.get("app.name").getStr();
        String sender = setting.get("spring.mail.username").getStr();
        Integer aliveTime = setting.get("captcha.alive-time").getIntValue();
        String template = setting.get("email.verification-template").getStr();
        String content = String.format(template, code, aliveTime);
        log.info(content);
        String subject = String.format("【%s】邮箱验证码", appName);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(message, true);
            // 邮件发送人
            mimeMessageHelper.setFrom(appName + '<' + sender + '>');
            // 邮件接收人
            mimeMessageHelper.setTo(recipient);
            // 邮件主题
            mimeMessageHelper.setSubject(subject);
            // 邮件内容,HTML格式
            mimeMessageHelper.setText(content, true);
            javaMailSender.send(message);

            // 记录邮箱验证码发送记录
            Verification verification = new Verification();
            verification.setContent(content);
            verification.setKind("email");
            verification.setReceiver(recipient);
            verificationService.save(verification);
        } catch (Exception e) {
            log.error("发送邮件验证码失败", e);
        }

    }
}
