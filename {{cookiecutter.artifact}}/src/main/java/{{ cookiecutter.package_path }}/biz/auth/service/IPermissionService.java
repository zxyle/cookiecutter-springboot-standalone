package {{ cookiecutter.basePackage }}.biz.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import {{ cookiecutter.basePackage }}.biz.auth.entity.Permission;

import java.util.List;

/**
 * 权限信息 服务类
 */
public interface IPermissionService extends IService<Permission> {

    /**
     * 获取用户所有权限名称
     *
     * @param userId 用户ID
     */
    List<String> getAllPermissions(Long userId);

    /**
     * 删除权限
     *
     * @param permissionId 权限ID
     */
    boolean delete(Long permissionId);

    /**
     * 查询用户所有权限码和用户所有角色码
     *
     * @param userId 用户ID
     */
    List<String> getSecurityPermissions(Long userId);

    /**
     * 重新将新的权限码和角色码加载到redis中
     *
     * @param userId 用户ID
     */
    boolean refreshPermissions(Long userId);

    /**
     * 获取持有该权限的用户
     *
     * @param code 权限码
     */
    List<Long> holdPermission(String code);

}
