package {{ cookiecutter.basePackage }}.common.aspect;

import java.lang.annotation.*;

/**
 * 接口行为控制 （这里设计很多开关是避免一个接口太多注解，影响代码可读性，多次获取注解，影响性能）
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ApiPolicy {

    /**
     * 标记一个Controller类或方法是否允许匿名访问
     *
     * @return true 允许匿名访问； false 不允许匿名访问
     */
    boolean anon() default false;

    /**
     * 是否不打印请求参数
     *
     * @return true 不打印请求参数； false 打印请求参数
     */
    boolean noReq() default false;

    /**
     * 是否不打印响应参数 (有时候响应参数会包含敏感信息、内容过大，不适合打印)
     *
     * @return true 不打印响应参数； false 打印响应参数
     */
    boolean noRes() default false;

    // TODO 考虑集成 @LogOperation 注解

}
