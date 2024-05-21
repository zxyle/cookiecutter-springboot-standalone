package ${table.packageName};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;

/**
 * ${table.comment} 数据访问类
<#if author??>
 *
 * @author ${author}
</#if>
 */
@Mapper
public interface ${className} extends BaseMapper<${table.className}> {

    /**
     * 自定义分页查询
     */
    Page<${table.className}> page(Page<${table.className}> page, @Param("req") ${table.className}PageRequest req);

    /**
     * 截断表
     */
    @Update("TRUNCATE TABLE ${table.tableName}")
    void truncate();
}