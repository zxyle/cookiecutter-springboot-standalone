package {{ cookiecutter.basePackage }};

import dev.zhengxiang.tool.crypto.PlainKeyPair;
import dev.zhengxiang.tool.crypto.RSAEncrypt;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.wildfly.common.Assert;

/**
 * 加密工具类单元测试
 */
class TestEncrypt {

    @Test
    void testRsa() throws Exception {
        PlainKeyPair plainKeyPair = RSAEncrypt.genKeyPair();
        String privateKey = plainKeyPair.getPrivateKey();
        String publicKey = plainKeyPair.getPublicKey();

        String content = "123456";
        String encrypt = RSAEncrypt.encrypt(content, publicKey);

        String decrypt = RSAEncrypt.decrypt(encrypt, privateKey);
        Assert.assertTrue(content.equals(decrypt));
    }

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

        Assert.assertTrue(pbkdf2PasswordEncoder.matches("123456", pbk1));
        Assert.assertTrue(pbkdf2PasswordEncoder.matches("123456", pbk2));
        Assert.assertTrue(bCryptPasswordEncoder.matches("123456", bcr1));
        Assert.assertTrue(bCryptPasswordEncoder.matches("123456", bcr2));
    }
    {% endif -%}
}
