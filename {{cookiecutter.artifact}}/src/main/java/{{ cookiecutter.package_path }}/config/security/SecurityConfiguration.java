package {{ cookiecutter.basePackage }}.config.security;
{% if cookiecutter.bootVersion.split('.')[0] == '2' -%}
import {{ cookiecutter.basePackage }}.common.aspect.ApiPolicy;
import {{ cookiecutter.basePackage }}.config.CorsProperties;
import {{ cookiecutter.basePackage }}.config.security.filter.AntiSpiderFilter;
import {{ cookiecutter.basePackage }}.config.security.filter.AclFilter;
import {{ cookiecutter.basePackage }}.config.security.filter.JwtAuthenticationTokenFilter;
import {{ cookiecutter.basePackage }}.config.security.mobile.SmsSecurityConfigurerAdapter;
import {{ cookiecutter.basePackage }}.config.security.wechat.WechatSecurityConfigurerAdapter;
import {{ cookiecutter.basePackage }}.config.security.onekey.OnekeySecurityConfigurerAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * Spring Security配置
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
// 开启方法级别的权限控制, securedEnabled = true 开启 @Secured 注解过滤权限, prePostEnabled = true 开启 @PreAuthorize 注解过滤权限
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration {

    final AntiSpiderFilter antiSpiderFilter;
    final AclFilter aclFilter;
    final JwtAuthenticationTokenFilter jwtFilter;
    final AuthenticationEntryPoint authenticationEntryPoint;
    final AccessDeniedHandler accessDeniedHandler;
    final SmsSecurityConfigurerAdapter smsSecurityConfigurerAdapter;
    final CorsProperties corsProperties;
    final WechatSecurityConfigurerAdapter wechatSecurityConfigurerAdapter;
    final OnekeySecurityConfigurerAdapter onekeySecurityConfigurerAdapter;

    // 使用 @Qualifier 指定 Bean 名称
    @Autowired
    @Qualifier("requestMappingHandlerMapping")  // 明确指定默认的 Bean
    private RequestMappingHandlerMapping mapping;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 获取所有免认证路径
        String[] anonymousUrls = getAnonymousUrls();

        // 关闭csrf
        http.csrf().disable()
                .cors().configurationSource(corsConfigSource()).and()
                // 不通过session获取securityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/auth/login/**").anonymous()  // 仅允许匿名用户（未认证用户）访问
                .antMatchers(anonymousUrls).permitAll() // 允许所有用户访问，无论是否认证。

                // 除上述请求 全部需要鉴权认证
                .anyRequest().authenticated()
                .and().apply(smsSecurityConfigurerAdapter)
                .and().apply(onekeySecurityConfigurerAdapter)
                .and().apply(wechatSecurityConfigurerAdapter);


        // 添加过滤器
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(aclFilter, JwtAuthenticationTokenFilter.class);
        http.addFilterBefore(antiSpiderFilter, AclFilter.class);

        // 配置异常处理器
        http.exceptionHandling()
                // 配置认证失败处理器
                .authenticationEntryPoint(authenticationEntryPoint)
                // 配置授权失败处理器
                .accessDeniedHandler(accessDeniedHandler);

        // 允许跨域
        http.cors();
        return http.build();
    }

    /**
     * Spring Security 配置跨域
     */
    private CorsConfigurationSource corsConfigSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(corsProperties.getAllowedHeaders());
        configuration.setAllowedOrigins(corsProperties.getAllowedOrigins());
        configuration.setAllowedMethods(corsProperties.getAllowedMethods());
        configuration.setMaxAge(corsProperties.getMaxAge());
        configuration.setExposedHeaders(configuration.getExposedHeaders());
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    private String[] getAnonymousUrls() {
        Set<String> anonymousUrls = new HashSet<>();
        mapping.getHandlerMethods().forEach((info, handlerMethod) -> {
            // 检查类或方法是否有 @ApiBehavior 注解
            boolean isClassAnnotated = handlerMethod.getBeanType().isAnnotationPresent(ApiPolicy.class) &&
                    handlerMethod.getBeanType().getAnnotation(ApiPolicy.class).anon();
            boolean isMethodAnnotated = handlerMethod.hasMethodAnnotation(ApiPolicy.class)
                    && Objects.requireNonNull(handlerMethod.getMethodAnnotation(ApiPolicy.class)).anon();

            // 单独处理方法
            if (!isClassAnnotated && isMethodAnnotated) {
                // 获取接口路径（支持 Ant 风格）
                Set<String> patterns = info.getPatternValues();
                anonymousUrls.addAll(patterns);
            }

            if (isClassAnnotated) {
                String[] classPaths = getClassLevelPaths(handlerMethod.getBeanType());
                for (String classPath : classPaths) {
                    anonymousUrls.add(classPath + "/**");
                }
            }
        });

        return anonymousUrls.toArray(new String[0]);
    }

    /**
     * 获取类级别的路径前缀
     */
    private String[] getClassLevelPaths(Class<?> clazz) {
        RequestMapping classMapping = clazz.getAnnotation(RequestMapping.class);
        if (classMapping != null) {
            return classMapping.value();
        }
        return new String[]{""};  // 默认空路径
    }
}
{% else %}
import {{ cookiecutter.basePackage }}.config.CorsProperties;
import {{ cookiecutter.basePackage }}.config.security.filter.AclFilter;
import {{ cookiecutter.basePackage }}.config.security.filter.AntiSpiderFilter;
import {{ cookiecutter.basePackage }}.config.security.filter.JwtAuthenticationTokenFilter;
import {{ cookiecutter.basePackage }}.config.security.mobile.SmsSecurityConfigurerAdapter;
import {{ cookiecutter.basePackage }}.config.security.wechat.WechatSecurityConfigurerAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring Security配置
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
// 开启方法级别的权限控制, securedEnabled = true 开启 @Secured 注解过滤权限,
// prePostEnabled = true 开启 @PreAuthorize 注解过滤权限
public class SecurityConfiguration {

