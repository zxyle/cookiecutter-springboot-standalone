package {{ cookiecutter.basePackage }}.biz.sys.captcha;

import java.security.SecureRandom;

/**
 * 验证码工具类
 */
public final class CaptchaUtil {

    private CaptchaUtil() {
    }

    private static final SecureRandom RANDOM = new SecureRandom();

    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGIT = "0123456789";
    private static final String SPECIAL_CHAR = "!@#$%^&*";

    public static String randNumber(int len) {
        return randCode(len, false, false, true, false);
    }

    public static String randAlphabet(int len) {
        return randCode(len, true, true, false, false);
    }

    public static String randCode(int len, boolean hasUpper, boolean hasLower, boolean hasDigit, boolean hasSpecialChar) {
        String chars = "";
        if (hasUpper) {
            chars += UPPER;
        }
        if (hasLower) {
            chars += LOWER;
        }
        if (hasDigit) {
            chars += DIGIT;
        }
        if (hasSpecialChar) {
            chars += SPECIAL_CHAR;
        }
        return randCode(len, chars);
    }

    /**
     * 生成随机码
     *
     * @param len   长度
     * @param chars 字符集
     * @return 随机码
     */
    public static String randCode(int len, String chars) {
        if (len <= 0) {
            throw new IllegalArgumentException("Length must be greater than zero.");
        }
        if (chars == null || chars.isEmpty()) {
            throw new IllegalArgumentException("Character set cannot be null or empty.");
        }
        StringBuilder builder = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int no = RANDOM.nextInt(chars.length());
            builder.append(chars.charAt(no));
        }
        return builder.toString();
    }
}
