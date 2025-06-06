package {{ cookiecutter.basePackage }}.config.interceptor;

import {{ cookiecutter.basePackage }}.config.CorsProperties;
import {{ cookiecutter.namespace }}.servlet.http.HttpServletRequest;
import {{ cookiecutter.namespace }}.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 跨域拦截器
 */
@Component
@RequiredArgsConstructor
public class CorsInterceptor implements HandlerInterceptor {

    final CorsProperties coreProperties;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, String.join(",", coreProperties.getAllowedOrigins()));
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, String.join(",", coreProperties.getAllowedMethods()));
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, String.valueOf(coreProperties.getMaxAge()));
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, String.join(",", coreProperties.getAllowedHeaders()));
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, String.valueOf(coreProperties.getAllowCredentials()));
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,  String.join(",", coreProperties.getExposedHeaders()));
        return true;
    }
}

