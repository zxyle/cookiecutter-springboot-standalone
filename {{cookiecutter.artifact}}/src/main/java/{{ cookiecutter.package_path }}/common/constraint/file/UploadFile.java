package {{ cookiecutter.basePackage }}.common.constraint.file;

import {{ cookiecutter.namespace }}.validation.Constraint;
import java.lang.annotation.*;

/**
 * 这个注解用于校验上传文件的属性，可应用于MultipartFile类型的属性上
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = UploadFileValidator.class)
public @interface UploadFile {

    /**
     * 支持上传的扩展名，不需要包含.
     */
    String[] allowedExtensions() default {};

    /**
     * 文件最大大小 默认10Mb
     */
    int maxSize() default 1024 * 1024 * 10;

    /**
     * 文件最小大小 默认0
     */
    int minSize() default 0;

    String message() default "文件格式不支持";

    /**
     * @return the groups the constraint belongs to
     */
    Class<?>[] groups() default {};

    /**
     * @return the payload associated to the constraint
     */
    Class<?>[] payload() default {};

}
