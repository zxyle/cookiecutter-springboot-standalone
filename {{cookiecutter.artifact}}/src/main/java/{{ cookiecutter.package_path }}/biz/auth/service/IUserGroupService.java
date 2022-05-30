package {{ cookiecutter.basePackage }}.biz.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import {{ cookiecutter.basePackage }}.biz.auth.entity.UserGroup;

import java.util.List;

/**
 * 用户-用户组关联 服务类
 */
public interface IUserGroupService extends IService<UserGroup> {

    /**
     * 删除映射关系
     *
     * @param userId  用户ID
     * @param groupId 用户组ID
     */
    boolean deleteRelation(Long userId, Long groupId);

    /**
     * 查询映射关系
     *
     * @param userId  用户ID
     * @param groupId 用户组ID
     */
    List<UserGroup> queryRelation(Long userId, Long groupId);

    /**
     * 统计映射关系
     *
     * @param userId  用户ID
     * @param groupId 用户组ID
     */
    Integer countRelation(Long userId, Long groupId);

    /**
     * 创建映射关系
     *
     * @param userId  用户ID
     * @param groupId 用户组ID
     */
    boolean createRelation(Long userId, Long groupId);

    /**
     * 分页查询映射关系
     *
     * @param userId  用户ID
     * @param groupId 用户组ID
     */
    IPage<UserGroup> pageRelation(Long userId, Long groupId, IPage<UserGroup> page);

}
