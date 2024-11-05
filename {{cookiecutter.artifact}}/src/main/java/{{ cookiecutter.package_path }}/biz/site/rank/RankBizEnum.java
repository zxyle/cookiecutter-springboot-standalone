package {{ cookiecutter.basePackage }}.biz.site.rank;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 排行榜业务枚举
 */
@Getter
@AllArgsConstructor
public enum RankBizEnum {

    /**
     * top-排行榜
     */
    TOP("top", "排行榜"),

    /**
     * like-点赞排行榜
     */
    LIKE("like", "点赞排行榜"),

    /**
     * comment-评论排行榜
     */
    COMMENT("comment", "评论排行榜"),

    /**
     * article-热门文章排行榜
     */
    ARTICLE("article", "热门文章排行榜"),

    /**
     * hot-热搜排行榜
     */
    HOT("hot", "热搜排行榜"),

    /**
     * view-浏览排行榜
     */
    VIEW("view", "浏览排行榜");

    /**
     * 业务名称
     */
    private final String code;

    /**
     * 业务描述
     */
    private final String desc;
}
