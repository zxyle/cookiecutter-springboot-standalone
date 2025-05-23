package {{ cookiecutter.basePackage }}.biz.auth.user.role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.biz.auth.role.Role;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户和角色关联表 Mapper 接口
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据用户ID 查询该用户所拥有的角色信息
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<Role> findRolesByUserId(Integer userId);

    /**
     * 分页查询用户直接拥有的角色
     *
     * @param page    分页对象
     * @param userId  用户ID
     * @param request 分页请求
     * @return 角色分页对象
     */
    Page<Role> page(Page<Role> page, Integer userId, PaginationRequest request);
}
