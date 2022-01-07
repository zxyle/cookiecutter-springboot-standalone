package {{ cookiecutter.basePackage}}.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    // 密钥
    private static final String SECRET_KEY = "{{ random_ascii_string(32) }}";

    /**
     * 生成token
     *
     * @param payload 传入payload
     * @return 返回token
     */
    public static String getToken(Map<String, String> payload) {
        JWTCreator.Builder builder = JWT.create();
        payload.forEach(builder::withClaim);

        // 指定7天过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);
        builder.withExpiresAt(instance.getTime());

        // 签名并返回
        return builder.sign(Algorithm.HMAC256(SECRET_KEY));
    }

    /**
     * 验证token是否正确
     *
     * @param token token
     */
    public static void verify(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
        jwtVerifier.verify(token);
    }

    /**
     * 获取token中的payload
     *
     * @param token token
     * @return DecodedJWT
     */
    public static DecodedJWT getTokenInfo(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
    }

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        verify(getToken(map));
    }


}
