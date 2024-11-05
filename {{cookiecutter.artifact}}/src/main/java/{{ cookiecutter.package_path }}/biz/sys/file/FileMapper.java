package {{ cookiecutter.basePackage }}.biz.sys.file;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件 Mapper 接口
 */
@Mapper
public interface FileMapper extends BaseMapper<File> {

}
