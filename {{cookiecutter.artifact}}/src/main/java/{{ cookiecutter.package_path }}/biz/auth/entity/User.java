package {{ cookiecutter.basePackage }}.biz.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("auth_user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 登录用户名
     */
    private String loginName;

    /**
     * 密码
     */
    @JsonIgnore
    private String pwd;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 固定电话
     */
    private String telephone;

    /**
     * 锁1-上锁 0-解锁
     */
    private Integer userLock;

    /**
     * 超级管理员
     */
    private Integer isSuper;

    /**
     * 账号过期时间
     */
    private LocalDateTime expireTime;

}
