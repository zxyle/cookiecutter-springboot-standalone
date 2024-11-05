package {{ cookiecutter.basePackage }}.biz.sys.log;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志 Mapper 接口
 */
@Mapper
public interface OperateLogMapper extends BaseMapper<OperateLog> {

}
