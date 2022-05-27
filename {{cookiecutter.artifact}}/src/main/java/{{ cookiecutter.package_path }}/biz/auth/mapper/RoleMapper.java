package {{ cookiecutter.basePackage }}.biz.auth.mapper;

import {{ cookiecutter.basePackage }}.biz.auth.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色信息 Mapper 接口
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectAll();

    // 截断表
    void truncate();

}
