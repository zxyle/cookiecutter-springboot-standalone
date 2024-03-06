package ${table.packageName};

import ${table.basePackageName}.common.request.PaginationRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

<#list table.imports as import>
import ${import};
</#list>

/**
 * ${table.comment} 分页请求类
<#if author??>
 *
 * @author ${author}
</#if>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ${className} extends PaginationRequest {

<#list table.columns as column>
    /**
     * ${column.comment}
     */
    <#if column.length gt 0>
    @Length(max = ${column.length}, message = "${column.comment}长度不能超过${column.length}个字符")
    </#if>
    <#if !column.nullable>
        <#if column.javaType == "String">
    @NotBlank(message = "${column.comment}不能为空")
        <#else>
    @NotNull(message = "${column.comment}不能为null")
        </#if>
    </#if>
    private ${column.javaType} ${column.property};

</#list>
}