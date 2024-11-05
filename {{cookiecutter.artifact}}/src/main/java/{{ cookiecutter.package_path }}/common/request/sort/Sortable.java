package {{ cookiecutter.basePackage }}.common.request.sort;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来给实体类属性标注可排序
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Sortable {

    /**
     * 排序顺序
     *
     * @return 排序顺序, 默认为升序
     */
    SortOrder[] allowed() default {SortOrder.ASC, SortOrder.DESC};

}
