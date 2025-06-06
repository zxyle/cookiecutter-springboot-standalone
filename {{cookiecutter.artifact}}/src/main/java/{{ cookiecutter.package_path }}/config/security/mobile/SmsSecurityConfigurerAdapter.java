package {{ cookiecutter.basePackage }}.config.security.mobile;

import {{ cookiecutter.basePackage }}.biz.auth.user.UserService;
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
 * 验证码登录认证配置
 */
@Component
@RequiredArgsConstructor
public class SmsSecurityConfigurerAdapter extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    final UserDetailsService userDetailsService;
    final ValidateService validateService;
    final LoginLogService loginLogService;
    final UserService userService;

    @Override
    public void configure(HttpSecurity http) {
        // 自定义SmsCodeAuthenticationFilter过滤器
        SmsCodeAuthenticationFilter smsAuthenticationFilter = new SmsCodeAuthenticationFilter();
        smsAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        smsAuthenticationFilter.setAuthenticationSuccessHandler(new SmsCodeAuthenticationSuccessHandler(loginLogService, userService));
        smsAuthenticationFilter.setAuthenticationFailureHandler(new SmsCodeAuthenticationFailureHandler(loginLogService));

        // 设置自定义SmsCodeAuthenticationProvider的认证器userDetailsService
        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider =
                new SmsCodeAuthenticationProvider(userDetailsService, validateService, userService);

        // 在UsernamePasswordAuthenticationFilter过滤前执行
        http.authenticationProvider(smsCodeAuthenticationProvider)
                .addFilterAfter(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

