package {{ cookiecutter.basePackage }}.common.request;

public class BaseRequest {

    /**
     * timestamp
     */
    private String ts;

    /**
     * signature
     */
    private String sign;

    /**
     * Number used once
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
