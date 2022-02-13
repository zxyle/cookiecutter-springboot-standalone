package {{ cookiecutter.basePackage }}.biz.sys.mapper;

import {{ cookiecutter.basePackage }}.biz.sys.entity.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统信息 Mapper 接口
 */
@Repository
public interface ParamMapper extends BaseMapper<Param> {

    List<Param> selectAll();

    // 截断表
    void truncate();

}
