package {{ cookiecutter.basePackage }}.biz.social.like;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 列表、详情页点赞信息
 */
@Data
@AllArgsConstructor
public class LikeInfo {

    /**
     * 当前用户是否点赞
     *
     * @mock true
     */
    private boolean liked;

    /**
     * 当前点赞数
     *
     * @mock 100
     */
    private Long likeCount;
}
