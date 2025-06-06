package {{ cookiecutter.basePackage }}.biz.auth.mfa;

import {{ cookiecutter.basePackage }}.common.constant.RegexConst;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * 账号工具类
 */
public final class AccountUtil {

    private AccountUtil() {
    }

    /**
     * 判断账号类型
     *
     * @param account 账号
     */
    public static AccountEnum judge(String account) {
        if (Pattern.matches(RegexConst.REGEX_EMAIL, account)) {
            return AccountEnum.EMAIL;
        }

        if (Pattern.matches(RegexConst.REGEX_MOBILE, account)) {
            return AccountEnum.MOBILE;
        }

        // 用户名仅支持中英文、数字和下划线,且不能为纯数字
        if (Pattern.matches(RegexConst.REGEX_USERNAME, account) && !StringUtils.isNumeric(account)) {
            return AccountEnum.USERNAME;
        }

        // 非法字符串
        return AccountEnum.ILLEGAL;
    }

    /**
     * 判断账号类型 (下同)
     *
     * @param account 账号
     */
    public static boolean isUsername(String account) {
        return judge(account) == AccountEnum.USERNAME;
    }

    public static boolean isEmail(String account) {
        return judge(account) == AccountEnum.EMAIL;
    }

    public static boolean isMobile(String account) {
        return judge(account) == AccountEnum.MOBILE;
    }

}
