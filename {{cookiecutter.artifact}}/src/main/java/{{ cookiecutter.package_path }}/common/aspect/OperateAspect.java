package {{ cookiecutter.basePackage }}.common.aspect;

import {{ cookiecutter.basePackage }}.biz.auth.token.JwtUtil;
import {{ cookiecutter.basePackage }}.biz.sys.log.OperateLog;
import {{ cookiecutter.basePackage }}.biz.sys.log.OperateLogService;
import {{ cookiecutter.basePackage }}.common.constant.AuthConst;
import {{ cookiecutter.basePackage }}.common.constant.ProjectConst;
import {{ cookiecutter.basePackage }}.common.exception.GlobalExceptionHandler;
import {{ cookiecutter.basePackage }}.common.response.R;
import {{ cookiecutter.basePackage }}.common.util.IpUtil;
import {{ cookiecutter.basePackage }}.config.interceptor.TraceInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import {{ cookiecutter.namespace }}.servlet.http.HttpServletRequest;
import {{ cookiecutter.namespace }}.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * 操作日志记录切面
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OperateAspect {

    final OperateLogService logService;
    final ObjectMapper objectMapper;
    final StringRedisTemplate stringRedisTemplate;

    // 定义切入点表达式（匹配Controller层）
    @Pointcut("@within(org.springframework.stereotype.Controller) || " +
            "@within(org.springframework.web.bind.annotation.RestController)")
    public void logPointcut() {
    }

    /**
     * 环绕通知处理操作日志
     */
    @Around("logPointcut()")
    public Object logOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        // 1. 判断是否需要忽略日志记录
        List<Boolean> list = isIgnoreLog(joinPoint);
        boolean ignoreRequest = list.get(0);
        boolean ignoreResponse = list.get(1);

        // 2. 准备基础请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        OperateLog op = new OperateLog();
        // 使用StringBuilder预先构建日志内容，减少日志API调用次数
        StringBuilder logBuilder = new StringBuilder(512)
                // 删除这条横线， 业务日志 和请求日志就在显示在一起了
                .append("\n========================================== Start ==========================================\n")
                .append("Request Url    : ").append(request.getMethod()).append(" ").append(request.getRequestURL()).append("\n");

        List<Object> requestArgs = filterRequestArgs(joinPoint);
        if (!requestArgs.isEmpty()) {
            String argsJson = objectMapper.writeValueAsString(requestArgs);
            if (!ignoreRequest) {
                logBuilder.append("Request Args   : ").append(argsJson).append("\n");
            }
            op.setRequest(StringUtils.substring(argsJson, 0, 2048));
        }

        // 获取请求接口的用户ID
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isNotBlank(token)) {
            logBuilder.append("Token          : ").append(token).append("\n");

            if (token.startsWith(AuthConst.AUTH_SCHEME)) {
                Claims claims = JwtUtil.parseJwt(token.substring(AuthConst.AUTH_SCHEME.length()));
                if (claims != null) {
                    String userId = claims.getSubject();
                    logBuilder.append("UserId         : ").append(userId).append("\n");
                    op.setUserId(Integer.valueOf(userId));
                    // 设置用户在线时长
                    stringRedisTemplate.opsForValue().set("online:" + userId, "1", 5, TimeUnit.MINUTES);
                }
            }
        }

        String ip = IpUtil.getIpAddr(request);
        logBuilder.append("User Agent     : ").append(request.getHeader(HttpHeaders.USER_AGENT)).append("\n")
                .append("Content Type   : ").append(request.getContentType()).append("\n")
                .append("Class Method   : ").append(joinPoint.getSignature().getDeclaringTypeName()).append(".").append(joinPoint.getSignature().getName()).append("\n")
                .append("IP             : ").append(ip)
                // 以下代码是用来展示IP归属地
                // .append(" （").append(IpUtil.getRegion(ip)).append("）")
                .append("\n")
                .append("TraceId        : ").append(MDC.get(TraceInterceptor.TRACE_ID)).append("\n");

        // 获取请求IP、路径和方法
        op.setIp(ip);
        op.setPath(request.getRequestURI());
        op.setMethod(request.getMethod());

        // 获取@LogOperation注解上的操作名称和业务名称
        String operationName = "";
        String biz = "";

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        LogOperation logOperation = method.getAnnotation(LogOperation.class);
        if (logOperation != null) {
            operationName = logOperation.name();
            biz = logOperation.biz();
        } else {
            // 如果没有注解，尝试从Controller和方法名生成操作名称
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = method.getName();
            operationName = className + "." + methodName;

            // 尝试从Controller上的RequestMapping获取业务模块
            RequestMapping requestMapping = joinPoint.getTarget().getClass().getAnnotation(RequestMapping.class);
            if (requestMapping != null && requestMapping.value().length > 0) {
                biz = requestMapping.value()[0];
            }
        }

        // 记录日志信息
        op.setBiz(biz);
        op.setOperationName(operationName);
        op.setOperateTime(LocalDateTime.now());

        // 调用目标方法
        long startTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            // 记录操作结果
            if (!(result instanceof R<?>)) {
                return result;
            }

            @SuppressWarnings("unchecked")
            R<Object> response = (R<Object>) result;
            if (!ignoreResponse) {
                logBuilder.append("Response       : ").append(objectMapper.writeValueAsString(response)).append("\n");
            }
            op.setSuccess(response.isSuccess());
            op.setTraceId(response.getTraceId());
            // 如果操作失败，记录失败原因
            if (!response.isSuccess()) {
                op.setResponse(StringUtils.substring(response.getMessage(), 0, Math.min(response.getMessage().length(), 1024)));
            }

            return result;
        } catch (Exception e) {
            op.setSuccess(false);
            if (StringUtils.isNotBlank(e.getMessage())) {
                op.setResponse(StringUtils.substring(e.getMessage(), 0, Math.min(e.getMessage().length(), 1024)));
            }
            logBuilder.append("Exception: ").append(GlobalExceptionHandler.getFilteredExceptionMessage(e, ProjectConst.BASE_PACKAGE)).append("\n");
            throw e;
        } finally {
            long measured = System.currentTimeMillis() - startTime;
            op.setMeasured(measured);
            logBuilder.append("Time Cost      : ").append(measured).append("ms\n");
            op.setTraceId(MDC.get(TraceInterceptor.TRACE_ID));
            logService.saveLogAsync(op);
            logBuilder.append("=========================================== End ===========================================\n");
            log.info(logBuilder.toString());
        }
    }

    /**
     * 判断是否忽略日志
     */
    private List<Boolean> isIgnoreLog(JoinPoint joinPoint) {
        boolean ignoreRequestClass = false;
        boolean ignoreResponseClass = false;

        boolean ignoreRequestMethod = false;
        boolean ignoreResponseMethod = false;

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        // 检查方法级别的忽略注解
        if (signature.getMethod().isAnnotationPresent(ApiPolicy.class)) {
            ApiPolicy annotation = signature.getMethod().getAnnotation(ApiPolicy.class);
            if (annotation != null) {
                ignoreRequestMethod = annotation.noReq();
                ignoreResponseMethod = annotation.noRes();
            }
        }

        // 检查类级别的忽略注解
        if (joinPoint.getTarget().getClass().isAnnotationPresent(ApiPolicy.class)) {
            ApiPolicy annotation = joinPoint.getTarget().getClass().getAnnotation(ApiPolicy.class);
            if (annotation != null) {
                ignoreRequestClass = annotation.noReq();
                ignoreResponseClass = annotation.noRes();
            }
        }
        return Arrays.asList(ignoreRequestClass || ignoreRequestMethod, ignoreResponseClass || ignoreResponseMethod);
    }

    /**
     * 过滤接口请求参数
     */
    private List<Object> filterRequestArgs(JoinPoint joinPoint) {
        // 过滤掉方法上的HttpServletRequest和HttpServletResponse参数，否则会造成序列化错误
        return Arrays.stream(joinPoint.getArgs())
                .filter(arg -> !(arg instanceof HttpServletRequest || arg instanceof HttpServletResponse))
                .filter(arg -> !(arg instanceof MultipartFile || arg instanceof MultipartFile[]))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}


