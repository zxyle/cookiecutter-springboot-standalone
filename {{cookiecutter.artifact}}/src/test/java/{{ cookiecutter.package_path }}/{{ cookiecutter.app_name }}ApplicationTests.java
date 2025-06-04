package {{ cookiecutter.basePackage }};

import {{ cookiecutter.basePackage }}.biz.sys.captcha.EmailCodeService;
import {{ cookiecutter.basePackage }}.common.lock.RedisDistributedLock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class {{ cookiecutter.app_name }}ApplicationTests {

    @Autowired
    EmailCodeService emailService;

    @Autowired
    RedisDistributedLock redisLock;

    {% if cookiecutter.bootVersion.split('.')[0] == '2' -%}
    // 其他密码HASH方法
    @Test
    void salt() {
        String secret = "111";
        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder(secret);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        String pbk1 = pbkdf2PasswordEncoder.encode("123456");
        String pbk2 = pbkdf2PasswordEncoder.encode("123456");

        String bcr1 = bCryptPasswordEncoder.encode("123456");
        String bcr2 = bCryptPasswordEncoder.encode("123456");

        System.out.println("pbk1: " + pbk1);
        System.out.println("pbk2: " + pbk2);
        System.out.println("pbk1 password:" + pbkdf2PasswordEncoder.matches("123456", pbk1));
        System.out.println("pbk2 password:" + pbkdf2PasswordEncoder.matches("123456", pbk2));

        System.out.println("---------------------");

        System.out.println("bcr1: " + bcr1);
        System.out.println("bcr2: " + bcr2);
        System.out.println("bcr1 password:" + bCryptPasswordEncoder.matches("123456", bcr1));
        System.out.println("bcr2 password:" + bCryptPasswordEncoder.matches("123456", bcr2));
    }
    {% endif -%}

    @Test
    void send() {
        emailService.sendVerificationCode("123456", "admin@example.com");
    }

    // 分布式锁测试
    @Test
    void lock() {
        // 获取锁
        String lockKey = "order_123";
        String lockValue = redisLock.lock(lockKey, 30, TimeUnit.SECONDS);

        try {
            if (lockValue != null) {
                // 执行业务逻辑
                System.out.println("执行业务逻辑...");
            }
        } finally {
            // 安全释放锁
            if (lockValue != null) {
                redisLock.unlock(lockKey, lockValue);
            }
        }
    }

}
