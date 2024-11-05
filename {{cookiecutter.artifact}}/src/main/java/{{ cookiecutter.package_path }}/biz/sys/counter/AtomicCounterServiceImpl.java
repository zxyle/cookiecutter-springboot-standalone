package {{ cookiecutter.basePackage }}.biz.sys.counter;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 原子计数器
 */
@Service
public class AtomicCounterServiceImpl implements CounterService {

    private static final String FORMAT = "%s:%s";
    private static final Map<String, AtomicLong> COUNTER_MAP = new ConcurrentHashMap<>();

    @Override
    public Long incr(String biz, String id) {
        String key = String.format(FORMAT, biz, id);
        COUNTER_MAP.putIfAbsent(key, new AtomicLong(0));
        return COUNTER_MAP.get(key).incrementAndGet();
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
        for (int i = 0; i < step; i++) {
            incr(biz, id);
        }
        return get(biz, id);
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
        return COUNTER_MAP.get(key).decrementAndGet();
    }

    @Override
    public Long get(String biz, String id) {
        String key = String.format(FORMAT, biz, id);
        if (COUNTER_MAP.containsKey(key)) {
            return COUNTER_MAP.get(key).get();
        }
        return 0L;
    }

    @Override
    public List<Long> batchGet(String biz, List<Integer> ids) {
        return Collections.emptyList();
    }

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
