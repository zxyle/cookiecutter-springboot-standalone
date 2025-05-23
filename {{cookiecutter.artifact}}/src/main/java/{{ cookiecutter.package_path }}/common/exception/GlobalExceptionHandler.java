package {{ cookiecutter.basePackage }}.common.exception;

import {{ cookiecutter.basePackage }}.common.enums.ErrorEnum;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import {{ cookiecutter.namespace }}.validation.ConstraintViolationException;
{% if cookiecutter.bootVersion.split('.')[0] == '3' -%}
import org.springframework.web.method.annotation.HandlerMethodValidationException;
{% endif -%}
import {{ cookiecutter.namespace }}.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理不支持的HTTP请求方法异常
     *
     * @param request Web请求对象
     * @param httpServletRequest HTTP请求对象
     * @return 返回操作结果
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<Void> handleHttpRequestMethodNotSupported(WebRequest request, HttpServletRequest httpServletRequest) {
        String url = request.getDescription(false).replace("uri=", "");
        String message = String.format("请求方法不支持: %s %s", httpServletRequest.getMethod(), url);
        log.error(message);
        return R.fail(message, HttpStatus.METHOD_NOT_ALLOWED.value());
    }

    /**
     * 处理使用GET PUT DELETE操作不存在的数据
     */
    @ExceptionHandler(DataNotFoundException.class)
    public R<Void> handleDataNotFoundException(DataNotFoundException ex) {
        // 对一个不存在的数据进行操作，认定是一次违规操作
        return R.fail(ex.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    /**
     * 处理密码不正确异常
     */
    @ExceptionHandler(BadCredentialsException.class)
    public R<Void> handleAuthException(Exception ex) {
        log.warn("用户名或密码错误: {}", ex.getMessage());
        return R.fail("用户名或密码错误", HttpStatus.UNAUTHORIZED.value());
    }

    /**
     * 处理无权限访问异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public R<Void> handleAccessDeniedException(Exception ex) {
        log.warn("权限不足: {}", ex.getMessage());
        return R.fail("权限不足", HttpStatus.FORBIDDEN.value());
    }

    /**
     * 处理密码已过期异常
     */
    @ExceptionHandler(CredentialsExpiredException.class)
    public R<Void> handleCredentialsExpiredException(Exception ex) {
        log.warn("密码已过期: {}", ex.getMessage());
        return R.fail("密码已过期，请联系管理员", HttpStatus.UNAUTHORIZED.value());
    }

    /**
     * 处理账号过期异常
     */
    @ExceptionHandler(AccountExpiredException.class)
    public R<Void> handleAccountExpiredException(AccountExpiredException ex) {
        log.warn("账号已过期: {}", ex.getMessage());
        return R.fail("账号已过期，请联系管理员", HttpStatus.FORBIDDEN.value());
    }

    /**
     * 处理账号停用异常
     */
    @ExceptionHandler(DisabledException.class)
    public R<Void> handleDisabledException(DisabledException ex) {
        log.warn("账号已被停用: {}", ex.getMessage());
        return R.fail("账号已被停用，请联系管理员", HttpStatus.FORBIDDEN.value());
    }

    /**
     * 处理账号锁定异常
     */
    @ExceptionHandler(LockedException.class)
    public R<Void> handleLockedException(LockedException ex) {
        log.warn("账号已被锁定: {}", ex.getMessage());
        return R.fail("账号已被锁定，请联系管理员", HttpStatus.FORBIDDEN.value());
    }

    /**
     * 处理AnonymousAuthenticationToken -> UsernamePasswordAuthenticationToken类型转换异常
     */
    // @ExceptionHandler(ClassCastException.class)
    // public R<Void> handleClassCastException(Exception ex) {
    //     log.warn("登录失效,请重新登录: {}", ex.getMessage());
    //     return R.fail("登录失效，请重新登录", HttpStatus.UNAUTHORIZED.value());
    // }

    /**
     * 处理JSON提交方式数据校验失败异常
     */
    // MethodArgumentNotValidException 是BindException的子类
    @ExceptionHandler(BindException.class)
    public R<Void> handleValidationExceptions(BindException ex) {
        StringBuilder builder = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            builder.append(fieldName).append(":").append(errorMessage).append(";");
        });
        log.warn("JSON数据校验失败: {}", builder);
        return R.fail(builder.toString(), HttpStatus.BAD_REQUEST.value());
    }

    /**
     * 处理表单方式数据校验失败异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public R<Void> handleConstraintViolationException(ConstraintViolationException ex) {
        log.warn("表单数据校验失败: {}", ex.getMessage());
        return R.fail(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    {% if cookiecutter.bootVersion.split('.')[0] == '3' -%}
    /**
     * spring boot3添加了对方法入参的校验
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public R<Void> handleMethodValidationException(HandlerMethodValidationException ex) {
        log.warn("URL参数校验失败: {}", ex.getMessage());
        return R.fail(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    {% endif %}

    /**
     * 处理其他未知异常
     */
    @ExceptionHandler(value = {Exception.class, NullPointerException.class, HttpMessageNotReadableException.class})
    public R<Void> exceptionHandler(Exception e) {
        // log.error("未知异常: {}", e.getMessage());
        // log.error("异常详情: {}", getExceptionMessage(e));
        return R.fail(ErrorEnum.SERVER_ERROR);
    }

    /**
     * 获取异常信息的字符串表示形式
     *
     * @param ex 异常对象
     * @return 异常信息的字符串表示形式
     */
    public static String getExceptionMessage(Exception ex) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        ex.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }

    public static String getFilteredExceptionMessage(Throwable ex, String packagePrefix) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        // 递归解析所有嵌套异常
        while (ex != null) {
            pw.println(ex.getClass().getName() + ": " + ex.getMessage());

            // 过滤堆栈：仅保留指定包名的调用链
            StackTraceElement[] stackTrace = ex.getStackTrace();
            for (StackTraceElement element : stackTrace) {
                if (element.getClassName().startsWith(packagePrefix)) {
                    pw.println("\tat " + element); // 保留原始格式
                }
            }

            ex = ex.getCause();
            if (ex != null) {
                pw.print("\nCaused by: ");
            }
        }
        return sw.toString();
    }
}
