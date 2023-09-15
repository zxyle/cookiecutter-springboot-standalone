package ${package};

import com.baomidou.mybatisplus.annotation.TableName;
import ${table.basePackageName}.common.entity.BaseEntity;
import lombok.*;

<#list table.imports as import>
import ${import};
</#list>

/**
 * ${table.comment}
 */
@Data
@TableName("${table.tableName}")
@EqualsAndHashCode(callSuper = true)
public class ${className} extends BaseEntity {

<#list table.columns as column>
    /**
     * ${column.comment}
     */
    private ${column.javaType} ${column.property};

</#list>
}