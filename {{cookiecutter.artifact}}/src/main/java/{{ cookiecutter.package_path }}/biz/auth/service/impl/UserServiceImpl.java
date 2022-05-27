package {{ cookiecutter.basePackage }}.biz.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {{ cookiecutter.basePackage }}.biz.auth.constant.PwdConstant;
import {{ cookiecutter.basePackage }}.biz.auth.entity.User;
import {{ cookiecutter.basePackage }}.biz.auth.entity.UserGroup;
import {{ cookiecutter.basePackage }}.biz.auth.mapper.UserMapper;
import {{ cookiecutter.basePackage }}.biz.auth.request.user.AddUserRequest;
import {{ cookiecutter.basePackage }}.biz.auth.service.IUserGroupService;
import {{ cookiecutter.basePackage }}.biz.auth.service.IUserPermissionService;
import {{ cookiecutter.basePackage }}.biz.auth.service.IUserRoleService;
import {{ cookiecutter.basePackage }}.biz.auth.service.IUserService;
import dev.zhengxiang.tool.crypto.Werkzeug;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户 服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    IUserGroupService userGroupService;

    @Autowired
    IUserRoleService userRoleService;

    @Autowired
    IUserPermissionService userPermissionService;

    /**
     * 添加用户
     */
    @Transactional
    @Override
    public User addUser(AddUserRequest request) {
        String pwd = request.getPwd();
        User user = new User();
        BeanUtils.copyProperties(request, user);
        Werkzeug werkzeug = new Werkzeug();
        pwd = StringUtils.isNotBlank(pwd) ? pwd : PwdConstant.DEFAULT_PWD;
        user.setPwd(werkzeug.generatePasswordHash(pwd));
        save(user);

        // 创建映射
        UserGroup userGroup = new UserGroup(user.getId(), request.getGroupId());
        userGroupService.save(userGroup);
        return user;
    }

    /**
     * 删除用户
     *
     * @param userId 用户ID
     */
    @Transactional
    @Override
    public boolean delete(Long userId) {
        // TODO 判断当前登录用户能否删除指定用户
        boolean s1 = removeById(userId);
        boolean s2 = userPermissionService.deleteRelation(userId, 0L);
        boolean s3 = userRoleService.deleteRelation(userId, 0L);
        boolean s4 = userGroupService.deleteRelation(userId, 0L);
        return (s1 && s2) && (s3 && s4);
    }

    /**
     * 通过ID查询用户
     *
     * @param userId 用户ID
     */
    @Override
    @Cacheable(cacheNames = "userCache", key = "#userId")
    public User queryById(Long userId) {
        return getById(userId);
    }
}
