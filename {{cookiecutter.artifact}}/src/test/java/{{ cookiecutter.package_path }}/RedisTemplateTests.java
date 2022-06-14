package {{ cookiecutter.basePackage }};

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTemplateTests {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Test
    void basic() {
        stringRedisTemplate.opsForValue().set("foo", "bar");
        System.out.println(stringRedisTemplate.opsForValue().get("foo"));
    }

    @Test
    void increment() {
        Long times = stringRedisTemplate.opsForValue().increment("numbers");
        System.out.println("You clicked " + times + " times.");
    }
}