    final AntiSpiderFilter antiSpiderFilter;
    final AclFilter aclFilter;
    final JwtAuthenticationTokenFilter jwtFilter;
    final AuthenticationEntryPoint authenticationEntryPoint;
    final AccessDeniedHandler accessDeniedHandler;
    final SmsSecurityConfigurerAdapter smsSecurityConfigurerAdapter;
    final UserDetailsService userDetailsService;
    final CorsProperties corsProperties;
    final WechatSecurityConfigurerAdapter wechatSecurityConfigurerAdapter;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigSource()))
                .csrf(AbstractHttpConfigurer::disable)
                // 对于登录接口 允许匿名访问
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/auth/login/**").anonymous()
                        .requestMatchers("/sys/dicts/**", "/sys/area/**", "/sys/file/**", "/site/infos", "/auth/user/register",
                                "/auth/password/**", "/sys/captcha/**", "/status", "/ping", "/ua", "/headers", "/ip", "/now",
                                "/getPublicKey").permitAll()// 除上述请求 全部需要鉴权认证
                        // 访问actuator信息 需要添加"/actuator/**"
                        .anyRequest().authenticated())
                // 不通过session获取securityContext
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                // 添加过滤器
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                // 配置异常处理器
                .exceptionHandling(exceptions -> exceptions
                        // 配置认证失败处理器
                        .authenticationEntryPoint(authenticationEntryPoint)
                        // 配置授权失败处理器
                        .accessDeniedHandler(accessDeniedHandler))
                 .with(smsSecurityConfigurerAdapter, customizer -> {})
                 .with(wechatSecurityConfigurerAdapter, customizer -> {});

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Spring Security 配置跨域
     */
    private CorsConfigurationSource corsConfigSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(corsProperties.getAllowedHeaders());
        configuration.setAllowedOrigins(corsProperties.getAllowedOrigins());
        configuration.setAllowedMethods(corsProperties.getAllowedMethods());
        configuration.setMaxAge(corsProperties.getMaxAge());
        configuration.setExposedHeaders(configuration.getExposedHeaders());
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
{% endif %}
