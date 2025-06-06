package {{ cookiecutter.basePackage }}.biz.sys.log;

import {{ cookiecutter.basePackage }}.common.entity.LiteEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 登录日志
 */
@Data
@TableName("sys_login_log")
@EqualsAndHashCode(callSuper = false)
public class LoginLog extends LiteEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginTime;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 登录账号
     */
    private String account;

    /**
     * 登录IP
     */
    private String ip;

    /**
     * 浏览器请求头
     */
    private String ua;

    /**
     * 消息
     */
    private String msg;

    /**
     * 登录是否成功
     */
    private Boolean success;

}
