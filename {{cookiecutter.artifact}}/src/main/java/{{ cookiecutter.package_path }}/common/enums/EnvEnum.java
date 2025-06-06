package {{ cookiecutter.basePackage }}.common.enums;

/**
 * 环境枚举
 */
public enum EnvEnum {

    /**
     * 开发环境
     */
    DEV("dev"),

    /**
     * 测试环境
     */
    TEST("test"),

    /**
     * 生产环境
     */
    PROD("prod");

    private String code;

    EnvEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
