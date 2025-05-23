package {{ cookiecutter.basePackage }}.biz.social.follow;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 个人关注统计
 */
@Data
@AllArgsConstructor
public class FollowStatResponse {

    /**
     * 关注数
     *
     * @mock 100
     */
    private Long followingCount;

    /**
     * 粉丝数
     *
     * @mock 200
     */
    private Long followerCount;
}
