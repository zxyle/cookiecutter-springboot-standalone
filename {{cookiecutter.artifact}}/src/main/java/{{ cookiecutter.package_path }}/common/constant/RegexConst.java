package {{ cookiecutter.basePackage }}.common.constant;

/**
 * 正则表达式常量
 */
public class RegexConst {

    public static final String REGEX_MOBILE = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";

    public static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    public static final String REGEX_TEL = "^(\\d{3,4}-)?\\d{7,8}$";

    public static final String REGEX_QQ = "^[1-9]\\d{4,10}$";

    public static final String REGEX_IDCARD = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";

    public static final String REGEX_IP = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";

    public static final String REGEX_URL = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5]+$";

    public static final String REGEX_JAPANESE = "^[\u0800-\u4e00]+$";

    public static final String REGEX_KOREAN = "^[\uAC00-\uD7A3]+$";

    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    public static final String REGEX_INTEGER = "^[-\\+]?[\\d]*$";

    public static final String REGEX_DOUBLE = "^[-\\+]?[\\d]*(\\.\\d{1,2})?$";

    public static final String REGEX_ZH = "^[\u4e00-\u9fa5]+$";

    public static final String REGEX_POSTCODE = "^[1-9]\\d{5}(?!\\d)$";

    public static final String REGEX_IPV4 = "^(\\d{1,3}\\.){3}\\d{1,3}$";

}
