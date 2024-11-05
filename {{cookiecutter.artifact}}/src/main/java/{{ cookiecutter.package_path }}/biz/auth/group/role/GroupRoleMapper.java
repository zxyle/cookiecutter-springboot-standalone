package {{ cookiecutter.basePackage }}.biz.auth.group.role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.biz.auth.role.Role;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 组角色关联 Mapper 接口
 */
@Mapper
public interface GroupRoleMapper extends BaseMapper<GroupRole> {

    /**
     * 查询用户组拥有的角色编码列表
     *
     * @param groupId 用户组ID
     * @return 角色列表
     */
    List<Role> findRolesByGroupId(Integer groupId);

    /**
     * 根据用户组ID列表查询角色列表
     *
     * @param groupIds 用户组ID列表
     * @return 角色列表
     */
    List<Role> findRolesByGroupIds(List<Integer> groupIds);

    /**
     * 分页查询用户组下的角色
     *
     * @param page    分页对象
     * @param groupId 用户组ID
     * @param request 分页请求
     * @return 角色分页对象
     */
    Page<Role> page(Page<Role> page, Integer groupId, PaginationRequest request);

}
