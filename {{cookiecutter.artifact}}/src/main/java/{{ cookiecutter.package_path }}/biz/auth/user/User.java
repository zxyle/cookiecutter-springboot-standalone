package {{ cookiecutter.basePackage }}.biz.auth.user;

import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户
 */
@Data
@TableName("auth_user")
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 登录用户名
     */
    private String username;

    /**
     * 登录手机号码
     */
    private String mobile;

    /**
     * 登录邮箱
     */
    private String email;

    /**
     * 密码
     */
    @JsonIgnore
    private String pwd;

    /**
     * 昵称/名字/真实姓名（只用于展示）
     */
    private String nickname;

    /**
     * 账号是否锁定
     */
    @JsonIgnore
    private Boolean locked;

    /**
     * 是否超级管理员
     */
    @JsonIgnore
    private Boolean admin;

    /**
     * 账号过期时间
     */
    @JsonIgnore
    private LocalDateTime expireTime;

    /**
     * 密码上次修改时间
     */
    @JsonIgnore
    private LocalDateTime pwdChangeTime;

    /**
     * 账号是否启用
     */
    private Boolean enabled;

    /**
     * 是否需要修改密码
     */
    private Boolean mustChangePwd;

    /**
     * 最后登录时间
     */
    @JsonIgnore
    private LocalDateTime lastLoginTime;

    /**
     * 微信用户唯一标识
     */
    private String openid;

}
