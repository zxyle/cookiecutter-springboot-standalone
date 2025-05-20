package {{ cookiecutter.basePackage }}.biz.sys.captcha;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证码工具类
 * <p>
 * 用于生成各种类型的随机验证码，支持数字、字母、特殊字符等多种组合。
 * </p>
 */
public final class RandomUtil {

    private RandomUtil() {
        // 私有构造函数，防止实例化
    }

    private static final SecureRandom RANDOM = new SecureRandom();

    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGIT = "0123456789";
    private static final String SPECIAL_CHAR = "!@#$%^&*";
    
    // 常用字符集组合的缓存
    private static final Map<String, String> CHAR_SET_CACHE = new HashMap<>();
    
    // 字符集类型常量
    private static final int TYPE_LOWER = 1;
    private static final int TYPE_UPPER = 2;
    private static final int TYPE_DIGIT = 4;
    private static final int TYPE_SPECIAL = 8;
    private static final int TYPE_ALPHA = TYPE_LOWER | TYPE_UPPER;
    private static final int TYPE_ALPHANUM = TYPE_ALPHA | TYPE_DIGIT;
    private static final int TYPE_ALL = TYPE_ALPHANUM | TYPE_SPECIAL;

    /**
     * 生成纯数字验证码
     *
     * @param length 验证码长度
     * @return 随机数字验证码
     * @throws IllegalArgumentException 如果长度小于等于0
     */
    public static String randNumber(int length) {
        return randCode(length, TYPE_DIGIT);
    }

    /**
     * 生成纯字母验证码（包含大小写）
     *
     * @param length 验证码长度
     * @return 随机字母验证码
     * @throws IllegalArgumentException 如果长度小于等于0
     */
    public static String randAlphabet(int length) {
        return randCode(length, TYPE_ALPHA);
    }
    
    /**
     * 生成字母数字混合验证码
     *
     * @param length 验证码长度
     * @return 随机字母数字验证码
     * @throws IllegalArgumentException 如果长度小于等于0
     */
    public static String randAlphanumeric(int length) {
        return randCode(length, TYPE_ALPHANUM);
    }
    
    /**
     * 生成包含所有字符类型的验证码
     *
     * @param length 验证码长度
     * @return 随机验证码
     * @throws IllegalArgumentException 如果长度小于等于0
     */
    public static String randAll(int length) {
        return randCode(length, TYPE_ALL);
    }

    /**
     * 根据指定的字符类型生成随机验证码
     *
     * @param length 验证码长度
     * @param type 字符类型组合（使用位运算组合多种类型）
     * @return 随机验证码
     * @throws IllegalArgumentException 如果长度小于等于0或类型无效
     */
    public static String randCode(int length, int type) {
        if (length <= 0) {
            throw new IllegalArgumentException("验证码长度必须大于零");
        }
        
        if (type <= 0 || type > TYPE_ALL) {
            throw new IllegalArgumentException("无效的字符类型");
        }
        
        String key = "type_" + type;
        String chars = CHAR_SET_CACHE.get(key);
        
        if (chars == null) {
            StringBuilder charSetBuilder = new StringBuilder(LOWER.length() + UPPER.length() + DIGIT.length() + SPECIAL_CHAR.length());

            if ((type & TYPE_LOWER) != 0) {
                charSetBuilder.append(LOWER);
            }
            if ((type & TYPE_UPPER) != 0) {
                charSetBuilder.append(UPPER);
            }
            if ((type & TYPE_DIGIT) != 0) {
                charSetBuilder.append(DIGIT);
            }
            if ((type & TYPE_SPECIAL) != 0) {
                charSetBuilder.append(SPECIAL_CHAR);
            }

            chars = charSetBuilder.toString();
            CHAR_SET_CACHE.put(key, chars);
        }
        
        return randCode(length, chars);
    }

    /**
     * 使用布尔参数指定字符类型生成随机验证码
     *
     * @param length 验证码长度
     * @param hasUpper 是否包含大写字母
     * @param hasLower 是否包含小写字母
     * @param hasDigit 是否包含数字
     * @param hasSpecialChar 是否包含特殊字符
     * @return 随机验证码
     * @throws IllegalArgumentException 如果长度小于等于0或未指定任何字符类型
     */
    public static String randCode(int length, boolean hasUpper, boolean hasLower, boolean hasDigit, boolean hasSpecialChar) {
        if (length <= 0) {
            throw new IllegalArgumentException("验证码长度必须大于零");
        }
        
        if (!hasUpper && !hasLower && !hasDigit && !hasSpecialChar) {
            throw new IllegalArgumentException("至少需要指定一种字符类型");
        }
        
        String key = String.format("bool_%b_%b_%b_%b", hasUpper, hasLower, hasDigit, hasSpecialChar);
        String chars = CHAR_SET_CACHE.get(key);
        
        if (chars == null) {
            StringBuilder charSetBuilder = new StringBuilder(
                    (hasLower ? LOWER.length() : 0) +
                    (hasUpper ? UPPER.length() : 0) +
                    (hasDigit ? DIGIT.length() : 0) +
                    (hasSpecialChar ? SPECIAL_CHAR.length() : 0)
            );
            
            if (hasLower) {
                charSetBuilder.append(LOWER);
            }
            if (hasUpper) {
                charSetBuilder.append(UPPER);
            }
            if (hasDigit) {
                charSetBuilder.append(DIGIT);
            }
            if (hasSpecialChar) {
                charSetBuilder.append(SPECIAL_CHAR);
            }
            
            chars = charSetBuilder.toString();
            CHAR_SET_CACHE.put(key, chars);
        }
        
        return randCode(length, chars);
    }

    /**
     * 使用自定义字符集生成随机验证码
     *
     * @param length 验证码长度
     * @param chars 自定义字符集
     * @return 随机验证码
     * @throws IllegalArgumentException 如果长度小于等于0或字符集为空
     */
    public static String randCode(int length, String chars) {
        if (length <= 0) {
            throw new IllegalArgumentException("验证码长度必须大于零");
        }
        
        if (chars == null || chars.isEmpty()) {
            throw new IllegalArgumentException("字符集不能为空");
        }
        
        int charLength = chars.length();
        StringBuilder builder = new StringBuilder(length);
        
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(charLength);
            builder.append(chars.charAt(index));
        }
        
        return builder.toString();
    }
    
    /**
     * 生成指定强度的密码
     * 
     * @param length 密码长度
     * @param strength 密码强度（1-4，数字越大强度越高）
     * @return 随机密码
     * @throws IllegalArgumentException 如果长度小于等于0或强度无效
     */
    public static String generatePassword(int length, int strength) {
        if (strength < 1 || strength > 4) {
            throw new IllegalArgumentException("密码强度必须在1到4之间");
        }
        
        switch (strength) {
            case 1: return randNumber(length);
            case 2: return randAlphabet(length);
            case 3: return randAlphanumeric(length);
            case 4: return randAll(length);
            default: throw new IllegalArgumentException("无效的密码强度");
        }
    }

    public static void main(String[] args) {
        System.out.println(generatePassword(8, 2));
        System.out.println(randAlphanumeric(4));
    }
}