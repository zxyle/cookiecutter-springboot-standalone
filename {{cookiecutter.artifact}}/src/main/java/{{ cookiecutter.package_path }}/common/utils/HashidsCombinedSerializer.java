package {{ cookiecutter.basePackage }}.common.utils;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;


/**
 * 序列化器和反序列化器
 */
@JsonComponent   //此注释会自动将 Serialize和Deserializer注册到jackson之中。
public class HashidsCombinedSerializer {
    public static class Serialize extends JsonSerializer<Long> {
        @Override
        public void serialize(Long ids, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            String hash = HashidsUtils.encode(ids);
            jsonGenerator.writeString(hash);
        }
    }

    public static class Deserialize extends JsonDeserializer<Long> {
        @Override
        public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String hash = jsonParser.getValueAsString();
            return HashidsUtils.decode(hash);
        }
    }
}


