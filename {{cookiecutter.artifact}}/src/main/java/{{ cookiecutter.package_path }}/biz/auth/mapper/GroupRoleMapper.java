package {{ cookiecutter.basePackage }}.biz.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import {{ cookiecutter.basePackage }}.biz.auth.entity.GroupRole;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 组角色关联 Mapper 接口
 */
@Repository
public interface GroupRoleMapper extends BaseMapper<GroupRole> {

    List<GroupRole> selectAll();

    // 截断表
    @Update("TRUNCATE TABLE auth_group_role")
    void truncate();

    // 查询用户组拥有的角色编码列表
    List<String> getRoleNameByGid(long gid);

}
