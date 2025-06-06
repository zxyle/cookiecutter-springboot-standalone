package {{ cookiecutter.basePackage }}.biz.sys.counter;

import {{ cookiecutter.namespace }}.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 使用Redis String实现计数器
 */
@Service
@ConditionalOnClass(StringRedisTemplate.class)
public class RedisStringCounterServiceImpl implements CounterService {

    /**
     * 计数key格式由 业务名:ID 组成，业务名需要尽可能短
     */
    private static final String FORMAT = "%s:%s";

    @Resource
    StringRedisTemplate stringRedisTemplate;

    /**
     * 自增并获取统计次数
     *
     * @param biz 业务名
     * @param id  ID
     */
    @Override
    public Long incr(String biz, String id) {
        return incr(biz, id, 1);
    }

    /**
     * 自增并获取统计次数
     *
     * @param biz  业务名
     * @param id   ID
     * @param step 自增步长
     * @return 自增后的统计次数
     */
    @Override
    public Long incr(String biz, String id, Integer step) {
        if (step == null || step < 1) {
            step = 1;
        }
        String key = String.format(FORMAT, biz, id);
        Long count = stringRedisTemplate.opsForValue().increment(key, step);
        return count != null ? count : 0;
    }

    /**
     * 自减并获取统计次数
     *
     * @param biz 业务名
     * @param id  ID
     * @return 自减后的统计次数
     */
    @Override
    public Long decr(String biz, String id) {
        String key = String.format(FORMAT, biz, id);
        Long count = stringRedisTemplate.opsForValue().decrement(key);
        if (count != null && count <= 0) {
            stringRedisTemplate.delete(key);
            return 0L;
        }
        return count != null ? count : 0;
    }

    /**
     * 获取统计次数
     *
     * @param biz 业务名
     * @param id  ID
     */
    @Override
    public Long get(String biz, String id) {
        String key = String.format(FORMAT, biz, id);
        String count = stringRedisTemplate.opsForValue().get(key);
        return StringUtils.isNumeric(count) ? Long.parseLong(count) : 0L;
    }

    /**
     * 使用mget批量获取统计次数
     *
     * @param biz 业务名
     * @param ids ID
     * @return 统计次数列表
     */
    @Override
    public List<Long> batchGet(String biz, List<Integer> ids) {
        List<String> keys = ids.stream().map(id -> String.format(FORMAT, biz, id)).{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};

        List<String> values = stringRedisTemplate.opsForValue().multiGet(keys);
        if (values != null && !values.isEmpty()) {
            return values.stream()
                    .map(result -> result == null ? 0 : result)
                    .map(result -> StringUtils.isNumeric(result.toString()) ? Long.parseLong(result.toString()) : 0L)
                    .{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};
        }
        return Collections.emptyList();
    }

    /**
     * 清除浏览统计次数
     *
     * @param biz 业务名
     * @param id  ID
     */
    @Override
    public boolean clear(String biz, String id) {
        String key = String.format(FORMAT, biz, id);
        Boolean hasKey = stringRedisTemplate.hasKey(key);
        if (!hasKey) {
            return true;
        }
        return stringRedisTemplate.delete(key);
    }

    /**
     * 批量清除统计次数
     *
     * @param biz 业务名
     * @param ids ID列表
     * @return 批量清除结果
     */
    @Override
    public boolean batchClear(String biz, List<Integer> ids) {
        List<String> keys = ids.stream().map(id -> String.format(FORMAT, biz, id)).{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};
        Long delete = stringRedisTemplate.delete(keys);
        return delete == ids.size();
    }
}

