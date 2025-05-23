package {{ cookiecutter.basePackage }}.biz.sys.setting;

import lombok.Data;

/**
 * 将设置项转换为Java数据类型
 */
@Data
public class Item {

    private long longValue;

    private String str;

    private int intValue;

    private double doubleValue;

    /**
     * 布尔值
     */
    private boolean real;

    public Item(Setting setting) {
        switch (setting.getDataType()) {
            case "long":
            case "Long":
            case "java.lang.Long":
            case "bigint":
                this.longValue = Long.parseLong(setting.getOptionValue());
                break;
            case "str":
            case "string":
            case "String":
            case "java.lang.String":
            case "varchar":
            case "char":
                this.str = setting.getOptionValue();
                break;
            case "int":
            case "Integer":
            case "java.lang.Integer":
            case "tinyint":
            case "smallint":
            case "mediumint":
                this.intValue = Integer.parseInt(setting.getOptionValue());
                break;
            case "boolean":
            case "bool":
            case "Boolean":
            case "java.lang.Boolean":
                this.real = Boolean.parseBoolean(setting.getOptionValue());
                break;
            case "double":
            case "float":
            case "decimal":
            case "java.lang.Double":
            case "java.lang.Float":
            case "java.math.BigDecimal":
                this.doubleValue = Double.parseDouble(setting.getOptionValue());
                break;
            default:
                throw new IllegalArgumentException("不支持的数据类型：" + setting.getDataType());
        }
    }
}
