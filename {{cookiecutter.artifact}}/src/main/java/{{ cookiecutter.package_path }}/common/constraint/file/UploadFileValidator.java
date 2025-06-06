package {{ cookiecutter.basePackage }}.common.constraint.file;

import org.springframework.web.multipart.MultipartFile;

import {{ cookiecutter.namespace }}.validation.ConstraintValidator;
import {{ cookiecutter.namespace }}.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

/**
 * 上传文件校验器
 */
public class UploadFileValidator implements ConstraintValidator<UploadFile, MultipartFile> {

    /**
     * 支持上传的文件扩展名
     */
    private Set<String> allowedExtensions;

    /**
     * 文件最大大小 单位：字节 B
     */
    private int maxSize;

    /**
     * 文件最小大小 单位：字节 B
     */
    private int minSize;

    @Override
    public void initialize(UploadFile constraintAnnotation) {
        allowedExtensions = Arrays.stream(constraintAnnotation.allowedExtensions())
                .collect(collectingAndThen(toSet(), Collections::unmodifiableSet));
        maxSize = constraintAnnotation.maxSize();
        minSize = constraintAnnotation.minSize();
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (value.getSize() > maxSize || value.getSize() < minSize) {
            overWriteMessage(context, "文件大小超过限制");
            return false;
        }

        String originalFilename = value.getOriginalFilename();
        if (originalFilename == null) {
            return false;
        }

        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        return allowedExtensions.stream()
                .anyMatch(extension::equalsIgnoreCase);
    }

    /**
     * 重写校验失败信息
     *
     * @param message                    错误消息
     * @param constraintValidatorContext 上下文
     */
    public void overWriteMessage(ConstraintValidatorContext constraintValidatorContext, String message) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
