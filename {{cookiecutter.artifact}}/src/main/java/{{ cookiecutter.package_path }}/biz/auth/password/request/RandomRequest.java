package {{ cookiecutter.basePackage }}.biz.auth.password.request;

import lombok.Data;

import {{ cookiecutter.namespace }}.validation.constraints.AssertTrue;
import {{ cookiecutter.namespace }}.validation.constraints.DecimalMin;

/**
 * 生成随机密码请求
 */
@Data
public class RandomRequest {

    private static final String LETTER = "abcdefghijklmnopqrstuvwxyz";

    /**
     * 密码长度
     *
     * @mock 8
     */
    @DecimalMin(value = "1", message = "密码长度不能小于1")
    private int length;

    /**
     * 是否包含小写字母
     */
    private boolean hasLower;

    /**
     * 是否包含大写字母
     */
    private boolean hasUpper;

    /**
     * 是否包含数字
     */
    @AssertTrue(message = "密码必须包含数字")
    private boolean hasDigit;

    /**
     * 是否包含特殊字符
     */
    private boolean hasSpecialChar;

    /**
     * 生成数量
     *
     * @mock 100
     */
    private int count;

    public String getChars() {
        StringBuilder builder = new StringBuilder();
        if (hasLower) {
            builder.append(LETTER);
        }
        if (hasUpper) {
            builder.append(LETTER.toUpperCase());
        }
        if (hasDigit) {
            builder.append("0123456789");
        }

        if (hasSpecialChar) {
            builder.append("!@#$%^&*");
        }

        return builder.toString();
    }

    public Integer getCount() {
        return count <= 0 ? 1 : count;
    }
}
