package {{ cookiecutter.basePackage }}.config.security;

import {{ cookiecutter.basePackage }}.biz.auth.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录用户信息
 */
@Data
@AllArgsConstructor
public class LoginUser implements UserDetails {

    /**
     * 权限码和角色码(以ROLE_前缀)
     */
    private List<String> permissions;

    /**
     * 用户信息
     */
    private User user;

    /**
     * 密码过期时间阈值，单位：天
     */
    private Integer expireDays;

    /**
     * 获取权限列表
     *
     * @return 权限列表
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 有时redis没有保存权限码信息
        if (CollectionUtils.isEmpty(permissions) || "".equals(permissions.get(0))) {
            return Collections.emptyList();
        }
        return permissions.stream().map(SimpleGrantedAuthority::new).{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};
    }

    @Override
    public String getPassword() {
        return user.getPwd();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * 判断当前账户是否过期
     * 报AccountExpiredException: User account has expired
     *
     * @return true-未过期 false-已过期
     */
    @Override
    public boolean isAccountNonExpired() {
        if (user.getExpireTime() == null) {
            return true;
        }

        return LocalDateTime.now().isBefore(user.getExpireTime());
    }

    /**
     * 判断当前账户是否锁定
     * 报LockedException 用户帐号已被锁定 异常
     *
     * @return true-未锁定 false-已锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return !user.getLocked();
    }

    /**
     * 判断当前密码是否过期, 强制用户修改密码
     * 报 CredentialsExpiredException User credentials have expired
     *
     * @return true-未过期 false-已过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        // 判断该时间与当前时间的差距，是否大于阈值，-1代表不限制
        if (expireDays == -1) {
            return true;
        }

        Duration duration = Duration.between(user.getPwdChangeTime(), LocalDateTime.now());
        return duration.toDays() < expireDays;
    }

    /**
     * 判断当前账户是否可用
     * 报DisabledException: User is disabled
     *
     * @return true-可用 false-不可用
     */
    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }

}
