package {{ cookiecutter.basePackage }}.biz.sys.setting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统设置 Mapper 接口
 */
@Mapper
public interface SettingMapper extends BaseMapper<Setting> {

}
