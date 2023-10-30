package ${table.packageName};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * ${table.comment} 数据访问类
<#if author??>
 *
 * @author ${author}
</#if>
 */
@Mapper
public interface ${className} extends BaseMapper<${table.className}> {

}