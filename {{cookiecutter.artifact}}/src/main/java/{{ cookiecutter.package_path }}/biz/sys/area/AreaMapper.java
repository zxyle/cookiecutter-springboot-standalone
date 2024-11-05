package {{ cookiecutter.basePackage }}.biz.sys.area;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 行政区 Mapper 接口
 */
@Mapper
public interface AreaMapper extends BaseMapper<Area> {

}
