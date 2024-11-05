package {{ cookiecutter.basePackage }}.common.request;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 查询字段注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryField {

    /**
     * 查询操作符，默认是等于查询
     */
    Operator operator() default Operator.EQ;

    /**
     * 是否跳过这个字段的组装，默认是不跳过
     */
    boolean skip() default false;

    /**
     * 指定查询的列名，默认是使用字段名转换
     */
    String column() default "";
}