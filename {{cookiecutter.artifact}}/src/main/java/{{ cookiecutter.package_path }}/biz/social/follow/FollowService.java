package {{ cookiecutter.basePackage }}.biz.social.follow;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;

/**
 * 用户关注 服务类
 */
public interface FollowService {

    /**
     * 关注
     *
     * @param userId   用户ID
     * @param followId 被关注用户ID
     */
    void follow(Integer userId, Integer followId);

    /**
     * 取消关注
     *
     * @param userId   用户ID
     * @param followId 被关注用户ID
     */
    void unfollow(Integer userId, Integer followId);

    /**
     * 获取关注列表
     *
     * @param userId   用户ID
     * @return 关注用户ID分页
     */
    Page<Integer> getFollowing(Integer userId, PaginationRequest req);

    /**
     * 获取粉丝列表
     *
     * @param userId   用户ID
     * @return 粉丝用户ID分页
     */
    Page<Integer> getFollowers(Integer userId, PaginationRequest req);

    /**
     * 获取关注数
     *
     * @param userId 用户ID
     * @return 关注数
     */
    Long countFollowing(Integer userId);

    /**
     * 获取粉丝数
     *
     * @param userId 用户ID
     * @return 粉丝数
     */
    Long countFollowers(Integer userId);

    /**
     * 判断是否关注
     *
     * @param userId   用户ID
     * @param followId 被关注用户ID
     * @return true - 已关注，false - 未关注
     */
    boolean isFollowing(Integer userId, Integer followId);

}
