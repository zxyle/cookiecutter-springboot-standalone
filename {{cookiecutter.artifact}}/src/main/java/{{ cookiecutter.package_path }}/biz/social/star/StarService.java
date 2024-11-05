package {{ cookiecutter.basePackage }}.biz.social.star;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;

/**
 * 收藏服务类
 */
public interface StarService {

    /**
     * 收藏，返回当前收藏数量
     *
     * @param resType 资源类型
     * @param resId   资源ID
     * @param userId  用户ID
     * @return 收藏数量
     */
    Long star(Integer resType, Integer resId, Integer userId);

    /**
     * 取消收藏，返回当前收藏数量
     *
     * @param resType 资源类型
     * @param resId   资源ID
     * @param userId  用户ID
     * @return 收藏数量
     */
    Long unstar(Integer resType, Integer resId, Integer userId);

    /**
     * 用户是否已收藏，当前收藏数
     *
     * @param resType 资源类型
     * @param resId   资源ID
     * @param userId  用户ID
     * @return true-已收藏，false-未收藏
     */
    boolean isStarred(Integer resType, Integer resId, Integer userId);

    /**
     * 获取用户收藏的资源ID列表
     *
     * @param resType  资源类型
     * @param userId   用户ID
     * @return 资源ID列表
     */
    Page<StarDTO> getResIdList(Integer resType, Integer userId, PaginationRequest req);

}
