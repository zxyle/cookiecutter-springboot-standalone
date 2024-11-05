package {{ cookiecutter.basePackage }}.config.security.filter;

import {{ cookiecutter.basePackage }}.biz.sys.acl.Acl;
import {{ cookiecutter.basePackage }}.biz.sys.acl.AclService;
import {{ cookiecutter.basePackage }}.biz.sys.setting.SettingService;
import {{ cookiecutter.basePackage }}.common.util.CidrUtil;
import {{ cookiecutter.basePackage }}.common.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import lombok.RequiredArgsConstructor;

import {{ cookiecutter.namespace }}.servlet.FilterChain;
import {{ cookiecutter.namespace }}.servlet.ServletException;
import {{ cookiecutter.namespace }}.servlet.http.HttpServletRequest;
import {{ cookiecutter.namespace }}.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * IP黑白名单过滤器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AclFilter extends OncePerRequestFilter {

    final AclService aclService;
    final Environment environment;
    final SettingService setting;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (isDev()|| !setting.get("acl.enable").isReal()) {
            filterChain.doFilter(request, response);
            return;
        }

        String ip = request.getRemoteAddr();
        List<Acl> acl = aclService.findAllIp();

        // 过滤掉过期的黑白名单
        List<String> whitelist = acl.stream().filter(
                b -> b.getEndTime() == null || LocalDateTime.now().isBefore(b.getEndTime())
        ).filter(e-> e.getAllowed().equals(true)).map(Acl::getIp).{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};

        List<String> blacklist = acl.stream().filter(
                b -> b.getEndTime() == null || LocalDateTime.now().isBefore(b.getEndTime())
        ).filter(e-> e.getAllowed().equals(false)).map(Acl::getIp).{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};

        if ((CidrUtil.in(ip, whitelist)) || (CidrUtil.notIn(ip, blacklist))) {
            log.error("不允许的ip访问: {}", ip);
            ResponseUtil.forbidden(response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean isDev() {
        String[] profiles = environment.getActiveProfiles();
        List<String> list = Arrays.asList(profiles);
        return list.contains("dev");
    }
}
