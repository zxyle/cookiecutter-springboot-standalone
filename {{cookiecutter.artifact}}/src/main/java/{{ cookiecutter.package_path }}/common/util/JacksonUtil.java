package {{ cookiecutter.basePackage }}.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * json工具类
 */
@Slf4j
public final class JacksonUtil {

    private JacksonUtil() {
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 反序列化
     */
    public static <T> T deserialize(String body, Class<T> t) {
        T response;
        try {
            response = MAPPER.readValue(body, t);
            return response;
        } catch (JsonProcessingException e) {
            log.error("error: ", e);
        }

        return null;
    }

    /**
     * 反序列化数组
     */
    public static <T> List<T> deserializeArray(String body) {
        List<T> list = new ArrayList<>();
        try {
            list = MAPPER.readValue(body, List.class);
            return list;
        } catch (JsonProcessingException e) {
            return list;
        }
    }

    /**
     * 序列化对象
     */
    public static String serialize(Object obj) {
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String o;
        try {
            // 解决无法序列化LocalDateTime问题
            MAPPER.registerModule(new JavaTimeModule());
            o = MAPPER.writeValueAsString(obj);
            return o;
        } catch (JsonProcessingException e) {
            log.error("error: ", e);
        }
        return null;
    }
}
