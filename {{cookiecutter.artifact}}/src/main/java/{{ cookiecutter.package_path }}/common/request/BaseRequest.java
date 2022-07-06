package {{ cookiecutter.basePackage }}.common.request;

public class BaseRequest {

    /**
     * 请求毫秒时间戳
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
