package {{ cookiecutter.basePackage }}.biz.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import {{ cookiecutter.basePackage }}.biz.auth.entity.GroupPermission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户组权限 Mapper 接口
 */
@Repository
public interface GroupPermissionMapper extends BaseMapper<GroupPermission> {

    List<GroupPermission> selectAll();

    // 截断表
    void truncate();

    // 查询用户组拥有的权限代码列表
    List<String> getPermissionNameByGid(long gid);

}
