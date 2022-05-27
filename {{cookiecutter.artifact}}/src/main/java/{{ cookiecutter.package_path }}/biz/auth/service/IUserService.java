package {{ cookiecutter.basePackage }}.biz.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import {{ cookiecutter.basePackage }}.biz.auth.entity.User;
import {{ cookiecutter.basePackage }}.biz.auth.request.user.AddUserRequest;

/**
 * 用户 服务类
 */
public interface IUserService extends IService<User> {

    /**
     * 添加用户
     */
    User addUser(AddUserRequest request);

    /**
     * 删除用户
     *
     * @param userId 用户ID
     */
    boolean delete(Long userId);

    /**
     * 通过ID查询用户
     *
     * @param uid 用户ID
     */
    User queryById(Long uid);

}
