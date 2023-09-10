package ${table.packageName};

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * ${table.comment}
 */
@Data
@TableName("${table.tableName}")
public class ${table.className} {

<#list table.columns as column>
    /**
     * ${column.comment}
     */
    private ${column.dataType} ${column.property};

</#list>
}