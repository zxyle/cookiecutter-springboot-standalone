package {{ cookiecutter.basePackage }}.config.security;

import {{ cookiecutter.basePackage }}.biz.auth.permission.Permission;
import {{ cookiecutter.basePackage }}.biz.auth.permission.SecurityPermissions;
import {{ cookiecutter.basePackage }}.biz.auth.role.permission.RolePermissionMapper;
import {{ cookiecutter.basePackage }}.biz.auth.role.permission.RolePermissionService;
import {{ cookiecutter.basePackage }}.biz.auth.user.role.UserRoleService;
import {{ cookiecutter.basePackage }}.biz.auth.permission.PermissionService;
import {{ cookiecutter.basePackage }}.biz.auth.user.User;
import {{ cookiecutter.basePackage }}.biz.auth.user.UserService;
import {{ cookiecutter.basePackage }}.biz.sys.setting.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 自定义用户信息加载
 * <a href="https://blog.csdn.net/qq_22075913/article/details/125148535">参考文章</a>
 */
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    final UserService userService;
    final PermissionService permissionService;
    final SettingService setting;
    final UserRoleService userRoleService;
    final RolePermissionMapper rolePermissionMapper;
    final RolePermissionService rolePermissionService;

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
        SecurityPermissions securityPermissions = permissionService.getSecurityPermissions2(userId);
        List<String> permissions = securityPermissions.getPermissions();
        List<Integer> roleIds = securityPermissions.getRoleIds();
        userRoleService.saveRolesToRedis(userId, roleIds);

        List<Permission> permissionList = rolePermissionMapper.findPermissionsByRoleIds(roleIds);
        Map<Integer, List<Permission>> groupingMap = permissionList.stream().collect(Collectors.groupingBy(Permission::getRoleId));
        for (Map.Entry<Integer, List<Permission>> entry : groupingMap.entrySet()) {
            rolePermissionService.savePermissionCodesToRedis(entry.getKey(), entry.getValue().stream().map(Permission::getCode).collect(Collectors.toList()));
        }

        Integer expireDays = setting.get("pwd.expire-days").getIntValue();
        return new LoginUser(permissions, user, expireDays);
    }
}
