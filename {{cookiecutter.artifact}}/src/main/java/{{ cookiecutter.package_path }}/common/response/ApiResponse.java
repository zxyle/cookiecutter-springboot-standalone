package {{ cookiecutter.basePackage }}.common.response;


public class ApiResponse<T> {

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

    public ApiResponse(T data) {
        setData(data);
    }

    public ApiResponse<T> ok() {
        return ok(null);
    }

    public ApiResponse<T> ok(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setMessage("成功");
        response.setCode("0");
        response.setSuccess(true);
        response.setData(data);
        return response;
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

