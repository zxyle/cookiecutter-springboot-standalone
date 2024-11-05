package {{ cookiecutter.basePackage }}.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 性别枚举（用于实体类属性）
 */
@Getter
@ToString
@AllArgsConstructor
public enum GenderEnum {

    /**
     * 女
     */
    FEMALE(0, "女"),

    /**
     * 男
     */
    MALE(1, "男");

    /**
     * 用于数据库存储的值
     */
    @EnumValue
    private final int code;

    /**
     * 用于前端展示的值
     */
    @JsonValue
    private final String name;

}
