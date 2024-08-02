package ${package};

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

<#list table.imports as import>
import ${import};
</#list>
<#if table.hasBaseEntity>
import ${table.baseEntityPath};
</#if>

/**
 * ${table.comment}
<#if author??>
 *
 * @author ${author}
</#if>
 */
@Data
@TableName("${table.tableName}")
<#if table.hasBaseEntity>
@EqualsAndHashCode(callSuper = false)
public class ${className} extends ${table.baseEntityClassName} {
<#else>
public class ${className} {
</#if>

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