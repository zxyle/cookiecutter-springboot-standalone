package {{ cookiecutter.basePackage }}.biz.sys.secret;

import cn.hutool.core.util.IdUtil;
import {{ cookiecutter.basePackage }}.common.response.R;
import dev.zhengxiang.tool.crypto.PlainKeyPair;
import dev.zhengxiang.tool.crypto.RSAEncrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * 密钥管理
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class SecretKeyController {

    final StringRedisTemplate stringRedisTemplate;

    /**
     * 获取RSA公钥
     */
    @RequestMapping("/getPublicKey")
    public R<SecretKeyResponse> getPublicKey() {
        String keyId = IdUtil.simpleUUID();
        String key = "privateKey:".concat(keyId);

        try {
            // 生成一对公钥和私钥
            PlainKeyPair plainKeyPair = RSAEncrypt.genKeyPair();
            String privateKey = plainKeyPair.getPrivateKey();
            stringRedisTemplate.opsForValue().set(key, privateKey, 60, TimeUnit.SECONDS);
            // 将公钥传递给前端
            SecretKeyResponse data = new SecretKeyResponse(keyId, plainKeyPair.getPublicKey());
            return R.ok(data);
        } catch (NoSuchAlgorithmException e) {
            log.error("error: ", e);
        }

        // 前端使用公钥将关键信息加密，传递给后端

        // 后端使用私钥进行解密
        return R.fail("获取公钥失败");
    }
}
