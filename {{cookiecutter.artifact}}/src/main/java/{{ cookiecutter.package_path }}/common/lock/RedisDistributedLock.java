package {{ cookiecutter.basePackage }}.common.lock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 使用Redis实现分布式锁
 *
 * @author Xiang Zheng
 * @version v1
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RedisDistributedLock {

    private static final String LOCK_PREFIX = "lock:";
    private static final long DEFAULT_EXPIRE_TIME = 10000; // 锁默认过期时间：10秒
    private static final long DEFAULT_WAIT_INTERVAL = 100; // 100毫秒

    private final StringRedisTemplate redisTemplate;

    // 类初始化时缓存脚本（添加静态字段）
    private static final RedisScript<Long> UNLOCK_SCRIPT = new DefaultRedisScript<>(
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                    "return redis.call('del', KEYS[1]) " +
                    "else return 0 end",
            Long.class
    );

    /**
     * 获取分布式锁（使用默认过期时间）
     *
     * @param lockKey 业务唯一标识，如: order:123
     * @return 锁的唯一标识（成功时返回UUID，失败返回null），用于释放自己加的锁
     */
    public String lock(String lockKey) {
        return lock(lockKey, DEFAULT_EXPIRE_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取分布式锁（自定义过期时间）
     *
     * @param lockKey    业务唯一标识，如: order:123
     * @param expireTime 锁过期时间
     * @param unit       时间单位
     * @return 锁的唯一标识（成功时返回UUID，失败返回null）
     */
    public String lock(String lockKey, long expireTime, TimeUnit unit) {
        if (lockKey == null || expireTime <= 0 || unit == null) {
            return null;
        }

        String lockValue = UUID.randomUUID().toString().replace("-", ""); // 使用UUID作为唯一标识
        return doLock(lockKey, expireTime, unit, lockValue) ? lockValue : null;
    }

    private boolean doLock(String lockKey, long expireTime, TimeUnit unit, String lockValue) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(
                LOCK_PREFIX + lockKey,
                lockValue,
                expireTime,
                unit
        );

        if (Boolean.TRUE.equals(success)) {
            if (log.isDebugEnabled()) {
                log.debug("加锁成功, lockKey: {}, lockValue: {}", lockKey, lockValue);
            }
            return true;
        }

        if (log.isDebugEnabled()) {
            log.debug("加锁失败, lockKey: {}", lockKey);
        }
        return false;
    }

    /**
     * 释放分布式锁（安全版本）
     *
     * @param lockKey   业务唯一标识，如: order:123
     * @param lockValue 加锁时返回的唯一标识，防止误删其他线程的锁
     * @return 是否释放成功
     */
    public boolean unlock(String lockKey, String lockValue) {
        if (lockKey == null || lockValue == null) {
            return false;
        }

        try {
            long result = redisTemplate.execute(
                    UNLOCK_SCRIPT,
                    Collections.singletonList(LOCK_PREFIX + lockKey),
                    lockValue
            );

            if (log.isDebugEnabled() && result == 1L) {
                log.debug("释放锁成功, lockKey: {}, lockValue: {}", lockKey, lockValue);
            }

            return result == 1L;
        } catch (Exception e) {
            log.error("释放锁失败, lockKey: {}, lockValue: {}", lockKey, lockValue, e);
            return false;
        }
    }

    /**
     * 尝试获取锁（带等待超时）
     *
     * @param lockKey     业务唯一标识，如: order:123
     * @param expireTime  锁过期时间
     * @param expireUnit  过期时间单位
     * @param maxWaitTime 最大等待时间
     * @param waitUnit    等待时间单位
     * @return 锁的唯一标识（成功时返回UUID，失败返回null）
     */
    public String tryLock(String lockKey,
                          long expireTime,
                          TimeUnit expireUnit,
                          long maxWaitTime,
                          TimeUnit waitUnit) throws InterruptedException {
        final long maxWaitMillis = waitUnit.toMillis(maxWaitTime);
        assert maxWaitMillis > 0;

        final long endTime = System.currentTimeMillis() + maxWaitMillis;
        final String lockValue = UUID.randomUUID().toString().replace("-", "");

        while (System.currentTimeMillis() < endTime) {
            if (doLock(lockKey, expireTime, expireUnit, lockValue)) {
                return lockValue;
            }

            long sleepTime = DEFAULT_WAIT_INTERVAL +
                    ThreadLocalRandom.current().nextLong(50);
            Thread.sleep(sleepTime);
        }
        return null;
    }
}