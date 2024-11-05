package {{ cookiecutter.basePackage }}.config.security.wechat;

import {{ cookiecutter.basePackage }}.biz.auth.user.UserService;
import {{ cookiecutter.basePackage }}.biz.site.miniprogram.WeChatMiniProgramService;
import {{ cookiecutter.basePackage }}.biz.sys.captcha.ValidateService;
import {{ cookiecutter.basePackage }}.biz.sys.log.LoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 微信小程序手机号授权登录认证配置
 */
@Component
@RequiredArgsConstructor
public class WechatSecurityConfigurerAdapter extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    final UserDetailsService userDetailsService;
    final ValidateService validateService;
    final LoginLogService loginLogService;
    final WeChatMiniProgramService wechatMiniProgramService;
    final UserService userService;

    @Override
    public void configure(HttpSecurity http) {
        // 自定义WechatAuthenticationFilter过滤器
        WechatAuthenticationFilter wechatAuthenticationFilter = new WechatAuthenticationFilter(wechatMiniProgramService);
        wechatAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        wechatAuthenticationFilter.setAuthenticationSuccessHandler(new WechatAuthenticationSuccessHandler(loginLogService, userService));
        wechatAuthenticationFilter.setAuthenticationFailureHandler(new WechatAuthenticationFailureHandler(loginLogService));

        // 设置自定义WechatAuthenticationProvider的认证器userDetailsService
        WechatAuthenticationProvider wechatAuthenticationProvider =
                new WechatAuthenticationProvider(userDetailsService, validateService, userService);

        // 在UsernamePasswordAuthenticationFilter过滤前执行
        http.authenticationProvider(wechatAuthenticationProvider)
                .addFilterAfter(wechatAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

