package {{ cookiecutter.basePackage }}.common.constraint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 性别枚举
 */
@Getter
@RequiredArgsConstructor
public enum Gender implements ValueEnum {

    /**
     * 1-男
     */
    MALE(1, "男"),

    /**
     * 2-女
     */
    FEMALE(2, "女");

    /**
     * 枚举值
     */
    private final Integer value;

    /**
     * 枚举描述
     */
    private final String name;
}
