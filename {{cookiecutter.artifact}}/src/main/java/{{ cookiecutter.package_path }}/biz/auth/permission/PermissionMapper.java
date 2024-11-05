package {{ cookiecutter.basePackage }}.biz.auth.permission;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 权限信息 Mapper 接口
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 查询用户所拥有的权限代码列表
     *
     * @param userId 用户ID
     * @return 权限代码列表
     */
    List<String> findPermissionsByUserId(Integer userId);

    /**
     * 查询最大排序号
     *
     * @param parentId 父级ID
     * @return 最大排序号
     */
    Integer findMaxSortByParentId(Integer parentId);

    /**
     * 获取用户所有权限编码
     *
     * @param userId 用户ID
     * @return 权限ID列表
     */
    List<Integer> findPermissionIds(Integer userId);
}
