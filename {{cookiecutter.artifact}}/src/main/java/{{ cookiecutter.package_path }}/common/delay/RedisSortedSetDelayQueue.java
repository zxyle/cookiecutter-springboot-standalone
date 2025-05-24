// 版本更新：
//    v1: 初始化功能
//    v2: 增加多队列支持

package {{ cookiecutter.basePackage }}.common.delay;

import {{ cookiecutter.basePackage }}.common.constant.DateFormatConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 使用Redis Sorted Set实现延时队列
 *
 * @author Xiang Zheng
 * @version v2
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSortedSetDelayQueue {

    final StringRedisTemplate stringRedisTemplate;
    final ApplicationContext applicationContext;
    final RedisScript<List> redisDelayQueueScript;

    /**
     * 延时队列 redis key 前缀
     */
    private static final String DELAY_QUEUE_KEY_PREFIX = "delay:";

    /**
     * 延时队列 redis key 集合
     */
    private static final Set<String> DELAY_QUEUE_KEY_SET = new HashSet<>();

    /**
     * 批处理大小
     */
    private static final int BATCH_SIZE = 50;

    /**
     * 基准点秒级时间戳，用来显著降低 zset score 数值大小，利于 Redis 存储优化
     */
    private static final long BASE_TIMESTAMP =
            LocalDateTime.of({% now 'local', '%Y, Integer.parseInt("%m"), Integer.parseInt("%d"), Integer.parseInt("%H"), Integer.parseInt("%M"), Integer.parseInt("%S")' %})
                    .atZone(ZoneId.systemDefault())
                    .toEpochSecond();

    // ================== 添加消息的三种方式 ==================

    /**
     * 添加提前多少时间的消息（如：医院挂号预约服务提醒）
     *
     * @param message    消息内容
     * @param advance    提前量
     * @param unit       时间单位（支持秒、分钟、小时、天等）
     * @param targetTime 指定时间
     */
    public void add(String queue, String message, long advance, ChronoUnit unit, LocalDateTime targetTime) {
        if (advance <= 0 || unit == null || targetTime == null) {
            throw new IllegalArgumentException("参数非法");
        }

        // 用于计算提前时间
        LocalDateTime expireTime = targetTime.minus(advance, unit);

        // 如果提前后的时间小于当前时间，则立即触发
        if (expireTime.isBefore(LocalDateTime.now())) {
            expireTime = LocalDateTime.now();
        }

        long expireTimestamp = expireTime.atZone(ZoneId.systemDefault()).toEpochSecond();
        addMessageWithExpireTime(queue, message, expireTimestamp);
    }

    /**
     * 添加延时消息（多少时间后执行，如：15分钟未支付后执行订单超时关闭）
     *
     * @param message 消息内容
     * @param delay   延时量
     * @param unit    时间单位（支持秒、分钟、小时、天等）
     * @throws IllegalArgumentException 如果delay为负数
     */
    public void add(String queue, String message, long delay, TimeUnit unit) {
        if (delay <= 0 || unit == null) {
            throw new IllegalArgumentException("参数非法");
        }

        long expireTime = Instant.now().getEpochSecond() + unit.toSeconds(delay);
        addMessageWithExpireTime(queue, message, expireTime);
    }

    /**
     * 添加定时消息
     *
     * @param message      消息内容
     * @param executedTime 执行时间
     */
    public void add(String queue, String message, LocalDateTime executedTime) {
        if (executedTime == null || executedTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("执行时间必须晚于当前时间");
        }

        long expireTime = executedTime.atZone(ZoneId.systemDefault())
                .toEpochSecond();
        addMessageWithExpireTime(queue, message, expireTime);
    }

    /**
     * 内部添加消息
     *
     * @param message    消息内容
     * @param expireTime 执行时间戳
     */
    private void addMessageWithExpireTime(String queue, String message, long expireTime) {
        if (StringUtils.isBlank(message)) {
            throw new IllegalArgumentException("消息内容不能为空");
        }

        DELAY_QUEUE_KEY_SET.add(queue);

        long relativeScore = expireTime - BASE_TIMESTAMP;
        String key = DELAY_QUEUE_KEY_PREFIX + queue;
        Boolean added = stringRedisTemplate.opsForZSet().add(key, message, relativeScore);
        if (Boolean.TRUE.equals(added)) {
            String executeTime = Instant.ofEpochSecond(expireTime)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime().format(DateFormatConst.YYYY_MM_DD_HH_MM_SS_DTF);

            log.info("延时队列：消息已添加到 {}, 执行时间 {}: {}", key, executeTime, message);
        }
    }

    /**
     * 从延时队列中删除消息
     *
     * @param message 消息内容
     */
    public void remove(String queue, String message) {
        String key = DELAY_QUEUE_KEY_PREFIX + queue;
        Long removed = stringRedisTemplate.opsForZSet().remove(key, message);
        if (removed != null && removed > 0) {
            log.info("延时队列：已从 {} 删除: {}", key, message);
        }
    }

    /**
     * 清空延时队列（防 bigkey 阻塞）
     */
    public void clear(String queue) {
        String key = DELAY_QUEUE_KEY_PREFIX + queue;
        Boolean unlinked = stringRedisTemplate.unlink(key);
        if (unlinked) {
            log.info("延时队列：{} 已清空", key);
        }
    }

    /**
     * 处理到期消息
     */
    @Scheduled(fixedRate = 1000) // 每秒轮询一次
    public void process() {
        long now = Instant.now().getEpochSecond();
        long relativeNow = now - BASE_TIMESTAMP;

        for (String queue : DELAY_QUEUE_KEY_SET) {
            String key = DELAY_QUEUE_KEY_PREFIX + queue;

            // 查询 score <= 当前时间的消息
            List messages = stringRedisTemplate.execute(
                    redisDelayQueueScript,
                    Collections.singletonList(key),
                    String.valueOf(relativeNow), String.valueOf(BATCH_SIZE)
            );

            if (CollectionUtils.isEmpty(messages)) {
                return;
            }

            log.info("延时队列：获取到 {} 条延时消息: {}", messages.size(), messages);
            for (Object message : messages) {
                String messageStr = (String) message;
                try {
                    applicationContext.publishEvent(new DelayMessageEvent(this, queue, messageStr));
                    log.info("延时队列：消息分发成功: {}", message);
                } catch (Exception e) {
                    log.error("延时队列：消息处理失败，准备重试: {}", message, e);
                }
            }

            // 查看key 剩余member数
            Long size = stringRedisTemplate.opsForZSet().zCard(key);
            if (size != null && size == 0) {
                // 如果没有剩余的消息，则删除key
                clear(queue);
                DELAY_QUEUE_KEY_SET.remove(queue);
            }
        }
    }
}