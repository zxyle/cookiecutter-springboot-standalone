package {{ cookiecutter.basePackage }}.biz.site.rank;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import {{ cookiecutter.namespace }}.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Redis ZSet 实现排行榜服务
 */
@Service
public class RankService {

    /**
     * 排行榜key格式，举例 rank:article 热门文章排行榜，member存储的是文章ID，score是文章的阅读数
     */
    private static final String RANK_KEY = "rank:%s";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public String getKey(String biz) {
        return String.format(RANK_KEY, biz);
    }

    /**
     * 添加成员及其分数到排行榜中
     *
     * @param biz    排行榜的键, 比如文章article
     * @param member 成员信息，可以是文章ID、用户ID、热搜关键词
     * @param score  成员的分数
     */
    public void addMember(String biz, String member, double score) {
        String key = getKey(biz);
        stringRedisTemplate.opsForZSet().add(key, member, score);
    }

    /**
     * 获取成员的分数
     *
     * @param biz    排行榜的键, 比如文章article
     * @param member 成员信息，可以是文章ID、用户ID、热搜关键词
     * @return 成员的分数
     */
    public Double getMemberScore(String biz, String member) {
        String key = getKey(biz);
        return stringRedisTemplate.opsForZSet().score(key, member);
    }

    /**
     * 获取成员在排行榜中的排名
     *
     * @param biz    排行榜的键, 比如文章article
     * @param member 成员信息，可以是文章ID、用户ID、热搜关键词
     * @return 成员的排名
     */
    public Long getMemberRank(String biz, String member) {
        String key = getKey(biz);
        Long rank = stringRedisTemplate.opsForZSet().reverseRank(key, member);
        if (rank == null) {
            return 0L;
        }
        // 排名需要增加1
        return rank + 1;
    }

    /**
     * 获取排行榜中指定范围内的成员及其分数
     *
     * @param biz 排行榜的键, 比如文章article
     * @param k   要获取的成员数量
     * @return 成员和分数组成的列表
     */
    public List<TopResponse> getTopMember(String biz, int k) {
        long start = 0;
        long end = k - 1;
        String key = getKey(biz);
        Set<ZSetOperations.TypedTuple<String>> tuples = stringRedisTemplate.opsForZSet()
                .reverseRangeWithScores(key, start, end);
        if (tuples == null || tuples.isEmpty()) {
            return Collections.emptyList();
        }
        return tuples.stream().map(
                tuple -> new TopResponse(tuple.getValue(), "", tuple.getScore())
        ).{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};
    }

    /**
     * 增加成员的分数
     *
     * @param biz    排行榜的键, 比如文章article
     * @param member 成员信息，可以是文章ID、用户ID、热搜关键词
     * @param delta  增加的分数
     * @return 增加后的分数
     */
    public Double incrMemberScore(String biz, String member, double delta) {
        String key = getKey(biz);
        return stringRedisTemplate.opsForZSet().incrementScore(key, member, delta);
    }

    /**
     * 删除排行榜中的成员
     *
     * @param biz    排行榜的键, 比如文章article
     * @param member 成员信息，可以是文章ID、用户ID、热搜关键词
     */
    public void removeMember(String biz, String member) {
        String key = getKey(biz);
        stringRedisTemplate.opsForZSet().remove(key, member);
    }

    /**
     * 删除排行榜中排名在指定名次之后的成员
     *
     * @param biz  排行榜的键, 比如文章article
     * @param rank 排名阈值（不包含）
     */
    public void removeResOutsideTop(String biz, long rank) {
        String key = getKey(biz);
        long start = 0;
        long end = -(rank + 1);
        stringRedisTemplate.opsForZSet().removeRange(key, start, end);
    }

}