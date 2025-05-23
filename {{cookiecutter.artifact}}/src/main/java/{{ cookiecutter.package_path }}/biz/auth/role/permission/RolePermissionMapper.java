package {{ cookiecutter.basePackage }}.biz.auth.role.permission;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.biz.auth.permission.Permission;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色权限信息 Mapper 接口
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    /**
     * 查询角色所拥有的权限列表
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<Permission> findPermissionsByRoleId(Integer roleId);

    /**
     * 根据角色ID列表查询权限列表
     *
     * @param roleIds 角色ID列表
     * @return 权限列表
     */
    List<Permission> findPermissionsByRoleIds(List<Integer> roleIds);

    /**
     * 分页查询角色拥有的权限
     *
     * @param page    分页对象
     * @param roleId  角色ID
     * @param request 分页请求
     * @return 权限分页对象
     */
    Page<Permission> page(Page<Permission> page, Integer roleId, PaginationRequest request);

}
