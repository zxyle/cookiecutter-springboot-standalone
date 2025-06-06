package {{ cookiecutter.basePackage }}.biz.auth.mfa.totp;

import com.bastiaanjansen.otp.HMACAlgorithm;
import com.bastiaanjansen.otp.SecretGenerator;
import com.bastiaanjansen.otp.TOTPGenerator;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

/**
 * Authenticator
 */
public final class Authenticator {

    /**
     * 生成二维码
     *
     * @param account 账号名
     * @param issuer  服务提供者(一般为为网址)
     */
    public static TotpResponse generate(String account, String issuer) {
        byte[] secret = SecretGenerator.generate();
        TOTPGenerator generator = build(secret);
        URI url;
        try {
            url = generator.getURI(issuer, account);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return new TotpResponse(url.toString(), account, issuer, new String(secret));
    }

    /**
     * 校验验证码是否正确
     *
     * @param secret 密钥
     * @param code   动态验证码
     */
    public static boolean valid(String secret, String code) {
        TOTPGenerator generator = build(secret.getBytes());
        return generator.verify(code);
    }

    private static TOTPGenerator build(byte[] secret) {
        return new TOTPGenerator.Builder(secret)
                .withHOTPGenerator(builder -> {
                    builder.withPasswordLength(6);
                    builder.withAlgorithm(HMACAlgorithm.SHA256); // SHA256 and SHA512 are also supported
                })
                .withPeriod(Duration.ofSeconds(30))
                .build();
    }

}
