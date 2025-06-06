package {{ cookiecutter.basePackage }}.biz.auth.password;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 密码复杂度检查工具类
 */
public final class PasswordChecker {

    private static final int MIN_LENGTH = 8;
    private static final Pattern UPPER_CASE = Pattern.compile("[A-Z]");
    private static final Pattern LOWER_CASE = Pattern.compile("[a-z]");
    private static final Pattern DIGIT = Pattern.compile("\\d");
    private static final Pattern SPECIAL = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");

    private PasswordChecker() {
    }

    /**
     * 检查密码复杂度
     *
     * @param password 密码
     * @return 0-5 分别代表弱、中、强
     */
    public static int checkPasswordComplexity(String password) {
        int score = 0;

        // Check for uppercase letters
        Matcher hasUpperCase = UPPER_CASE.matcher(password);
        if (hasUpperCase.find()) {
            score++;
        }

        // Check for lowercase letters
        Matcher hasLowerCase = LOWER_CASE.matcher(password);
        if (hasLowerCase.find()) {
            score++;
        }

        // Check for digits
        Matcher hasDigit = DIGIT.matcher(password);
        if (hasDigit.find()) {
            score++;
        }

        // Check for special characters
        Matcher hasSpecial = SPECIAL.matcher(password);
        if (hasSpecial.find()) {
            score++;
        }

        // Check for length
        if (password.length() >= MIN_LENGTH) {
            score++;
        }

        return score;
    }
}

