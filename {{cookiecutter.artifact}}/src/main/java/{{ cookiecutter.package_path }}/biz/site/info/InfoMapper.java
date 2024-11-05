package {{ cookiecutter.basePackage }}.biz.site.info;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统信息 Mapper 接口
 */
@Mapper
public interface InfoMapper extends BaseMapper<Info> {

}
