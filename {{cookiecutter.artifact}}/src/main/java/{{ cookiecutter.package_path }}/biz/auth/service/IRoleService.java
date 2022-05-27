package {{ cookiecutter.basePackage }}.biz.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import {{ cookiecutter.basePackage }}.biz.auth.entity.Role;

import java.util.List;

/**
 * 角色信息 服务类
 */
public interface IRoleService extends IService<Role> {

    /**
     * 获取用户所有角色名称
     *
     * @param userId 用户ID
     */
    List<String> getAllRoles(Long userId);

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     */
    boolean delete(Long roleId);

    /**
     * 通过ID查询角色
     *
     * @param roleId 角色ID
     */
    Role queryById(Long roleId);

}
