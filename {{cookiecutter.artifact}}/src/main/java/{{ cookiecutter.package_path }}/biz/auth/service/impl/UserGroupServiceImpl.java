package {{ cookiecutter.basePackage }}.biz.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {{ cookiecutter.basePackage }}.biz.auth.entity.UserGroup;
import {{ cookiecutter.basePackage }}.biz.auth.mapper.UserGroupMapper;
import {{ cookiecutter.basePackage }}.biz.auth.service.IUserGroupService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户-用户组关联 服务实现类
 */
@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup> implements IUserGroupService {


    /**
     * 删除映射关系
     *
     * @param userId  用户ID
     * @param groupId 用户组ID
     */
    @Override
    public boolean deleteRelation(Long userId, Long groupId) {
        return remove(buildWrapper(userId, groupId));
    }

    /**
     * 查询映射关系
     *
     * @param userId  用户ID
     * @param groupId 用户组ID
     */
    @Override
    public List<UserGroup> queryRelation(Long userId, Long groupId) {
        QueryWrapper<UserGroup> wrapper = buildWrapper(userId, groupId);
        wrapper.select("user_id, group_id");
        return list(wrapper);
    }

    /**
     * 统计映射关系
     *
     * @param userId  用户ID
     * @param groupId 用户组ID
     */
    @Override
    public Integer countRelation(Long userId, Long groupId) {
        return count(buildWrapper(userId, groupId));
    }

    /**
     * 创建映射关系
     *
     * @param userId  用户ID
     * @param groupId 用户组ID
     */
    @Override
    public boolean createRelation(Long userId, Long groupId) {
        UserGroup entity = new UserGroup(userId, groupId);
        try {
            save(entity);
        } catch (DuplicateKeyException ignored) {
        }
        return true;
    }

    /**
     * 分页查询映射关系
     *
     * @param userId  用户ID
     * @param groupId 用户组ID
     */
    @Override
    public IPage<UserGroup> pageRelation(Long userId, Long groupId, IPage<UserGroup> iPage) {
        QueryWrapper<UserGroup> wrapper = buildWrapper(userId, groupId);
        wrapper.select("user_id, group_id");
        return page(iPage, wrapper);
    }

    // 构建wrapper
    public QueryWrapper<UserGroup> buildWrapper(Long userId, Long groupId) {
        QueryWrapper<UserGroup> wrapper = new QueryWrapper<>();
        wrapper.eq(userId != 0L, "user_id", userId);
        wrapper.eq(groupId != 0L, "group_id", groupId);
        return wrapper;
    }

}
