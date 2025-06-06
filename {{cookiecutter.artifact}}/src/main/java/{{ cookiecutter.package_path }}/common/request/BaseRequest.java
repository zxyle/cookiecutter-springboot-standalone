package {{ cookiecutter.basePackage }}.common.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import {{ cookiecutter.namespace }}.validation.constraints.AssertTrue;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 基础请求类
 */
@Getter
@Setter
public class BaseRequest {

    protected static final ObjectMapper MAPPER = new ObjectMapper();
    protected static final List<String> IGNORE = Arrays.asList("sign", "calledSort", "offset");

    /**
     * 签名密钥，需要给前端
     */
    public static final String SECRET = "your_secret_key";

    /**
     * 请求超时时间 1分钟 = 60000毫秒
     */
    protected static final int TIMEOUT_MS = 60000;

    /**
     * 请求毫秒时间戳
     *
     * @mock 1644541518652
     */
    protected Long ts;

    /**
     * 请求体签名
     */
    protected String sign;

    /**
     * 一次性随机数
     */
    protected String nonce;

    /**
     * 校验时间戳
     */
    @AssertTrue(message = "时间戳无效")
    protected boolean isTsValid() {
        return ts == null || Math.abs(System.currentTimeMillis() - ts) <= TIMEOUT_MS;
    }

    /**
     * 校验签名，防止请求体被篡改
     */
    @AssertTrue(message = "签名无效")
    protected boolean isSignValid() {
        if (StringUtils.isBlank(sign)) {
            return true;
        }

        Map<String, Object> params = MAPPER.convertValue(this, new TypeReference<Map<String, Object>>() {
        });
        Set<String> keys = params.keySet().stream()
                .filter(key -> !IGNORE.contains(key))
                .sorted().collect(Collectors.toCollection(LinkedHashSet::new));

        String toSign = keys.stream()
                .filter(key -> params.get(key) != null)
                .map(key -> key + "=" + params.get(key))
                .collect(Collectors.joining("&"));

        String sign = DigestUtils.md5Hex(toSign + SECRET);
        return sign.equals(params.get("sign"));
    }
}