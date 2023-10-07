package ${table.packageName};

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * ${table.comment} 响应类
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ${className} {

<#list table.columns as column>
    /**
     * ${column.comment}
     */
    private ${column.javaType} ${column.property};

</#list>
}