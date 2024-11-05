package {{ cookiecutter.basePackage }}.biz.social.follow;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 使用Redis实现 用户关注业务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RedisFollowServiceImpl implements FollowService {

    final StringRedisTemplate stringRedisTemplate;
    private static final String FOLLOWING_KEY = "following:%d";
    private static final String FANS_KEY = "fans:%d";
    private static final String FOLLOWING_CNT_KEY = "following:cnt:%d";
    private static final String FANS_CNT_KEY = "fans:cnt:%d";
    // 防止zset每个元素score占用内存过大，设置一个起始时间戳
    private static final long START_TS = 1704038400L;

    /**
     * 关注用户
     *
     * @param userId   用户ID
     * @param followId 被关注用户ID
     */
    @Override
    public void follow(Integer userId, Integer followId) {
        // 自己关注列表
        String key = String.format(FOLLOWING_KEY, userId);
        // 对方粉丝列表
        String key2 = String.format(FANS_KEY, followId);
        // 自己关注数 +1
        String key3 = String.format(FOLLOWING_CNT_KEY, userId);
        // 对方粉丝数 +1
        String key4 = String.format(FANS_CNT_KEY, followId);

        List<Object> results = stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            connection.zAdd(key.getBytes(), Instant.now().getEpochSecond() - START_TS, followId.toString().getBytes());
            connection.zAdd(key2.getBytes(), Instant.now().getEpochSecond() - START_TS, userId.toString().getBytes());
            connection.incr(key3.getBytes());
            connection.incr(key4.getBytes());
            return null;
        });
        log.info("follow results: {}", results);
    }

    /**
     * 取消关注
     *
     * @param userId   用户ID
     * @param followId 被关注用户ID
     */
    @Override
    public void unfollow(Integer userId, Integer followId) {
        // 自己关注列表 删除掉
        String key = String.format(FOLLOWING_KEY, userId);
        // 对方粉丝列表 删除掉自己
        String key2 = String.format(FANS_KEY, followId);
        // 自己关注数 -1
        String key3 = String.format(FOLLOWING_CNT_KEY, userId);
        // 对方粉丝数 -1
        String key4 = String.format(FANS_CNT_KEY, followId);

        List<Object> results = stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            connection.zRem(key.getBytes(), followId.toString().getBytes());
            connection.zRem(key2.getBytes(), userId.toString().getBytes());
            Long decr1 = connection.decr(key3.getBytes());
            Long decr2 = connection.decr(key4.getBytes());
            if (decr1 == null || decr1 <= 0) {
                connection.del(key3.getBytes());
            }
            if (decr2 == null || decr2 <= 0) {
                connection.del(key4.getBytes());
            }
            return null;
        });
        log.info("unfollow results: {}", results);
    }

    /**
     * 获取关注用户ID列表
     *
     * @param userId 用户ID
     * @return 关注用户ID分页
     */
    @Override
    public Page<Integer> getFollowing(Integer userId, PaginationRequest req) {
        String key = String.format(FOLLOWING_KEY, userId);
        return getIntegers(key, req.getPageNum(), req.getPageSize());
    }

    /**
     * 分页获取用户ID列表
     *
     * @param key      redis zset key
     * @param pageNum  页码
     * @param pageSize 页大小
     * @return 关注用户ID分页
     */
    private Page<Integer> getIntegers(String key, int pageNum, int pageSize) {
        Page<Integer> page = Page.of(pageNum, pageSize, 0L);
        Long total = stringRedisTemplate.opsForZSet().zCard(key);
        if (total == null || total == 0) {
            return page;
        }
        page.setTotal(total);
        int start = (pageNum - 1) * pageSize;
        int stop = start + pageSize - 1;

        Set<ZSetOperations.TypedTuple<String>> tuples = stringRedisTemplate.opsForZSet()
                .reverseRangeWithScores(key, start, stop);
        if (tuples != null && !tuples.isEmpty()) {
            List<Integer> list = tuples.stream()
                    .map(t -> Integer.valueOf(Objects.requireNonNull(t.getValue())))
                    .{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};
            page.setRecords(list);
        }

        return page;
    }

    /**
     * 获取粉丝列表
     *
     * @param userId 用户ID
     * @return 粉丝用户ID分页
     */
    @Override
    public Page<Integer> getFollowers(Integer userId, PaginationRequest req) {
        String key = String.format(FANS_KEY, userId);
        return getIntegers(key, req.getPageNum(), req.getPageSize());
    }

    /**
     * 获取关注数
     *
     * @param userId 用户ID
     * @return 关注数
     */
    @Override
    public Long countFollowing(Integer userId) {
        String key = String.format(FOLLOWING_KEY, userId);
        Long count = stringRedisTemplate.opsForZSet().zCard(key);
        return count != null ? count : 0L;
    }

    /**
     * 获取粉丝数
     *
     * @param userId 用户ID
     * @return 粉丝数
     */
    @Override
    public Long countFollowers(Integer userId) {
        String key = String.format(FANS_KEY, userId);
        Long count = stringRedisTemplate.opsForZSet().zCard(key);
        return count != null ? count : 0L;
    }

    /**
     * 判断是否关注
     *
     * @param userId   用户ID
     * @param followId 被关注用户ID
     * @return true - 已关注，false - 未关注
     */
    @Override
    public boolean isFollowing(Integer userId, Integer followId) {
        String key = String.format(FOLLOWING_KEY, userId);
        Double score = stringRedisTemplate.opsForZSet().score(key, followId.toString());
        return score != null;
    }
}
