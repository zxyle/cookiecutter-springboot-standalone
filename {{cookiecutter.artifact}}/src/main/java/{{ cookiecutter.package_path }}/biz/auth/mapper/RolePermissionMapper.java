package {{ cookiecutter.basePackage }}.biz.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import {{ cookiecutter.basePackage }}.biz.auth.entity.RolePermission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色权限信息 Mapper 接口
 */
@Repository
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    List<RolePermission> selectAll();

    // 截断表
    void truncate();

    // 查询角色所拥有的权限列表
    List<String> getPermissionNameByRid(long roleId);

}
