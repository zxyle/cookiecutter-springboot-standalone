package {{ cookiecutter.basePackage }}.config.interceptor;

import {{ cookiecutter.basePackage }}.common.constant.ProjectConst;
import {{ cookiecutter.basePackage }}.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 允许跨域拦截器
 */
@Component
public class AllowCrossDomainInterceptor implements HandlerInterceptor {

    private final Environment env;

    @Autowired
    public AllowCrossDomainInterceptor(Environment env) {
        this.env = env;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        // 仅在非正式环境下生效
        if (!Arrays.asList(env.getActiveProfiles()).contains(ProjectConst.PROD)) {
            String origin = httpServletRequest.getHeader("Origin");
            httpServletResponse.setHeader("Access-Control-Allow-Origin", StringUtil.isEmpty(origin) ? "*" : origin);
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, PATCH");
            httpServletResponse.setHeader("Access-Control-Max-Age", "0");
            httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpServletResponse.setHeader("XDomainRequestAllowed", "1");
        }
        return true;
    }
}
