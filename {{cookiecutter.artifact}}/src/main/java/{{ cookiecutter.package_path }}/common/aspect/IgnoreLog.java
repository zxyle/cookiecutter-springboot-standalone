package {{ cookiecutter.basePackage }}.common.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 忽略记录操作日志
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface IgnoreLog {

    /**
     * 忽略请求参数打印
     */
    boolean ignoreRequest() default false;

    /**
     * 忽略响应参数打印 (有时候响应参数会包含敏感信息、内容过大，不适合打印)
     */
    boolean ignoreResponse() default false;

}