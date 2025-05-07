package {{ cookiecutter.basePackage }}.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.List;

@Configuration
public class RedisLuaConfig {

    /**
     * 获取用户的权限列表
     */
    @Bean
    public DefaultRedisScript<List> userPermissionsScript() {
        DefaultRedisScript<List> script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("lua/get_user_permissions.lua"));
        script.setResultType(List.class);  // 返回结果为List<String>
        return script;
    }
}