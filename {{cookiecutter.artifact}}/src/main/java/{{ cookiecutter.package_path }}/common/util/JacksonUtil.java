package {{ cookiecutter.basePackage }}.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * json工具类
 */
@Slf4j
public class JacksonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 反序列化
     */
    public static <T> T deserialize(String body, Class<T> t) {
        T response;
        try {
            response = objectMapper.readValue(body, t);
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
            list = objectMapper.readValue(body, List.class);
            return list;
        } catch (JsonProcessingException e) {
            return list;
        }
    }

    /**
     * 序列化对象
     */
    public static String serialize(Object obj) {
        String o;
        try {
            o = objectMapper.writeValueAsString(obj);
            return o;
        } catch (JsonProcessingException e) {
            log.error("error: ", e);
        }
        return null;
    }
}
