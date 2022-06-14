package {{ cookiecutter.basePackage }}.common.response;

import lombok.ToString;

@ToString
public class ApiResponse<T> {

    public static final String SUCCESS_MSG = "操作成功";

    public static final String FAILURE_MSG = "操作失败";

    /**
     * 状态码
     */
    private String code;

    /**
     * 消息
     */
    private String message;

    /**
     * 是否成功
     */
    private boolean success;

    private Object data;

    public ApiResponse() {
    }

    public ApiResponse(String message) {
        setData(null);
        setMessage(message);
        setCode("0");
        setSuccess(true);
    }

    public ApiResponse(String message, boolean success) {
        setData(null);
        setMessage(message);
        setCode("0");
        setSuccess(success);
    }

    public ApiResponse(T data) {
        setData(data);
        setMessage(SUCCESS_MSG);
        setCode("0");
        setSuccess(true);
    }

    public ApiResponse(boolean success) {
        setSuccess(success);
        setMessage(success ? SUCCESS_MSG : FAILURE_MSG);
        setCode("0");
    }

    public ApiResponse<T> ok() {
        return ok(null);
    }

    public ApiResponse<T> ok(T data) {
        setMessage(SUCCESS_MSG);
        setCode("0");
        setSuccess(true);
        setData(data);
        return this;
    }

    public ApiResponse(String code, String message, boolean success, Object data) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public ApiResponse(String code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
