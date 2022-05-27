package {{ cookiecutter.basePackage }}.biz.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import {{ cookiecutter.basePackage }}.biz.auth.entity.UserRole;

import java.util.List;

/**
 * 用户和角色关联表 服务类
 */
public interface IUserRoleService extends IService<UserRole> {

    /**
     * 删除映射关系
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    boolean deleteRelation(Long userId, Long roleId);

    /**
     * 查询映射关系
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    List<UserRole> queryRelation(Long userId, Long roleId);

    /**
     * 统计映射关系
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    Integer countRelation(Long userId, Long roleId);

    /**
     * 创建映射关系
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    boolean createRelation(Long userId, Long roleId);

    /**
     * 分页查询映射关系
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    IPage<UserRole> pageRelation(Long userId, Long roleId, IPage<UserRole> page);

}
