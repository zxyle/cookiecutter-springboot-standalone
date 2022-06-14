// package {{ cookiecutter.basePackage }}.biz.auth.security;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.AuthenticationEntryPoint;
// import org.springframework.security.web.access.AccessDeniedHandler;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
// @Configuration
// @EnableGlobalMethodSecurity(prePostEnabled = true)
// public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//     @Autowired
//     private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
//
//     @Autowired
//     private AuthenticationEntryPoint authenticationEntryPoint;
//
//     @Autowired
//     private AccessDeniedHandler accessDeniedHandler;
//
//     @Bean
//     public BCryptPasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
//
//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         // 关闭csrf
//         http.csrf().disable()
//                 // 不通过session获取securityContext
//                 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                 .and()
//                 .authorizeRequests()
//                 // 对于登录接口 允许匿名访问
//                 .antMatchers("/user/login").anonymous()
//                 // 除上述请求 全部需要鉴权认证
//                 .anyRequest().authenticated();
//
//         // 添加过滤器
//         http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
//
//         // 配置异常处理器
//         http.exceptionHandling()
//                 // 配置认证失败处理器
//                 .authenticationEntryPoint(authenticationEntryPoint)
//                 // 配置授权失败处理器
//                 .accessDeniedHandler(accessDeniedHandler);
//
//         // 允许跨域
//         http.cors();
//
//     }
//
//     @Bean
//     @Override
//     public AuthenticationManager authenticationManagerBean() throws Exception {
//         return super.authenticationManagerBean();
//     }
//
// }
