package {{ cookiecutter.basePackage }}.common.constraint.image;

/**
 * 图片尺寸校验策略
 */
public enum ImagePolicy {

    // 相等
    EQUAL,

    // 大于等于
    GREATER_EQUAL,

    // 小于等于
    LESS_EQUAL
}
