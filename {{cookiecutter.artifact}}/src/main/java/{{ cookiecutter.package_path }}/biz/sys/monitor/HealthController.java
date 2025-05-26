package {{ cookiecutter.basePackage }}.biz.sys.monitor;

import {{ cookiecutter.basePackage }}.common.aspect.ApiPolicy;
import {{ cookiecutter.basePackage }}.common.constant.DateFormatConst;
import {{ cookiecutter.basePackage }}.common.util.IpUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import {{ cookiecutter.namespace }}.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统健康状态
 */
@RestController
@ApiPolicy(anon = true)
public class HealthController {

    /**
     * 系统状态
     */
    @GetMapping("/status")
    public String status() {
        return "It worked!";
    }

    /**
     * 回声测试
     */
    @GetMapping("/ping")
    public Map<String, String> ping() {
        Map<String, String> map = new HashMap<>(1);
        map.put("ping", "pong");
        return map;
    }

    /**
     * 获取请求headers
     */
    @GetMapping("/headers")
    public Map<String, String> headers(HttpServletRequest servletRequest) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = servletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            headers.put(key, servletRequest.getHeader(key));
        }
        return headers;
    }

    /**
     * 获取浏览器请求头
     */
    @GetMapping("/ua")
    public String ua(HttpServletRequest servletRequest) {
        return servletRequest.getHeader(HttpHeaders.USER_AGENT);
    }

    /**
     * 获取请求IP
     */
    @GetMapping("/ip")
    public String ip(HttpServletRequest servletRequest) {
        return IpUtil.getIpAddr(servletRequest);
    }

    /**
     * 获取服务器时间
     */
    @GetMapping("/now")
    public String now() {
        return LocalDateTime.now().format(DateFormatConst.YYYY_MM_DD_HH_MM_SS_DTF);
    }

}
