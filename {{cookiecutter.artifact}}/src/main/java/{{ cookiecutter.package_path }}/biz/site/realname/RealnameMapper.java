package {{ cookiecutter.basePackage }}.biz.site.realname;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 实名认证 数据访问类
 */
@Mapper
public interface RealnameMapper extends BaseMapper<Realname> {

}