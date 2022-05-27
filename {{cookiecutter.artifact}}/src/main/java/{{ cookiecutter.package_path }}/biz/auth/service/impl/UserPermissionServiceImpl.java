package {{ cookiecutter.basePackage }}.biz.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {{ cookiecutter.basePackage }}.biz.auth.entity.UserPermission;
import {{ cookiecutter.basePackage }}.biz.auth.mapper.UserPermissionMapper;
import {{ cookiecutter.basePackage }}.biz.auth.service.IUserPermissionService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户-权限关联 服务实现类
 */
@Service
public class UserPermissionServiceImpl extends ServiceImpl<UserPermissionMapper, UserPermission> implements IUserPermissionService {

    /**
     * 删除映射关系
     *
     * @param userId       用户ID
     * @param permissionId 权限ID
     */
    @Override
    public boolean deleteRelation(Long userId, Long permissionId) {
        return remove(buildWrapper(userId, permissionId));
    }

    /**
     * 查询映射关系
     *
     * @param userId       用户ID
     * @param permissionId 权限ID
     */
    @Override
    public List<UserPermission> queryRelation(Long userId, Long permissionId) {
        QueryWrapper<UserPermission> wrapper = buildWrapper(userId, permissionId);
        wrapper.select("user_id, permission_id");
        return list(wrapper);
    }

    /**
     * 统计映射关系
     *
     * @param userId       用户ID
     * @param permissionId 权限ID
     */
    @Override
    public Integer countRelation(Long userId, Long permissionId) {
        return count(buildWrapper(userId, permissionId));
    }

    /**
     * 创建映射关系
     *
     * @param userId       用户ID
     * @param permissionId 权限ID
     */
    @Override
    public boolean createRelation(Long userId, Long permissionId) {
        UserPermission entity = new UserPermission();
        entity.setUserId(userId);
        entity.setPermissionId(permissionId);
        try {
            save(entity);
        } catch (DuplicateKeyException ignored) {
        }
        return true;
    }

    /**
     * 分页查询映射关系
     *
     * @param userId       用户ID
     * @param permissionId 权限ID
     */
    @Override
    public IPage<UserPermission> pageRelation(Long userId, Long permissionId, IPage<UserPermission> iPage) {
        QueryWrapper<UserPermission> wrapper = buildWrapper(userId, permissionId);
        wrapper.select("user_id, permission_d");
        return page(iPage, wrapper);
    }

    // 构建wrapper
    public QueryWrapper<UserPermission> buildWrapper(Long userId, Long permissionId) {
        QueryWrapper<UserPermission> wrapper = new QueryWrapper<>();
        wrapper.eq(userId != 0L, "user_id", userId);
        wrapper.eq(permissionId != 0L, "permission_id", permissionId);
        return wrapper;
    }
}
