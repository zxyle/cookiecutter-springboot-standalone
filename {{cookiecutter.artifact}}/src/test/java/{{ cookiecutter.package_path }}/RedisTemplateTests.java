package {{ cookiecutter.basePackage }};

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class RedisTemplateTests {

    @Autowired
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
