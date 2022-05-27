package {{ cookiecutter.basePackage }}.biz.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import {{ cookiecutter.basePackage }}.biz.auth.entity.UserPermission;

import java.util.List;

/**
 * 用户-权限关联 服务类
 */
public interface IUserPermissionService extends IService<UserPermission> {

    /**
     * 删除映射关系
     *
     * @param userId       用户ID
     * @param permissionId 权限ID
     */
    boolean deleteRelation(Long userId, Long permissionId);

    /**
     * 查询映射关系
     *
     * @param userId       用户ID
     * @param permissionId 权限ID
     */
    List<UserPermission> queryRelation(Long userId, Long permissionId);

    /**
     * 统计映射关系
     *
     * @param userId       用户ID
     * @param permissionId 权限ID
     */
    Integer countRelation(Long userId, Long permissionId);

    /**
     * 创建映射关系
     *
     * @param userId       用户ID
     * @param permissionId 权限ID
     */
    boolean createRelation(Long userId, Long permissionId);

    /**
     * 分页查询映射关系
     *
     * @param userId       用户ID
     * @param permissionId 权限ID
     */
    IPage<UserPermission> pageRelation(Long userId, Long permissionId, IPage<UserPermission> page);

}
