package {{ cookiecutter.basePackage }}.common.response;

import {{ cookiecutter.basePackage }}.common.enums.ErrorEnum;
import lombok.Data;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 通用响应结果
 */
@Data
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SUCCESS_MSG = "操作成功";
    private static final String FAILURE_MSG = "操作失败";
    private static final String TRACE_ID = "traceId";


    /**
     * 业务状态码
     *
     * @mock 200
     */
    private Integer code;

    /**
     * 信息描述
     *
     * @mock 操作成功
     */
    private String message;

    /**
     * 是否成功
     *
     * @mock true
     */
    private boolean success;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 请求追踪ID
     *
     * @mock 6dc01bc62c0d44018360af107673d71e
     */
    private String traceId;

    public static <T> R<T> ok() {
        return ok(null, SUCCESS_MSG);
    }

    public static <T> R<T> ok(T data) {
        return ok(data, SUCCESS_MSG);
    }

    public static <T> R<T> ok(T data, String message) {
        R<T> response = new R<>();
        response.setCode(HttpStatus.OK.value());
        response.setMessage(message);
        response.setSuccess(true);
        response.setData(data);
        response.setTraceId(MDC.get(TRACE_ID));
        return response;
    }

    public static <T> R<T> ok(String message) {
        return ok(null, message);
    }

    public static <T> R<T> fail(String message) {
        return fail(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public static <T> R<T> fail(String message, Integer code) {
        R<T> response = new R<>();
        response.setCode(code);
        response.setMessage(message);
        response.setSuccess(false);
        response.setTraceId(MDC.get(TRACE_ID));
        return response;
    }

    public static <T> R<T> fail(ErrorEnum errorEnum) {
        return fail(errorEnum.getDesc(), errorEnum.getCode());
    }

    public static <T> R<T> result(boolean success) {
        return success ? ok(SUCCESS_MSG) : fail(FAILURE_MSG);
    }
}
