package {{ cookiecutter.basePackage }}.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 反序列化
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

    // 序列化
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
