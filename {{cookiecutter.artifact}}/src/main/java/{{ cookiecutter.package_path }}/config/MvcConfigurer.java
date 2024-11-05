package {{ cookiecutter.basePackage }}.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class MvcConfigurer implements WebMvcConfigurer {

    final CorsProperties corsProperties;

    /**
     * 跨域访问 配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(corsProperties.getAllowedOrigins().toArray(new String[0]))
                .allowedMethods(corsProperties.getAllowedMethods().toArray(new String[0]))
                .allowedHeaders(corsProperties.getAllowedHeaders().toArray(new String[0]))
                .exposedHeaders(corsProperties.getExposedHeaders().toArray(new String[0]))
                .allowCredentials(corsProperties.getAllowCredentials())
                .maxAge(corsProperties.getMaxAge());
    }

    // 格式化
    // TODO 补充对应示例
    @Override
    public void addFormatters(FormatterRegistry registry) {

    }

    // URI 到视图的映射
    // TODO 补充对应示例
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

    }

    // 其他更多全局定制接口
    // TODO 补充对应示例


    /**
     * 配置静态资源
     * addResourceHandler   对外暴露的访问路径
     * addResourceLocations 静态资源存放的位置
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");

    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

    }
}