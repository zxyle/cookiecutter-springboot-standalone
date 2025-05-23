package {{ cookiecutter.basePackage }}.biz.auth.token;

import {{ cookiecutter.basePackage }}.common.constant.AuthConst;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * JWT工具类
 */
public final class JwtUtil {

    private JwtUtil() {
    }

    /**
     * 登录有效期（单位: 毫秒）默认一天
     */
    private static final long JWT_TTL = AuthConst.DEFAULT_EXPIRE_DAYS * 24 * 60 * 60 * 1000L;

    /**
     * 设置秘钥明文
     */
    private static final String JWT_KEY = "{{ random_ascii_string(32) }}";

    private static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成jwt 令牌
     *
     * @param subject token中要存放的数据（json格式）
     */
    public static String createJwt(String subject) {
        JwtBuilder builder = getJwtBuilder(subject, null, getUuid());// 设置过期时间
        return builder.compact();
    }

    /**
     * 生成jwt token
     *
     * @param subject   token中要存放的数据（json格式）
     * @param ttlMillis token超时时间
     */
    public static String createJwt(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUuid());// 设置过期时间
        return builder.compact();
    }

    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        // Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis; // 过期时间
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                // HEADER
                // .setHeaderParam("key", "value")
                // PAYLOAD
                // .claim("key", "value")
                // .setId(uuid)              // 唯一的ID
                .setSubject(subject)      // 主题  可以是JSON数据
                // .setIssuer("sg")          // 签发者
                // .setIssuedAt(now)         // 签发时间
                .signWith(signatureAlgorithm, secretKey) // 使用HS256对称加密算法签名, 第二个参数为秘钥
                .setExpiration(expDate);
    }

    /**
     * 创建token
     *
     * @param id        唯一的ID
     * @param subject   主题
     * @param ttlMillis 过期时间
     */
    public static String createJwt(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);  // 设置过期时间
        return builder.compact();
    }

    /**
     * 生成加密后的秘钥 secretKey
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 解析token
     */
    public static Claims parseJwt(String token) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}

