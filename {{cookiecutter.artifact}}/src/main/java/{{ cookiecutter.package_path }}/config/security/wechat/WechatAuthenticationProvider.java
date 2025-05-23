package {{ cookiecutter.basePackage }}.config.security.wechat;

import {{ cookiecutter.basePackage }}.biz.auth.user.User;
import {{ cookiecutter.basePackage }}.biz.auth.user.UserService;
import {{ cookiecutter.basePackage }}.biz.sys.captcha.ValidateService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 微信小程序手机号授权登录认证Provider
 */
public class WechatAuthenticationProvider implements AuthenticationProvider {

    UserDetailsService userDetailsService;
    ValidateService validateService;
    UserService userService;

    public WechatAuthenticationProvider(UserDetailsService userDetailsService, ValidateService validateService, UserService userService) {
        this.userDetailsService = userDetailsService;
        this.validateService = validateService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WechatAuthenticationToken authenticationToken = (WechatAuthenticationToken) authentication;

        String phone = (String) authenticationToken.getPrincipal();
        String openid = (String) authenticationToken.getCredentials();

        // 检查当前手机号是否已注册，没有则创建用户
        User account1 = userService.findByAccount(phone);
        if (account1 != null) {
            if (Boolean.FALSE.equals(account1.getEnabled())) {
                throw new DisabledException("账号已被禁用");
            }

            if (Boolean.TRUE.equals(account1.getLocked())) {
                throw new LockedException("账号已被锁定");
            }
        }

        if (account1 == null) {
            User user = new User();
            user.setMobile(phone);
            user.setOpenid(openid);
            user.setNickname("微信用户");
            userService.save(user);
        } else {
            // TODO 存在着openid不一致的情况
            if (!account1.getOpenid().equals(openid)) {
                throw new BadCredentialsException("openid不一致");
            }
        }

        // 调用自定义的userDetailsService认证
        UserDetails userDetails = userDetailsService.loadUserByUsername(phone);
        if (userDetails == null) {
            // TODO 如果用户不存在，则注册用户
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        // 如果user不为空重新构建WechatAuthenticationToken（已认证）
        WechatAuthenticationToken authenticationResult = new WechatAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    /**
     * 只有Authentication为WechatAuthenticationToken使用此Provider认证
     *
     * @param authentication 认证信息
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return WechatAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
