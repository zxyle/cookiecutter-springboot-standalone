package {{ cookiecutter.basePackage }}.biz.auth.security;

import {{ cookiecutter.basePackage }}.biz.auth.constant.AuthConst;
import {{ cookiecutter.basePackage }}.biz.auth.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    // 获取权限列表
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPwd();
    }

    @Override
    public String getUsername() {
        return user.getLoginName();
    }

    /**
     * 判断当前账户是否过期（true-未过期 false-已过期）
     */
    @Override
    public boolean isAccountNonExpired() {
        // 会抛出 LockedException: 用户帐号已被锁定
        if (user.getExpireTime() == null) {
            return true;
        }

        return LocalDateTime.now().isBefore(user.getExpireTime());
    }

    /**
     * 判断当前账户是否锁定（true-未锁定 false-已锁定）
     */
    @Override
    public boolean isAccountNonLocked() {
        // 会抛出LockedException 用户帐号已被锁定 异常
        return user.getUserLock().equals(AuthConst.UNLOCKED);
    }

    /**
     * 判断当前密码是否过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 判断当前账户是否可用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

}
