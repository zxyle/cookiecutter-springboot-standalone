package {{ cookiecutter.basePackage }}.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 用户认证策略配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "auth.user")
public class AuthUserProperties {

    /**
     * 最大登录失败次数
     */
    private Integer maxFailedTimes;

    /**
     * 锁定时间
     */
    private Integer lockTime;

    /**
     * 锁定时间单位
     */
    private TimeUnit lockTimeUnit;

    /**
     * 注册后是否自动登录
     */
    private boolean autoLogin;

    /**
     * 默认密码
     */
    private String defaultPassword;

    /**
     * 认证方式（password-密码登录、qrcode-扫码登录、code-验证码登录）
     */
    private String verification;

    /**
     * 是否开放用户注册
     */
    private boolean openRegistration;

    /**
     * 注册后默认角色ID
     */
    private Integer defaultRole;

    /**
     * 初次登录后是否需修改密码
     */
    private boolean reset;
}
