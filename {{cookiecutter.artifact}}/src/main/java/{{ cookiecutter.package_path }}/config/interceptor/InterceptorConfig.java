package {{ cookiecutter.basePackage }}.config.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    /**
     * 拦截器
     * addPathPatterns     用于添加拦截规则
     * excludePathPatterns 用户排除拦截
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceInterceptor()).addPathPatterns("/**");
        /*registry.addInterceptor(new LoginInterceptor())
                    .addPathPatterns("/admin/**")
                    .excludePathPatterns("/admin")
                    .excludePathPatterns("/admin/login");**/
    }
}

