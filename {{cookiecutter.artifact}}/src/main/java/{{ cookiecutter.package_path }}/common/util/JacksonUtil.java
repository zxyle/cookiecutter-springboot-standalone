package {{ cookiecutter.basePackage }}.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * json工具类
 */
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
            e.printStackTrace();
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
     * 序列化
     */
    public static String serialize(Object obj) {
        String o;
        try {
            o = objectMapper.writeValueAsString(obj);
            return o;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
