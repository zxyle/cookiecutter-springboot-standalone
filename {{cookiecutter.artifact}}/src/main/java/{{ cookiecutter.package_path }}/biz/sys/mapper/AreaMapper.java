package {{ cookiecutter.basePackage }}.biz.sys.mapper;

import {{ cookiecutter.basePackage }}.biz.sys.entity.Area;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 行政区 Mapper 接口
 */
@Repository
public interface AreaMapper extends BaseMapper<Area> {

    List<Area> selectAll();

}
