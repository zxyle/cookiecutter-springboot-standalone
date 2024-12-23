package {{ cookiecutter.basePackage }}.biz.site.friendly;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 友情链接 Mapper 接口
 */
@Mapper
public interface FriendlyMapper extends BaseMapper<Friendly> {

}
