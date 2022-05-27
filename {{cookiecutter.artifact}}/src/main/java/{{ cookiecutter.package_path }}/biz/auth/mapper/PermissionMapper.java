package {{ cookiecutter.basePackage }}.biz.auth.mapper;

import {{ cookiecutter.basePackage }}.biz.auth.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限信息 Mapper 接口
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> selectAll();

    // 截断表
    void truncate();

    // 查询用户所拥有的权限代码列表
    List<String> queryPermissionsByUid(@Param("uid") long uid);

}
