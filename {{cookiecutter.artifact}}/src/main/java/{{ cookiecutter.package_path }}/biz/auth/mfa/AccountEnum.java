package {{ cookiecutter.basePackage }}.biz.auth.mfa;

/**
 * 账号类型枚举
 */
public enum AccountEnum {

    /**
     * 用户名
     */
    USERNAME,

    /**
     * 手机号
     */
    MOBILE,

    /**
     * 邮箱
     */
    EMAIL,

    /**
     * 非法字符串
     */
    ILLEGAL;

}
