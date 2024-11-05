package {{ cookiecutter.basePackage }}.biz.site.release;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 发布版本 Mapper 接口
 */
@Mapper
public interface ReleaseMapper extends BaseMapper<Release> {

}
