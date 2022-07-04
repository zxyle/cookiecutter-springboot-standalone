package {{ cookiecutter.basePackage }}.biz.auth.controller;

import dev.zhengxiang.tool.crypto.PlainKeyPair;
import dev.zhengxiang.tool.crypto.RSAEncrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * 密钥管理
 */
@RestController
@Slf4j
public class EncryptController {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    /**
     * 获取RSA公钥
     */
    @RequestMapping("/getPublicKey")
    public String getPublicKey() {
        String sessionId = "";
        String key = "privateKey:".concat(sessionId);

        try {
            // 生成一对公钥和私钥
            PlainKeyPair plainKeyPair = RSAEncrypt.genKeyPair();
            String privateKey = plainKeyPair.getPrivateKey();
            stringRedisTemplate.opsForValue().set(key, privateKey, 60, TimeUnit.SECONDS);
            // 将公钥传递给前端
            return plainKeyPair.getPublicKey();
        } catch (NoSuchAlgorithmException e) {
            log.error("error: ", e);
        }

        // 前端使用公钥将关键信息加密，传递给后端

        // 后端使用私钥进行解密
        return "";

    }
}
