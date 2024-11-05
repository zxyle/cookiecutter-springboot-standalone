package {{ cookiecutter.basePackage }}.common.constraint;

import {{ cookiecutter.namespace }}.validation.Constraint;
import {{ cookiecutter.namespace }}.validation.Payload;
import java.lang.annotation.*;

/**
 * 枚举值校验注解，使用方法:
 * EnumValue(enumClass = Gender.class)
 * private Integer gender;
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValueValidator.class)
public @interface EnumValue {

    String message() default "枚举值不合法";

    Class<? extends ValueEnum> enumClass();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
