package {{ cookiecutter.basePackage }}.common.constraint.image;

import {{ cookiecutter.namespace }}.validation.Constraint;
import java.lang.annotation.*;

/**
 * 这个注解用于校验上传的图片文件的尺寸是否符合规定，可应用于MultipartFile类型的属性上
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Image {

    /**
     * @return the width of the image
     */
    int width() default 0;

    /**
     * @return the height of the image
     */
    int height() default 0;

    /**
     * @return the policy of the image size
     */
    ImagePolicy policy() default ImagePolicy.EQUAL;

    /**
     * @return the error message template
     */
    String message() default "图片必须{policy} {width}x{height}";

    /**
     * @return the groups the constraint belongs to
     */
    Class<?>[] groups() default {};

    /**
     * @return the payload associated to the constraint
     */
    Class<?>[] payload() default {};

}
