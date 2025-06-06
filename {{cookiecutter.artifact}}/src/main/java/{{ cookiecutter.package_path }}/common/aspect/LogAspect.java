package {{ cookiecutter.basePackage }}.common.aspect;

import {{ cookiecutter.basePackage }}.biz.auth.login.AccountLoginRequest;
import {{ cookiecutter.basePackage }}.biz.auth.login.LoginResponse;
import {{ cookiecutter.basePackage }}.biz.sys.log.LoginLog;
import {{ cookiecutter.basePackage }}.biz.sys.log.LoginLogService;
import {{ cookiecutter.basePackage }}.common.response.R;
import {{ cookiecutter.basePackage }}.common.util.IpUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import {{ cookiecutter.namespace }}.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 登录日志切面
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    final LoginLogService loginLogService;

    // 定义切入点表达式
    @Pointcut("execution(* {{ cookiecutter.basePackage }}.biz.auth.login.LoginController.login(..))")
    public void log() {
    }

    @Before("log()")    // 引用切入点
    public void doBefore(JoinPoint joinPoint) {
        log.info("进入切面");

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String ip = request.getRemoteAddr();
        // 获得类名.方法名
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        // 获得方法参数
        Object[] args = joinPoint.getArgs();

        RequestLog requestLog = new RequestLog(method, url, ip, classMethod, args);
        // 打印请求信息
        log.info("Request: {}", requestLog);
    }

    @After("log()")
    public void doAfter() {
        log.info("------------doAfter------------");
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result) {
        // 打印返回值
        log.info("AfterReturning Result: {}", result);
    }


    /**
     * Around注解 环绕执行，就是在调用目标方法之前和调用之后，都会执行一定的逻辑
     */
    @Around("log()")
    public R<LoginResponse> doAround(ProceedingJoinPoint pjp) throws Throwable {
        LoginLog loginLog = new LoginLog();

        // log.info("around start");
        Object[] values = pjp.getArgs();
        String[] names = ((CodeSignature) pjp.getSignature()).getParameterNames();
        Map<String, Object> map = new HashMap<>(names.length);
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], values[i]);
        }
        // log.info("params map: {}", map);
        HttpServletRequest servletRequest = (HttpServletRequest) map.get("servletRequest");
        AccountLoginRequest request = (AccountLoginRequest) map.get("req");
        loginLog.setUa(servletRequest.getHeader(HttpHeaders.USER_AGENT));
        loginLog.setIp(IpUtil.getIpAddr(servletRequest));
        loginLog.setAccount(request.getAccount());
        long start = System.currentTimeMillis();
        // 调用执行目标方法(result为目标方法执行结果)，必须有此行代码才会执行目标调用的方法（等价于@befor+@after），否则只会执行一次之前的（等价于@before）
        @SuppressWarnings("unchecked")
        R<LoginResponse> result = (R<LoginResponse>) pjp.proceed();
        // log.info("Around Result: {}", result);
        long end = System.currentTimeMillis();
        loginLog.setMsg(result.getMessage());
        loginLog.setSuccess(result.isSuccess());
        Optional.ofNullable(result.getData()).ifPresent(data -> loginLog.setUserId(data.getUserId()));

        log.debug("{} -> {}, 耗费时间: {}毫秒.", pjp.getTarget().getClass().getSimpleName(), pjp.getSignature().getName(), (end - start));
        log.info("登录耗费时间: {}毫秒", (end - start));
        log.info("log: {}", loginLog);
        loginLogService.saveLogAsync(loginLog);
        return result;
    }

    @AfterThrowing(throwing = "e", pointcut = "log()")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("------------doAfterThrowing------------");
        log.error("Exception: {}", e.getMessage());
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        log.error("request: {}", request);

        LoginLog loginLog = new LoginLog();
        Object[] values = joinPoint.getArgs();
        String[] names = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Map<String, Object> map = new HashMap<>(names.length);
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], values[i]);
        }
        HttpServletRequest servletRequest = (HttpServletRequest) map.get("servletRequest");
        AccountLoginRequest accountRequest = (AccountLoginRequest) map.get("req");
        loginLog.setUa(servletRequest.getHeader(HttpHeaders.USER_AGENT));
        loginLog.setIp(IpUtil.getIpAddr(servletRequest));
        loginLog.setAccount(accountRequest.getAccount());
        loginLog.setSuccess(false);
        loginLog.setMsg(e.getMessage());
        loginLog.setUserId(null);  // FIXME 如何记录登录失败的用户ID？

        log.info("登录失败日志: {}", loginLog);
        loginLogService.saveLogAsync(loginLog);
    }

    @Data
    @AllArgsConstructor
    public static class RequestLog {      // 用于封装请求信息
        private String method;
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;
    }
}

