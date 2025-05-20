package {{ cookiecutter.basePackage }}.common.constant;

/**
 * 正则表达式常量
 */
public final class RegexConst {

    private RegexConst() {
    }

    // 中国大陆手机号：1开头，第二位是3-9，后面跟着9位数字
    public static final String REGEX_MOBILE = "^1(3[0-9]|4[57]|5[0-35-9]|6[2567]|7[0-3,5-8]|8\\d|9\\d)\\d{8}$";

    public static final String REGEX_EMAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    public static final String REGEX_TEL = "^(\\d{3,4}-)?\\d{7,8}$";

    // QQ号从10000开始，最长不超过10位
    public static final String REGEX_QQ = "^[1-9]\\d{4,9}$";

    public static final String REGEX_ID_NUM = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";

    public static final String REGEX_IP = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";

    public static final String REGEX_URL = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5]+$";

    public static final String REGEX_JAPANESE = "^[\u0800-\u4e00]+$";

    public static final String REGEX_KOREAN = "^[\uAC00-\uD7A3]+$";

    // 5-18位字母、数字、下划线，必须以字母开头
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{4,17}$";

    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    public static final String REGEX_INTEGER = "^[-\\+]?[\\d]*$";

    public static final String REGEX_DOUBLE = "^[-\\+]?[\\d]*(\\.\\d{1,2})?$";

    public static final String REGEX_ZH = "^[\u4e00-\u9fa5]+$";

    // 中国大陆邮政编码：第一位不能是0，后面紧跟 5位数字，严格限制只能有6位数字
    public static final String REGEX_POSTCODE = "^[1-9]\\d{5}(?!\\d)$";

    public static final String REGEX_IPV4 = "^(\\d{1,3}\\.){3}\\d{1,3}$";

    public static final String REGEX_DATE = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";

    // yyyy-MM-dd HH:mm:ss
    public static final String REGEX_DATETIME = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]) ([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$";

    // HH:mm:ss
    public static final String REGEX_TIME = "^([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$";

    // UUID: 1e6a9d02-0eb1-4316-ad31-94010bfecd4d
    public static final String REGEX_UUID = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";

}
