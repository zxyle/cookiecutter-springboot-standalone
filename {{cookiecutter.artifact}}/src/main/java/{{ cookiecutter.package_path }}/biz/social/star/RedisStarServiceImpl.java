package {{ cookiecutter.basePackage }}.biz.social.star;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 使用Redis实现 收藏业务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RedisStarServiceImpl implements StarService {

    final StringRedisTemplate stringRedisTemplate;
    private static final String STAR_KEY = "star:%d:%d";
    private static final String STAR_COUNT_KEY = "starCnt:%d:%d";
    private static final String USER_STAR_LIST_KEY = "starList:%d:%d";
    // 防止zset每个元素score占用内存过大，设置一个起始时间戳
    private static final long BASE_TIMESTAMP = LocalDateTime.of({% now 'local', '%Y, Integer.parseInt("%m"), Integer.parseInt("%d"), Integer.parseInt("%H"), Integer.parseInt("%M"), Integer.parseInt("%S")' %})
                    .atZone(ZoneId.systemDefault())
                    .toEpochSecond();

    /**
     * 收藏，返回当前收藏数量
     *
     * @param resType 资源类型
     * @param resId   资源ID
     * @param userId  用户ID
     * @return 收藏数量
     */
    @Override
    public Long star(Integer resType, Integer resId, Integer userId) {
        String key = String.format(STAR_KEY, resType, resId);
        String countKey = String.format(STAR_COUNT_KEY, resType, resId);
        String userKey = String.format(USER_STAR_LIST_KEY, resType, userId);

        List<Object> results = stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            // 资源增加用户
            connection.zAdd(key.getBytes(), Instant.now().getEpochSecond() - BASE_TIMESTAMP, userId.toString().getBytes());
            // 点赞数 +1
            connection.incr(countKey.getBytes());
            // 用户收藏列表添加
            connection.zAdd(userKey.getBytes(), Instant.now().getEpochSecond() - BASE_TIMESTAMP, resId.toString().getBytes());
            return null;
        });
        log.info("star: {}", results);
        return 0L;
    }

    /**
     * 取消收藏，返回当前收藏数量
     *
     * @param resType 资源类型
     * @param resId   资源ID
     * @param userId  用户ID
     * @return 收藏数量
     */
    @Override
    public Long unstar(Integer resType, Integer resId, Integer userId) {
        String key = String.format(STAR_KEY, resType, resId);
        String countKey = String.format(STAR_COUNT_KEY, resType, resId);
        String userKey = String.format(USER_STAR_LIST_KEY, resType, userId);

        List<Object> results = stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            // 资源用户列表移除
            connection.zRem(key.getBytes(), userId.toString().getBytes());
            // 点赞数 -1
            connection.decr(countKey.getBytes());
            // 用户收藏列表移除
            connection.zRem(userKey.getBytes(), resId.toString().getBytes());
            return null;
        });
        log.info("unstar: {}", results);
        return 0L;
    }

    /**
     * 用户是否已收藏，当前收藏数
     *
     * @param resType 资源类型
     * @param resId   资源ID
     * @param userId  用户ID
     * @return true-已收藏，false-未收藏
     */
    @Override
    public boolean isStarred(Integer resType, Integer resId, Integer userId) {
        Double score = stringRedisTemplate.opsForZSet().score(String.format(STAR_KEY, resType, resId), userId.toString());
        return score != null;
    }

    /**
     * 获取用户收藏的资源ID列表
     *
     * @param resType  资源类型
     * @param userId   用户ID
     * @return 资源ID列表
     */
    @Override
    public Page<StarDTO> getResIdList(Integer resType, Integer userId, PaginationRequest req) {
        Page<StarDTO> page = Page.of(req.getPageNum(), req.getPageSize(), 0L);
        String userKey = String.format(USER_STAR_LIST_KEY, resType, userId);

        Long total = stringRedisTemplate.opsForZSet().zCard(userKey);
        if (total == null || total == 0) {
            return page;
        }
        page.setTotal(total);

        int start = (req.getPageNum() - 1) * req.getPageSize();
        int stop = start + req.getPageSize() - 1;

        Set<ZSetOperations.TypedTuple<String>> tupleSet = stringRedisTemplate.opsForZSet()
                .reverseRangeWithScores(userKey, start, stop);

        if (tupleSet != null && !tupleSet.isEmpty()) {
            List<StarDTO> list = tupleSet.stream().map(tuple -> {
                StarDTO starDTO = new StarDTO();
                starDTO.setStarTime(Instant.ofEpochSecond(Objects.requireNonNull(tuple.getScore()).longValue() + BASE_TIMESTAMP)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime());
                starDTO.setResId(Integer.parseInt(Objects.requireNonNull(tuple.getValue())));
                return starDTO;
            }).{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};
            page.setRecords(list);
        }

        return page;
    }
}
