package {{ cookiecutter.basePackage }}.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.List;

/**
 * Redis Lua 脚本配置类
 */
@Configuration
public class RedisLuaConfig {

    /**
     * 获取用户的权限列表
     */
    @Bean
    public DefaultRedisScript<List> userPermissionsScript() {
        DefaultRedisScript<List> script = new DefaultRedisScript<>();
        // 如果出现文件读取不到的情况，请启用一下代码
        // script.setScriptText("");
        script.setLocation(new ClassPathResource("lua/get_user_permissions.lua"));
        script.setResultType(List.class);  // 返回结果为List<String>
        return script;
    }

    /**
     * 延时队列消息获取与删除
     */
    @Bean
    public DefaultRedisScript<List> redisDelayQueueScript() {
        DefaultRedisScript<List> script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("lua/redis_delay_queue.lua"));
        script.setResultType(List.class); // Redis 返回的是一个数组
        return script;
    }
}