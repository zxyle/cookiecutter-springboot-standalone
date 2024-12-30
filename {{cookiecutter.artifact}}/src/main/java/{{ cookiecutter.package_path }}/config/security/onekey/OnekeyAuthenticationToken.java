package {{ cookiecutter.basePackage }}.config.security.onekey;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * 本机手机号授权登录认证Token
 */
public class OnekeyAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 2383092775910246016L;

    /**
     * 账号（手机号）
     */
    private final Object principal;

    /**
     * 验证码
     */
    private final Object credentials;

    /**
     * WechatAuthenticationFilter中构建的未认证的Authentication
     *
     * @param principal   账号 （手机号或邮箱）
     * @param credentials 验证码
     */
    public OnekeyAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);  // 默认未认证
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
