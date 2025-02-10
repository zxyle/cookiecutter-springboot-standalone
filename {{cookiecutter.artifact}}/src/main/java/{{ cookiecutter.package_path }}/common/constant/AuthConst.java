package {{ cookiecutter.basePackage }}.common.constant;

public final class AuthConst {

    private AuthConst() {
    }

    // 未锁定
    public static final int UNLOCKED = 0;

    // 锁定
    public static final int LOCKED = 1;

    public static final int SUCCESS = 1;

    public static final int FAILURE = 0;

    public static final String DELIMITER = ",";

    // 权限缓存key前缀
    public static final String KEY_PREFIX = "permissions:";

    // 不可用
    public static final int DISABLED = 0;

    // 可用
    public static final int ENABLED = 1;

    // AES加密key 需要16位
    public static final String AES_KEY = "{{ random_ascii_string(16) }}";

    /**
     * 登录默认过期天数
     */
    public static final int DEFAULT_EXPIRE_DAYS = 30;

    /**
     * 认证类型 (注意: 后面有个空格)
     */
    public static final String AUTH_SCHEME = "Bearer ";

}
