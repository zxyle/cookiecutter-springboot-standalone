package ${table.packageName};

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

<#list table.imports as import>
import ${import};
</#list>

/**
 * ${table.comment} 响应类
<#if author??>
 *
 * @author ${author}
</#if>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ${className} {

<#list table.columns as column>
    /**
     * ${column.comment}
     *
     * @mock <#if column.defaultValue??>${column.defaultValue}</#if>
     */
    <#if column.javaType == "LocalDateTime">
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    </#if>
    private ${column.javaType} ${column.property};

</#list>
}