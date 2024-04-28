package ${table.packageName};

import ${table.basePackageName}.common.request.BaseRequest;
import ${table.basePackageName}.common.validation.Add;
import ${table.basePackageName}.common.validation.Search;
import ${table.basePackageName}.common.validation.Update;
import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;
import {{ cookiecutter.namespace }}.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

<#list table.imports as import>
import ${import};
</#list>

/**
 * ${table.comment} 请求类
<#if author??>
 *
 * @author ${author}
</#if>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ${className} extends BaseRequest {

<#list table.columns as column>
    /**
     * ${column.comment}
     */
    <#if column.length gt 0>
    @Length(max = ${column.length?string("0")}, message = "${column.comment}长度不能超过${column.length?string("0")}个字符", groups = {Add.class, Update.class, Search.class})
    </#if>
    <#if !column.nullable>
        <#if column.javaType == "String">
    @NotBlank(message = "${column.comment}不能为空", groups = {Add.class, Update.class, Search.class})
        <#else>
    @NotNull(message = "${column.comment}不能为null", groups = {Add.class, Update.class, Search.class})
        </#if>
    </#if>
    private ${column.javaType} ${column.property};

</#list>
}