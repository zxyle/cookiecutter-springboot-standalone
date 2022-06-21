package {{ cookiecutter.basePackage }}.biz.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录日志
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_login_log")
public class LoginLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 登录用户名
     */
    private String loginName;

    /**
     * IP地址
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
     * 1-成功 0-失败
     */
    private Integer isSuccess;

}
