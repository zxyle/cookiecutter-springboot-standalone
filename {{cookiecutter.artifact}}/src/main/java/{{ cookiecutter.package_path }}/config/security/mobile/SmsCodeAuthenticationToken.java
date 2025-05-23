package {{ cookiecutter.basePackage }}.config.security.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * 验证码登录认证Token
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 2383092775910246006L;

    /**
     * 账号 （手机号或邮箱）
     */
    private final Object principal;

    /**
     * 验证码
     */
    private final Object credentials;

    /**
     * SmsCodeAuthenticationFilter中构建的未认证的Authentication
     *
     * @param principal   账号 （手机号或邮箱）
     * @param credentials 验证码
     */
    public SmsCodeAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
