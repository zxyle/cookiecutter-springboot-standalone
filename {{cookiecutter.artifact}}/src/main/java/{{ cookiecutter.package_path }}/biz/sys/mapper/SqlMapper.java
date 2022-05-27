package {{ cookiecutter.basePackage }}.biz.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import {{ cookiecutter.basePackage }}.biz.sys.entity.Sql;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * SQL执行 Mapper 接口
 */
@Repository
public interface SqlMapper extends BaseMapper<Sql> {

    List<Sql> selectAll();

    // 截断表
    void truncate();

    List<Map<String, Object>> execute(String sql);

}
