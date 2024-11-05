package {{ cookiecutter.basePackage }}.config.interceptor;

import cn.hutool.core.util.IdUtil;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import {{ cookiecutter.namespace }}.servlet.http.HttpServletRequest;
import {{ cookiecutter.namespace }}.servlet.http.HttpServletResponse;

/**
 * 请求追踪拦截器
 */
public class TraceInterceptor implements HandlerInterceptor {

    public static final String TRACE_ID = "traceId";

    /**
     * 给每个请求生成一个唯一的traceId
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        MDC.put(TRACE_ID, IdUtil.fastSimpleUUID().substring(0, 8));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.remove(TRACE_ID);
    }
}
