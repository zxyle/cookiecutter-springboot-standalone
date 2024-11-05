package {{ cookiecutter.basePackage }}.biz.auth.profile;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户资料 Mapper 接口
 */
@Mapper
public interface ProfileMapper extends BaseMapper<Profile> {

}
