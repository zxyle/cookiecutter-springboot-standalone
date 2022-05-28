package {{ cookiecutter.basePackage }}.common.request;

public class BaseRequest {

    /**
     * 请求时间毫秒戳
     *
     * @mock 1644541518652
     */
    private String ts;

    /**
     * 请求体签名
     */
    private String sign;

    /**
     * 随机数
     */
    private String nonce;

    /**
     * 请求追踪ID
     *
     * @mock 6dc01bc6-2c0d-4401-8360-af107673d71e
     */
    private String traceId;

    /**
     * 请求追踪ID
     *
     * @mock 6dc01bc6-2c0d-4401-8360-af107673d71e
     */
    private String requestId;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}
