package ${package};

<#list table.imports as import>
import ${import};
</#list>

<#if excel>
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
</#if>

import lombok.Data;

/**
 * ${table.comment} Excel数据导出
<#if author??>
 *
 * @author ${author}
</#if>
 */
@Data
public class ${className} {
    <#if excel>
    /**
     * 序号
     */
    @ColumnWidth(8)
    @ExcelProperty("序号")
    private int seq;
    </#if>

<#list table.columns as column>
    <#if excel>
    @ColumnWidth(${column.comment?length * 4})
    @ExcelProperty("${column.comment}")
    </#if>
    private ${column.javaType} ${column.property};

</#list>
}