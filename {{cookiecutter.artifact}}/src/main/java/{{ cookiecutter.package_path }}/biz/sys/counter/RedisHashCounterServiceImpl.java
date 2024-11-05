package {{ cookiecutter.basePackage }}.biz.sys.counter;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import {{ cookiecutter.namespace }}.annotation.Resource;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 使用Redis Hash来存储和管理多个统计值。
 * 每个资源的统计值作为哈希字段存储，字段名作为计数器名称，字段值作为计数器值。
 */
@Service
public class RedisHashCounterServiceImpl {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    /**
     * 增加计数器的值。
     *
     * @param biz   业务标识
     * @param field 计数器的字段名
     * @param id    资源ID
     * @param num   增加的数量，可以为负数
     * @return 更新后的计数器值
     */
    public Long incr(String biz, String field, Integer id, Integer num) {
        if (num == null) {
            num = 1;
        } else if (num == 0) {
            return getCount(biz, field, id);
        }

        String key = String.format("%s:%d", biz, id);
        return stringRedisTemplate.opsForHash().increment(key, field, num);
    }

    /**
     * 增加计数器的值，默认增加1。
     *
     * @param biz   业务标识
     * @param field 计数器的字段名
     * @param id    资源ID
     * @return 更新后的计数器值
     */
    public Long incr(String biz, String field, Integer id) {
        return incr(biz, field, id, 1);
    }

    /**
     * 减少计数器的值，默认减少1。
     *
     * @param biz   业务标识
     * @param field 计数器的字段名
     * @param id    资源ID
     * @return 更新后的计数器值
     */
    public Long decr(String biz, String field, Integer id) {
        return decr(biz, field, id, 1);
    }

    /**
     * 减少计数器的值。
     *
     * @param biz   业务标识
     * @param field 计数器的字段名
     * @param id    资源ID
     * @param num   减少的数量，可以为负数
     * @return 更新后的计数器值
     */
    public Long decr(String biz, String field, Integer id, Integer num) {
        if (num == null) {
            num = 1;
        } else if (num == 0) {
            return getCount(biz, field, id);
        }

        String key = String.format("%s:%d", biz, id);
        return stringRedisTemplate.opsForHash().increment(key, field, -num);
    }

    /**
     * 获取指定资源的所有计数器值。
     *
     * @param biz 业务标识
     * @param id  资源ID
     * @return 计数器值的Map
     */
    public Map<String, Long> get(String biz, Integer id) {
        String key = String.format("%s:%d", biz, id);
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(key);
        return entries.entrySet().stream().collect(
                Collectors.toMap(
                        entry -> (String) entry.getKey(),
                        entry -> Long.parseLong(entry.getValue().toString())
                )
        );
    }

    /**
     * 获取指定资源和字段的计数器值。
     *
     * @param biz   业务标识
     * @param field 计数器的字段名
     * @param id    资源ID
     * @return 计数器值
     */
    public Long getCount(String biz, String field, Integer id) {
        String key = String.format("%s:%d", biz, id);
        Object value = stringRedisTemplate.opsForHash().get(key, field);
        return value != null ? Long.parseLong(value.toString()) : 0L;
    }

}
