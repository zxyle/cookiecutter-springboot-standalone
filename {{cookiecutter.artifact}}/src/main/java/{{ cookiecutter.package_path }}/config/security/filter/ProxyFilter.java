package {{ cookiecutter.basePackage }}.config.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import {{ cookiecutter.namespace }}.servlet.FilterChain;
import {{ cookiecutter.namespace }}.servlet.ServletException;
import {{ cookiecutter.namespace }}.servlet.http.HttpServletRequest;
import {{ cookiecutter.namespace }}.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 识别代理请求
 */
@Slf4j
@Component
public class ProxyFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 识别请求使用了IP代理
        Map<String, String> map = new HashMap<>(19);
        map.put("Proxy-Client-IP", request.getHeader("Proxy-Client-IP"));
        map.put("WL-Proxy-Client-IP", request.getHeader("WL-Proxy-Client-IP"));
        map.put("HTTP_CLIENT_IP", request.getHeader("HTTP_CLIENT_IP"));
        map.put("HTTP_X_FORWARDED_FOR", request.getHeader("HTTP_X_FORWARDED_FOR"));
        map.put("X-Forwarded-For", request.getHeader("X-Forwarded-For"));
        map.put("X-Real-IP", request.getHeader("X-Real-IP"));
        map.put("RemoteAddr", request.getRemoteAddr());
        map.put("RemoteHost", request.getRemoteHost());
        map.put("RemotePort", String.valueOf(request.getRemotePort()));
        map.put("LocalAddr", request.getLocalAddr());
        map.put("LocalName", request.getLocalName());
        map.put("LocalPort", String.valueOf(request.getLocalPort()));
        map.put("ServerName", request.getServerName());
        map.put("ServerPort", String.valueOf(request.getServerPort()));
        map.put("Scheme", request.getScheme());
        map.put("Protocol", request.getProtocol());
        map.put("ContextPath", request.getContextPath());
        map.put("ServletPath", request.getServletPath());
        log.info("ProxyFilter: {}", map);
        filterChain.doFilter(request, response);
    }
}
