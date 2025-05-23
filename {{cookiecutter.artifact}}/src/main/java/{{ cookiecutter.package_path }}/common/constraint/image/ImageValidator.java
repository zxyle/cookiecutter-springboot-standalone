package {{ cookiecutter.basePackage }}.common.constraint.image;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import {{ cookiecutter.namespace }}.validation.ConstraintValidator;
import {{ cookiecutter.namespace }}.validation.ConstraintValidatorContext;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 图片校验器
 */
public class ImageValidator implements ConstraintValidator<Image, MultipartFile> {

    /**
     * 图片宽度
     */
    private int width;

    /**
     * 图片高度
     */
    private int height;

    /**
     * 图片尺寸校验策略
     */
    private ImagePolicy policy;

    @Override
    public void initialize(Image constraintAnnotation) {
        width = constraintAnnotation.width();
        height = constraintAnnotation.height();
        policy = constraintAnnotation.policy();
    }

    @Override
    public boolean isValid(MultipartFile imageFile, ConstraintValidatorContext context) {
        try {
            BufferedImage bufferedImage = ImageIO.read(imageFile.getInputStream());
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            if (policy == ImagePolicy.EQUAL) {
                if (width != this.width || height != this.height) {
                    overWriteMessage(context, "图片尺寸需要为" + this.width + "x" + this.height);
                    return false;
                }
            } else if (policy == ImagePolicy.GREATER_EQUAL) {
                if (width < this.width || height < this.height) {
                    overWriteMessage(context, "图片尺寸需要大于等于" + this.width + "x" + this.height);
                    return false;
                }
            } else if (policy == ImagePolicy.LESS_EQUAL) {
                if (width > this.width || height > this.height) {
                    overWriteMessage(context, "图片尺寸需要小于等于" + this.width + "x" + this.height);
                    return false;
                }
            }
        } catch (IOException e) {
            return false;
        }

        return true;
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
