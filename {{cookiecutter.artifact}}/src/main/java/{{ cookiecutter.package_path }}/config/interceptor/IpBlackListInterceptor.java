package {{ cookiecutter.basePackage }}.config.interceptor;

import {{ cookiecutter.basePackage }}.biz.sys.service.IIpBlacklistService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

@Service
public class IpBlackListInterceptor implements HandlerInterceptor {

    IIpBlacklistService blacklistService;

    public IpBlackListInterceptor(IIpBlacklistService blacklistService) {
        this.blacklistService = blacklistService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = request.getRemoteAddr();
        List<String> blacklist = blacklistService.getBlacklist();
        if (blacklist.contains(ip)) {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.println("{\"message\": \"禁止访问\", \"success\": false, \"code\": \"403\"}");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }
        return true;
    }
}
