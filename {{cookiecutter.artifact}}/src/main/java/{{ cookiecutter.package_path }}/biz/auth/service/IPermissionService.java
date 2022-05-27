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

}
