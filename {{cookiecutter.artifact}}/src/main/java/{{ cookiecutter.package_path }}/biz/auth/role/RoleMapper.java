package {{ cookiecutter.basePackage }}.biz.auth.role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色信息 Mapper 接口
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 获取用户所有角色编码
     *
     * @param userId 用户ID
     * @return 角色编码列表
     */
    List<Role> findRolesByUserId(Integer userId);

}
