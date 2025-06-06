package {{ cookiecutter.basePackage }}.biz.auth.user.group;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.biz.auth.group.Group;
import {{ cookiecutter.basePackage }}.common.request.auth.ListAuthRequest;
import {{ cookiecutter.basePackage }}.biz.auth.user.User;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户-用户组关联 Mapper 接口
 */
@Mapper
public interface UserGroupMapper extends BaseMapper<UserGroup> {

    /**
     * 查询用户组下所有用户
     *
     * @param groupId 用户组ID
     * @return 用户列表
     */
    List<User> listUsers(Integer groupId);

    /**
     * 查询用户所属的用户组
     *
     * @param userId 用户ID
     * @return 用户组列表
     */
    List<Group> listGroups(Integer userId);

    /**
     * 分页查询用户所属的用户组
     *
     * @param page    分页对象
     * @param userId  用户ID
     * @param request 分页请求
     * @return 用户组分页对象
     */
    Page<Group> page(Page<Group> page, Integer userId, PaginationRequest request);

    /**
     * 分页查询用户组下的用户
     *
     * @param page    分页对象
     * @param groupId 用户组ID
     * @param request 请求对象
     * @return 用户分页对象
     */
    Page<User> pageUser(Page<User> page, Integer groupId, ListAuthRequest request);

}

