package {{ cookiecutter.basePackage }}.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务异常枚举
 */
@Getter
@AllArgsConstructor
public enum ErrorEnum {

    SERVER_ERROR(500, "服务异常"),
    PARAM_ERROR(400, "参数错误"),
    PARAM_VALID_ERROR(400, "参数校验失败"),
    AUTH_ERROR(401, "认证失败"),
    TOKEN_ERROR(401, "token错误"),
    TOKEN_EXPIRED(401, "token过期"),
    ACCESS_DENIED(403, "无权限访问"),
    DATA_NOT_FOUND(404, "数据不存在"),
    EXCEPTION(500, "系统异常"),
    ;

    private final int code;
    private final String desc;

}
