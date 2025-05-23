package {{ cookiecutter.basePackage }}.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * 性别枚举
 */
public enum SexEnum implements IEnum<Integer> {

    /**
     * 男
     */
    MALE(1, "男"),

    /**
     * 女
     */
    FEMALE(2, "女");

    private final int value;
    private final String desc;

    SexEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.desc;
    }

}
