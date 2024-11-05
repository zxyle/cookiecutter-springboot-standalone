package {{ cookiecutter.basePackage }}.config.security.mobile;

import {{ cookiecutter.basePackage }}.biz.auth.user.User;
import {{ cookiecutter.basePackage }}.biz.auth.user.UserService;
import {{ cookiecutter.basePackage }}.biz.sys.captcha.ValidateService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 验证码登录认证Provider
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    UserDetailsService userDetailsService;
    ValidateService validateService;
    UserService userService;

    public SmsCodeAuthenticationProvider(UserDetailsService userDetailsService, ValidateService validateService, UserService userService) {
        this.userDetailsService = userDetailsService;
        this.validateService = validateService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;

        // 验证验证码
        String account = (String) authenticationToken.getPrincipal();
        String smsCode = (String) authenticationToken.getCredentials();

        // 验证码验证
        String key = "code:" + account;
        if (!validateService.validate(key, smsCode)) {
            throw new BadCredentialsException("输入的验证码不正确或可能过期，请重新输入");
        }

        // 检查当前手机号是否已注册，没有则创建用户
        User account1 = userService.findByAccount(account);
        if (account1 == null) {
            User user = new User();
            user.setMobile(account);  // TODO 需要判断账号是邮箱还是手机号
            userService.save(user);
        }

        // 调用自定义的userDetailsService认证
        UserDetails user = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
        if (user == null) {
            // TODO 如果用户不存在，则注册用户
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        // 如果user不为空重新构建SmsCodeAuthenticationToken（已认证）
        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    /**
     * 只有Authentication为SmsCodeAuthenticationToken使用此Provider认证
     *
     * @param authentication 认证信息
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
