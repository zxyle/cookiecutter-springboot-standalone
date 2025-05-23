package {{ cookiecutter.basePackage }}.biz.site.friendly;

import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;

/**
 * 友情链接
 */
@Data
@TableName("site_friendly")
@EqualsAndHashCode(callSuper = false)
public class Friendly extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 链接文本
     */
    @Length(max = 32, message = "链接文本不能超过32个字符")
    @NotBlank(message = "链接文本不能为空")
    private String content;

    /**
     * 链接
     */
    @URL(message = "链接格式不正确")
    @Length(max = 255, message = "链接不能超过255个字符")
    @NotBlank(message = "链接不能为空")
    private String url;

    /**
     * 排序
     */
    @Positive(message = "排序必须为正整数")
    private Integer sort;

    /**
     * 友链是否启用
     */
    private Boolean enabled;

}
