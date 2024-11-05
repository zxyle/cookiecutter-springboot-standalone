package {{ cookiecutter.basePackage }}.biz.auth.password;

import lombok.Getter;

/**
 * 密码修改策略枚举
 */
@Getter
public enum ChangePasswordEnum {

    /**
     * 初始密码
     */
    INITIAL("initial", "admin"),

    /**
     * 重置密码
     */
    RESET("reset", "admin"),

    /**
     * 忘记/找回密码
     */
    FORGET("forget", "user"),

    /**
     * 修改密码
     */
    CHANGE("change", "user");

    /**
     * 密码修改类型
     */
    private final String kind;

    /**
     * 密码修改人
     */
    private final String editedBy;

    ChangePasswordEnum(String kind, String editedBy) {
        this.kind = kind;
        this.editedBy = editedBy;
    }

}
