package {{ cookiecutter.basePackage }}.config.security;

import {{ cookiecutter.basePackage }}.common.constant.AuthConst;
import {{ cookiecutter.basePackage }}.biz.auth.permission.PermissionService;
import {{ cookiecutter.basePackage }}.biz.auth.user.User;
import {{ cookiecutter.basePackage }}.biz.auth.user.UserService;
import {{ cookiecutter.basePackage }}.biz.sys.setting.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 自定义用户信息加载
 * <a href="https://blog.csdn.net/qq_22075913/article/details/125148535">参考文章</a>
 */
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    final UserService userService;
    final PermissionService permissionService;
    final StringRedisTemplate stringRedisTemplate;
    final SettingService setting;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        // 根据账号查询用户信息
        User user = userService.findByAccount(account);

        // 如果没有查询到对应用户
        if (null == user) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        int userId = user.getId();

        // 查询用户所有权限码和用户所有角色码
        List<String> permissions = permissionService.getSecurityPermissions2(userId);

        // 将权限码和角色码存入redis
        String key = AuthConst.KEY_PREFIX + userId;
        String join = String.join(AuthConst.DELIMITER, permissions);
        // TODO 没权限是否要存redis？
        stringRedisTemplate.opsForValue().set(key, join, AuthConst.DEFAULT_EXPIRE_DAYS, TimeUnit.DAYS);
        Integer expireDays = setting.get("pwd.expire-days").getIntValue();
        return new LoginUser(permissions, user, expireDays);
    }
}
