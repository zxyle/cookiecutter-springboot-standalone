package {{ cookiecutter.basePackage }}.biz.auth.user.permission;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.biz.auth.permission.Permission;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户-权限关联 Mapper 接口
 */
@Mapper
public interface UserPermissionMapper extends BaseMapper<UserPermission> {

    /**
     * 查询用户直接拥有的权限代码列表
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<Permission> findPermissionsByUserId(Integer userId);

    /**
     * 分页查询用户直接拥有的权限
     *
     * @param page    分页对象
     * @param userId  用户ID
     * @param request 分页请求
     * @return 权限分页对象
     */
    Page<Permission> page(Page<Permission> page, Integer userId, PaginationRequest request);

}
