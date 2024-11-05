package {{ cookiecutter.basePackage }}.biz.site.realname;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 实名认证状态枚举（用于实体类属性）
 */
@Getter
@ToString
@AllArgsConstructor
public enum VerificationStatusEnum {

    UNVERIFIED(0, "未认证"),
    VERIFICATION_IN_PROGRESS(1, "认证中"),
    VERIFIED(2, "已认证"),
    VERIFICATION_FAILED(3, "认证失败");

    /**
     * 用于数据库存储的值
     */
    @EnumValue
    private final int code;

    /**
     * 用于前端展示的值
     */
    @JsonValue
    private final String desc;
}
