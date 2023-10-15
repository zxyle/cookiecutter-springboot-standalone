package ${package};

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

<#list table.imports as import>
import ${import};
</#list>
<#if table.hasBaseEntity>
import ${table.baseEntityPath};
</#if>

/**
 * ${table.comment}
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
     */
    private ${column.javaType} ${column.property};

</#list>
}