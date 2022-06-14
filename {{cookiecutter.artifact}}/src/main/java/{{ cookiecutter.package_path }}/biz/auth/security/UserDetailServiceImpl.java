package {{ cookiecutter.basePackage }}.biz.auth.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import {{ cookiecutter.basePackage }}.biz.auth.constant.AuthConstant;
import {{ cookiecutter.basePackage }}.biz.auth.entity.User;
import {{ cookiecutter.basePackage }}.biz.auth.service.IPermissionService;
import {{ cookiecutter.basePackage }}.biz.auth.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

// 参考文章： https://blog.csdn.net/qq_22075913/article/details/125148535
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    IUserService userService;

    @Autowired
    IPermissionService permissionService;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 根据用户名查询用户信息
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("login_name", username);
        User one = userService.getOne(wrapper);

        // 如果没有查询到对应用户
        if (null == one) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        long userId = one.getId();

        // 查询用户所有权限码和用户所有角色码
        List<String> permissions = permissionService.getSecurityPermissions(userId);

        // 将权限码和角色码存入redis
        String key = "permissions:" + userId;
        stringRedisTemplate.opsForValue().set(key, String.join(AuthConstant.DELIMITER, permissions), 1, TimeUnit.DAYS);
        return new LoginUser(permissions, one);
    }
}
