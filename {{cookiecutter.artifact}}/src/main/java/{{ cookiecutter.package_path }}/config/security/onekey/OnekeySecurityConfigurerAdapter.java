package {{ cookiecutter.basePackage }}.config.security.onekey;

import {{ cookiecutter.basePackage }}.biz.auth.login.OneKeyLoginService;
import {{ cookiecutter.basePackage }}.biz.auth.user.UserService;
import {{ cookiecutter.basePackage }}.biz.auth.user.role.UserRoleService;
import {{ cookiecutter.basePackage }}.biz.sys.captcha.ValidateService;
import {{ cookiecutter.basePackage }}.biz.sys.log.LoginLogService;
import io.github.zxyle.map.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 本机手机号授权登录认证配置
 */
@Component
@RequiredArgsConstructor
public class OnekeySecurityConfigurerAdapter extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    final UserDetailsService userDetailsService;
    final ValidateService validateService;
    final LoginLogService loginLogService;
    final OneKeyLoginService oneKeyLoginService;
    final UserService userService;
    final MapService mapService;
    final UserRoleService userRoleService;

    @Override
    public void configure(HttpSecurity http) {
        // 自定义WechatAuthenticationFilter过滤器
        OnekeyAuthenticationFilter wechatAuthenticationFilter = new OnekeyAuthenticationFilter(oneKeyLoginService);
        wechatAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        wechatAuthenticationFilter.setAuthenticationSuccessHandler(new OnekeyAuthenticationSuccessHandler(loginLogService, userService, mapService));
        wechatAuthenticationFilter.setAuthenticationFailureHandler(new OnekeyAuthenticationFailureHandler(loginLogService));

        // 设置自定义WechatAuthenticationProvider的认证器userDetailsService
        OnekeyAuthenticationProvider wechatAuthenticationProvider =
                new OnekeyAuthenticationProvider(userDetailsService, validateService, userService, userRoleService);

        // 在UsernamePasswordAuthenticationFilter过滤前执行
        http.authenticationProvider(wechatAuthenticationProvider)
                .addFilterAfter(wechatAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

