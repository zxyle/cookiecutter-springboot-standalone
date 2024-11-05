package {{ cookiecutter.basePackage }}.common.request;

/**
 * 查询操作符枚举
 */
public enum Operator {

    /**
     * 等于
     */
    EQ,

    /**
     * 不等于
     */
    NE,

    /**
     * LIKE '%值%'
     */
    LIKE,

    /**
     * not LIKE '%值%'
     */
    NOT_LIKE,

    /**
     * LIKE '值%'
     */
    LEFT_LIKE,

    /**
     * LIKE '%值'
     */
    RIGHT_LIKE,

    /**
     * 大于等于
     */
    GE,

    /**
     * 大于
     */
    GT,

    /**
     * 小于等于
     */
    LE,

    /**
     * 小于
     */
    LT,

    /**
     * 属性可以是List、Set、数组、逗号分隔的字符串
     */
    IN, NOT_IN,

    BETWEEN, NOT_BETWEEN,

    EXISTS, NOT_EXISTS,

    IS_NULL, IS_NOT_NULL,

}