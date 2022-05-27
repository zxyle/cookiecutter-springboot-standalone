package {{ cookiecutter.basePackage }}.biz.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {{ cookiecutter.basePackage }}.biz.auth.entity.RolePermission;
import {{ cookiecutter.basePackage }}.biz.auth.mapper.RolePermissionMapper;
import {{ cookiecutter.basePackage }}.biz.auth.service.IRolePermissionService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色权限信息 服务实现类
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {

    /**
     * 删除映射关系
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     */
    @Override
    public boolean deleteRelation(Long roleId, Long permissionId) {
        return remove(buildWrapper(roleId, permissionId));
    }

    /**
     * 查询映射关系
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     */
    @Override
    public List<RolePermission> queryRelation(Long roleId, Long permissionId) {
        QueryWrapper<RolePermission> wrapper = buildWrapper(roleId, permissionId);
        wrapper.select("role_id, permission_id");
        return list(wrapper);
    }

    /**
     * 统计映射关系
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     */
    @Override
    public Integer countRelation(Long roleId, Long permissionId) {
        return count(buildWrapper(roleId, permissionId));
    }

    /**
     * 创建映射关系
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     */
    @Override
    public boolean createRelation(Long roleId, Long permissionId) {
        RolePermission entity = new RolePermission();
        entity.setRoleId(roleId);
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
     * @param roleId       角色ID
     * @param permissionId 权限ID
     */
    @Override
    public IPage<RolePermission> pageRelation(Long roleId, Long permissionId, IPage<RolePermission> iPage) {
        QueryWrapper<RolePermission> wrapper = buildWrapper(roleId, permissionId);
        wrapper.select("role_id, permission_id");
        return page(iPage, wrapper);
    }

    // 构建wrapper
    public QueryWrapper<RolePermission> buildWrapper(Long roleId, Long permissionId) {
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq(roleId != 0L, "role_id", roleId);
        wrapper.eq(permissionId != 0L, "permission_id", permissionId);
        return wrapper;
    }
}
