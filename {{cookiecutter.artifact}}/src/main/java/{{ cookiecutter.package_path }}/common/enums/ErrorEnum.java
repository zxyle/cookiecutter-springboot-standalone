package {{ cookiecutter.basePackage }}.common.enums;


/**
 * 业务异常枚举
 */
public enum ErrorEnum {

    ERROR_9999("服务异常", "9999"),
    ;

    private final String message;
    private final String code;

    ErrorEnum(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
