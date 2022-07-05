package {{ cookiecutter.basePackage }}.biz.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import {{ cookiecutter.basePackage }}.biz.auth.entity.RolePermission;

import java.util.List;

/**
 * 角色权限信息 服务类
 */
public interface IRolePermissionService extends IService<RolePermission> {

    /**
     * 删除映射关系
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     */
    boolean deleteRelation(Long roleId, Long permissionId);

    /**
     * 查询映射关系
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     */
    List<RolePermission> queryRelation(Long roleId, Long permissionId);

    /**
     * 统计映射关系
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     */
    Integer countRelation(Long roleId, Long permissionId);

    /**
     * 创建映射关系
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     */
    boolean createRelation(Long roleId, Long permissionId);

    /**
     * 分页查询映射关系
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     */
    IPage<RolePermission> pageRelation(Long roleId, Long permissionId, IPage<RolePermission> page);


    /**
     * 查询角色所拥有的权限列表
     *
     * @param roleId 角色ID
     */
    List<String> getPermissionNameByRoleId(long roleId);

}
