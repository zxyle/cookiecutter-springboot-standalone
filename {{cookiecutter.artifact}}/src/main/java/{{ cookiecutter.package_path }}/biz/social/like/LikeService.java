package {{ cookiecutter.basePackage }}.biz.social.like;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

/**
 * 点赞功能接口
 *
 * @version 1.0.0
 */
// TODO 点赞和取消点赞都返回当前点赞数,还有是否成功
public interface LikeService {

    /**
     * 点赞
     *
     * @param resType 资源类型
     * @param resId   资源ID
     * @param userId  点赞人用户ID
     * @return 当前点赞数
     */
    Long like(Integer resType, Integer resId, Integer userId);

    /**
     * 取消点赞
     *
     * @param resType 资源类型
     * @param resId   资源ID
     * @param userId  点赞人用户ID
     * @return 当前点赞数
     */
    Long unlike(Integer resType, Integer resId, Integer userId);

    /**
     * 查询点赞数
     *
     * @param resType 资源类型
     * @param resId   资源ID
     * @return 当前点赞数
     */
    Long count(Integer resType, Integer resId);

    /**
     * 判断用户是否已经为该内容点过赞
     *
     * @param resType 资源类型
     * @param resId   资源ID
     * @param userId  点赞人用户ID
     * @return true 表示已经点过赞，false 表示没有点过赞
     */
    boolean isLiked(Integer resType, Integer resId, Integer userId);

    /**
     * 移除点赞记录
     *
     * @param resType 资源类型
     * @param resId   资源ID
     * @return true 表示移除成功，false 表示移除失败
     */
    boolean remove(Integer resType, Integer resId);

    /**
     * 分页获取用户点赞的资源ID列表
     *
     * @param resType  资源类型
     * @param userId   点赞人用户ID
     * @param pageNum  分页页码
     * @param pageSize 分页大小
     * @return 资源ID列表
     */
    Page<LikeDTO> getResIdList(Integer resType, Integer userId, Integer pageNum, Integer pageSize);

    /**
     * 返回当前用户是否点赞，且当前资源的点赞数
     *
     * @param resType 资源类型
     * @param resId   资源ID
     * @param userId  点赞人用户ID
     * @return 点赞信息
     */
    LikeInfo getLikeInfo(Integer resType, Integer resId, Integer userId);
}
