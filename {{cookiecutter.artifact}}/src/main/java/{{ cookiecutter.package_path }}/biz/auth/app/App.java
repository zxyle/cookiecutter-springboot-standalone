package {{ cookiecutter.basePackage }}.biz.auth.app;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 应用
 */
@Data
@TableName("auth_app")
@EqualsAndHashCode(callSuper = false)
public class App extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 应用描述
     */
    private String description;

    /**
     * 应用logo
     */
    private String logo;

    /**
     * 应用标识
     */
    private String appKey;

    /**
     * 应用密钥
     */
    private String appSecret;

    /**
     * 回调地址
     */
    private String redirectUrl;

    /**
     * 应用是否启用
     */
    private Boolean enabled;

}
