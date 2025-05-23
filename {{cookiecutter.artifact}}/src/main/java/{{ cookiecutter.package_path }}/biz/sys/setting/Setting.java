package {{ cookiecutter.basePackage }}.biz.sys.setting;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统设置
 */
@Data
@TableName("sys_setting")
@EqualsAndHashCode(callSuper = false)
public class Setting extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 选项
     *
     * @mock captcha.on
     */
    private String optionLabel;

    /**
     * 选项值
     *
     * @mock false
     */
    private String optionValue;

    /**
     * 数据类型
     *
     * @mock java.lang.Boolean
     */
    private String dataType;

    /**
     * 选项描述
     *
     * @mock 登录是否开启图形验证码
     */
    private String description;

    /**
     * 默认值
     *
     * @mock true
     */
    private String defaultValue;

}
