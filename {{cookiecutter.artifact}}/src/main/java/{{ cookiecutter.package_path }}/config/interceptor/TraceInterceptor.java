package {{ cookiecutter.basePackage }}.config.interceptor;

import cn.hutool.core.util.IdUtil;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TraceInterceptor implements HandlerInterceptor {

    /**
     * 给每个请求生成一个唯一的traceId
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 服务器 IP + ID 产生的时间 + 自增序列 + 当前进程号
        // String ip = IpUtil.getIpAddr(request);
        // String traceId = ip + "-" + System.currentTimeMillis() + "-" + Thread.currentThread().getId();
        MDC.put("traceId", IdUtil.fastSimpleUUID());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove("traceId");
    }
}
