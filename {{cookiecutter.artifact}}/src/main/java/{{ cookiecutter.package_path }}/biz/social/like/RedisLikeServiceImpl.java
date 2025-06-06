package {{ cookiecutter.basePackage }}.biz.social.like;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * 点赞功能Redis实现
 *
 * @version 1.0.0
 */
// TODO 需要支持redis 事务
// TODO 是否可以使用Set来实现，减少redis空间占用
// TODO 防止bigkey问题
@Slf4j
@Service
@RequiredArgsConstructor
public class RedisLikeServiceImpl implements LikeService {

    final StringRedisTemplate stringRedisTemplate;
    private static final String LIKE_KEY = "like:%d:%d";
    private static final String LIKE_COUNT_KEY = "likeCnt:%d:%d";
    private static final String USER_LIKE_LIST_KEY = "likeList:%d:%d";
    // 防止zset每个元素score占用内存过大，设置一个起始时间戳
    private static final long BASE_TIMESTAMP = LocalDateTime.of({% now 'local', '%Y, Integer.parseInt("%m"), Integer.parseInt("%d"), Integer.parseInt("%H"), Integer.parseInt("%M"), Integer.parseInt("%S")' %})
                    .atZone(ZoneId.systemDefault())
                    .toEpochSecond();

    /**
     * 点赞
     *
     * @param resType 资源类型
     * @param resId   资源ID
     * @param userId  点赞人用户ID
     * @return 当前点赞数
     */
    public Long like(Integer resType, Integer resId, Integer userId) {
        String key = String.format(LIKE_KEY, resType, resId);
        String countKey = String.format(LIKE_COUNT_KEY, resType, resId);
        String userKey = String.format(USER_LIKE_LIST_KEY, resType, userId);

        // 检查该用户是否已经为这个内容点过赞
        if (!isLiked(resType, resId, userId)) {
            // 如果没有，则将用户ID加入到点赞集合中，并设置分数为当前时间戳
            stringRedisTemplate.opsForZSet().add(key, String.valueOf(userId), Instant.now().getEpochSecond() - BASE_TIMESTAMP);
            // 增加点赞数
            Long increment = stringRedisTemplate.opsForValue().increment(countKey);
            // 用户收藏列表添加
            stringRedisTemplate.opsForZSet().add(userKey, String.valueOf(resId), Instant.now().getEpochSecond() - BASE_TIMESTAMP);
            return increment == null || increment < 0 ? 0 : increment;
        }

        String s = stringRedisTemplate.opsForValue().get(countKey);
        return StringUtils.isNotBlank(s) ? Long.parseLong(s) : 0;
    }

    /**
     * 取消点赞
     *
     * @param resType 资源类型
     * @param resId   资源ID
     * @param userId  点赞人用户ID
     * @return 当前点赞数
     */
    public Long unlike(Integer resType, Integer resId, Integer userId) {
        String key = String.format(LIKE_KEY, resType, resId);
        String countKey = String.format(LIKE_COUNT_KEY, resType, resId);
        String userKey = String.format(USER_LIKE_LIST_KEY, resType, userId);

        if (!isLiked(resType, resId, userId)) {
            return count(resType, resId);
        }

        // 移除用户ID
        stringRedisTemplate.opsForZSet().remove(key, String.valueOf(userId));
        // 减小点赞数
        Long decrement = stringRedisTemplate.opsForValue().decrement(countKey);
        // 用户收藏列表移除
        stringRedisTemplate.opsForZSet().remove(userKey, String.valueOf(resId));

        // 如果为为负数 则删除key
        if (decrement == null || decrement < 0) {
            decrement = 0L;
            remove(resType, resId);
        }
        return decrement;
    }

    /**
     * 查询点赞数
     *
     * @param resType 资源类型
     * @param resId   资源ID
     * @return 点赞数
     */
    public Long count(Integer resType, Integer resId) {
        String countKey = String.format(LIKE_COUNT_KEY, resType, resId);
        String s = stringRedisTemplate.opsForValue().get(countKey);
        return StringUtils.isNotBlank(s) ? Long.parseLong(s) : 0;
    }

    /**
     * 判断用户是否已经为该内容点过赞
     *
     * @param resType 资源类型
     * @param resId   资源ID
     * @param userId  点赞人用户ID
     * @return true 表示已经点过赞，false 表示没有点过赞
     */
    public boolean isLiked(Integer resType, Integer resId, Integer userId) {
        String key = String.format(LIKE_KEY, resType, resId);
        Double score = stringRedisTemplate.opsForZSet().score(key, String.valueOf(userId));
        return score != null;
    }

    /**
     * 移除点赞记录
     *
     * @param resType 资源类型
     * @param resId   资源ID
     * @return true 表示移除成功，false 表示移除失败
     */
    @Override
    public boolean remove(Integer resType, Integer resId) {
        String key = String.format(LIKE_KEY, resType, resId);
        String countKey = String.format(LIKE_COUNT_KEY, resType, resId);
        return Boolean.TRUE.equals(stringRedisTemplate.delete(key)) &&
                Boolean.TRUE.equals(stringRedisTemplate.delete(countKey));
    }

    /**
     * 分页获取用户点赞的资源ID列表
     *
     * @param resType  资源类型
     * @param userId   点赞人用户ID
     * @param pageNum  分页页码
     * @param pageSize 分页大小
     * @return 资源ID列表
     */
    @Override
    public Page<LikeDTO> getResIdList(Integer resType, Integer userId, Integer pageNum, Integer pageSize) {
        Page<LikeDTO> page = Page.of(pageNum, pageSize, 0L);

        // 获取用户点赞的资源ID列表
        String key = String.format(USER_LIKE_LIST_KEY, resType, userId);
        Long total = stringRedisTemplate.opsForZSet().zCard(key);
        if (total == null || total == 0) {
            return page;
        }
        page.setTotal(total);

        int start = (pageNum - 1) * pageSize;
        int stop = start + pageSize - 1;

        Set<ZSetOperations.TypedTuple<String>> tupleSet = stringRedisTemplate.opsForZSet()
                .reverseRangeWithScores(key, start, stop);

        if (tupleSet != null && !tupleSet.isEmpty()) {
            List<LikeDTO> records = tupleSet.stream().map(tuple -> {
                LikeDTO likeDTO = new LikeDTO();
                likeDTO.setLikeTime(Instant.ofEpochSecond(Objects.requireNonNull(tuple.getScore()).longValue() + BASE_TIMESTAMP)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime());
                likeDTO.setResId(Integer.parseInt(Objects.requireNonNull(tuple.getValue())));
                return likeDTO;
            }).{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};
            page.setRecords(records);
        }

        return page;
    }

    /**
     * 返回当前用户是否点赞，且当前资源的点赞数
     *
     * @param resType 资源类型
     * @param resId   资源ID
     * @param userId  点赞人用户ID
     * @return 点赞信息
     */
    @Override
    public LikeInfo getLikeInfo(Integer resType, Integer resId, Integer userId) {
        boolean liked = isLiked(resType, resId, userId);
        Long count = count(resType, resId);

        return new LikeInfo(liked, count);
    }
}
