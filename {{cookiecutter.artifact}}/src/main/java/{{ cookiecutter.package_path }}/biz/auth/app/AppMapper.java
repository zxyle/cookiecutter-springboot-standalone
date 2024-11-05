package {{ cookiecutter.basePackage }}.biz.auth.app;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 应用 Mapper 接口
 */
@Mapper
public interface AppMapper extends BaseMapper<App> {

}
