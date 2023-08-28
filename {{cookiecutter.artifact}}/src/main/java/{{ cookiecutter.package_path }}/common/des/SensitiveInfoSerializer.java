package {{ cookiecutter.basePackage }}.common.des;

import cn.hutool.core.util.DesensitizedUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;

/**
 * 脱敏序列化
 */
public class SensitiveInfoSerializer extends JsonSerializer<String> implements ContextualSerializer {

    /**
     * hutool工具类脱敏类型
     */
    private DesensitizedUtil.DesensitizedType desensitizedType;

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(DesensitizedUtil.desensitized(s, desensitizedType));
    }


    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        Sensitive annotation = beanProperty.getAnnotation(Sensitive.class);
        if (annotation != null) {
            this.desensitizedType = annotation.value();
            return this;
        }

        return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
    }

}
