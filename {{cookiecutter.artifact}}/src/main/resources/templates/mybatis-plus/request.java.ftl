package ${table.packageName};

import ${table.basePackageName}.common.request.BaseRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;
import {{ cookiecutter.namespace }}.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

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
     *
     * @mock <#if column.defaultValue??>${column.defaultValue}</#if>
     */
    <#if column.length gt 0>
    @Length(max = ${column.length?string("0")}, message = "${column.comment}长度不能超过${column.length?string("0")}个字符")
    </#if>
    <#if !column.nullable>
        <#if column.javaType == "String">
    @NotBlank(message = "${column.comment}不能为空")
        <#else>
    @NotNull(message = "${column.comment}不能为null")
        </#if>
    </#if>
    <#if column.javaType == "LocalDateTime">
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    </#if>
    <#if column.javaType == "LocalDate">
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    </#if>
    private ${column.javaType} ${column.property};

</#list>
}