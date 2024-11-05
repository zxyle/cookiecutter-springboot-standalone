package {{ cookiecutter.basePackage }}.config.security.filter;

import {{ cookiecutter.basePackage }}.common.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import lombok.RequiredArgsConstructor;

import {{ cookiecutter.namespace }}.servlet.FilterChain;
import {{ cookiecutter.namespace }}.servlet.ServletException;
import {{ cookiecutter.namespace }}.servlet.http.HttpServletRequest;
import {{ cookiecutter.namespace }}.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 爬虫过滤器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AntiSpiderFilter extends OncePerRequestFilter {

    final Environment environment;

    /**
     * 爬虫关键字 包括搜索引擎爬虫和常见的爬虫框架
     */
    private static final List<String> KEYWORDS = Arrays.asList(
            // HTTP请求库
            "curl",        // curl/7.55.1
            "wget",        // Wget/1.14 (linux-gnu)
            "requests",    // python-requests/2.24.0
            "aiohttp",     // Python/3.7 aiohttp/3.7.2
            "urllib",      // Python-urllib/3.7
            "httpx",       // python-httpx/0.16.1
            "twisted",     // Twisted PageGetter
            "tornado",     // Tornado/6.1
            "httpclient",  // Apache-HttpClient/4.5.12 (Java/1.8.0_202)
            "headless",    // Headless Browser such as PhantomJS puppeteer
            "okhttp",      // okhttp/3.12.1

            // 爬虫框架
            "scrapy",      // Scrapy/2.0.0 (+https://scrapy.org)
            "pyspider",

            // 压力测试工具
            "jmeter",      // ApacheJMeter/5.2.1
            "gatling",
            "locust",
            "locust",
            "vegeta",

            // 搜索引擎爬虫
            "baiduspider",
            "bingbot",
            "googlebot",
            "sogou",
            "360spider",
            "sosospider",
            "yisouspider",
            "youdaobot",
            "spider",
            "crawler"
    );


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (isDev()) {
            filterChain.doFilter(request, response);
            return;
        }

        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        if (StringUtils.isBlank(userAgent) || KEYWORDS.stream().anyMatch(userAgent::contains)) {
            ResponseUtil.forbidden(response);
            log.info("拦截爬虫请求: {}", userAgent);
            // 是否将爬虫的IP记录到数据库中
            return;
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 判断当前环境是否为开发环境
     */
    public boolean isDev() {
        String[] profiles = environment.getActiveProfiles();
        List<String> list = Arrays.asList(profiles);
        return list.contains("dev");
    }
}
