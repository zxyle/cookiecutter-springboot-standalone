package {{ cookiecutter.basePackage }}.biz.sys.counter;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Map计数器实现
 */
@Service
public class MapCounterServiceImpl implements CounterService {

    private static final String FORMAT = "%s:%s";
    private static final Map<String, Long> COUNTER_MAP = new ConcurrentHashMap<>();

    /**
     * 自增并获取统计次数
     *
     * @param biz 业务名
     * @param id  ID
     */
    @Override
    public Long incr(String biz, String id) {
        String key = String.format(FORMAT, biz, id);
        COUNTER_MAP.put(key, COUNTER_MAP.getOrDefault(key, 0L) + 1);
        return COUNTER_MAP.get(key);
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
        String key = String.format(FORMAT, biz, id);
        COUNTER_MAP.put(key, COUNTER_MAP.getOrDefault(key, 0L) + step);
        return COUNTER_MAP.get(key);
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
        COUNTER_MAP.put(key, COUNTER_MAP.getOrDefault(key, 0L) - 1);
        return COUNTER_MAP.get(key);
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
        return COUNTER_MAP.getOrDefault(key, 0L);
    }

    @Override
    public List<Long> batchGet(String biz, List<Integer> ids) {
        return Collections.emptyList();
    }

    /**
     * 清除统计次数
     *
     * @param biz 业务名
     * @param id  ID
     */
    @Override
    public boolean clear(String biz, String id) {
        String key = String.format(FORMAT, biz, id);
        COUNTER_MAP.remove(key);
        return true;
    }

    @Override
    public boolean batchClear(String biz, List<Integer> ids) {
        return true;
    }
}
