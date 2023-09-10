package ${table.packageName};

import ${table.basePackageName}.common.request.BaseRequest;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * ${table.comment} 请求类
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ${table.className}Request extends BaseRequest {

<#list table.columns as column>
    <#if column.name != "id" && column.name != "create_time" && column.name != "update_time">
    /**
     * ${column.comment}
     */
    <#if column.length gt 0>
    @Length(max = ${column.length}, message = "${column.comment}长度不能超过${column.length}个字符")
    </#if>
    <#if column.nullable>
        <#if column.dataType == "String">
    @NotBlank(message = "${column.comment}不能为空")
        <#else>
    @NotNull(message = "${column.comment}不能为空")
        </#if>
    </#if>
    private ${column.dataType} ${column.property};

    </#if>
</#list>
}