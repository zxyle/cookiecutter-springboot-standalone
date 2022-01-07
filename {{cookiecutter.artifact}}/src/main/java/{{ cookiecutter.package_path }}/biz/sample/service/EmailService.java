package {{ cookiecutter.basePackage }}.biz.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailService {
    // 第1步: 注入邮件发送者
    @Autowired
    JavaMailSenderImpl javaMailSender;

    /**
     * 发送普通文本邮件
     */
    public void testSend01() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("这是邮件标题");
        message.setText("这是邮件内容");
        message.setTo("168726413@qq.com");
        message.setFrom("210797402@qq.com");
        javaMailSender.send(message);
    }

    /**
     * 带附件的邮件发送
     */
    public void testSend02() throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setSubject("这是邮件标题");
        helper.setText("<b style='color:red'>这是邮件内容</b>", true);
        helper.setTo("168726413@qq.com");
        helper.setFrom("210797402@qq.com");

        helper.addAttachment("1.jpg", new File("E:\\照片\\我的第一张身份证照片\\1508714734455.jpg"));
        helper.addAttachment("2.jpg", new File("E:\\照片\\我的第一张身份证照片\\1508714741173.jpg"));
        javaMailSender.send(message);

    }
}