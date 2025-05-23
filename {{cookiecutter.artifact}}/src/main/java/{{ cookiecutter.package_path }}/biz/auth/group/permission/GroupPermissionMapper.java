package {{ cookiecutter.basePackage }}.biz.auth.group.permission;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.biz.auth.permission.Permission;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户组权限 Mapper 接口
 */
@Mapper
public interface GroupPermissionMapper extends BaseMapper<GroupPermission> {

    /**
     * 查询用户组拥有的权限代码列表
     *
     * @param groupId 用户组ID
     * @return 权限代码列表
     */
    List<Permission> findPermissionsByGroupId(Integer groupId);

    /**
     * 查询用户组拥有的权限代码列表
     *
     * @param groupIds 用户组ID列表
     * @return 权限代码列表
     */
    List<Permission> findPermissionsByGroupIds(List<Integer> groupIds);

    /**
     * 分页查询用户组下的权限
     *
     * @param page    分页对象
     * @param groupId 用户组ID
     * @param request 分页请求
     * @return 权限分页对象
     */
    Page<Permission> page(Page<Permission> page, Integer groupId, PaginationRequest request);

}
