package {{ cookiecutter.basePackage }}.biz.auth.password;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 密码策略配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "pwd")
public class PasswordProperties {

    /**
     * 密码字符集
     */
    private String chars;

    /**
     * 密码过期天数（-1代表不限制）
     */
    private Integer expireDays;

    /**
     * 最大长度
     */
    private Integer maxLength;

    /**
     * 最小长度
     */
    private Integer minLength;

    /**
     * 密码复杂度
     */
    private String complexity;

    /**
     * 密码历史记录数量 0代表不限制
     */
    private int historyCount;

    /**
     * 是否记录历史密码
     */
    private boolean enableHistory;

    /**
     * 新密码能否和旧密码相同
     */
    private boolean enableSame;

}
