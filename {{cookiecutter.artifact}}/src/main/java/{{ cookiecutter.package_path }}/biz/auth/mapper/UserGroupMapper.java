package {{ cookiecutter.basePackage }}.biz.auth.mapper;

import {{ cookiecutter.basePackage }}.biz.auth.entity.User;
import {{ cookiecutter.basePackage }}.biz.auth.entity.UserGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户-用户组关联 Mapper 接口
 */
@Repository
public interface UserGroupMapper extends BaseMapper<UserGroup> {

    List<UserGroup> selectAll();

    // 截断表
    void truncate();

    // 查询用户组下所有用户
    List<User> listUsers(long groupId);

}
