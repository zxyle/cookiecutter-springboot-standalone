package {{ cookiecutter.basePackage }}.biz.site.banner;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 轮播图数据访问类
 */
@Mapper
public interface BannerMapper extends BaseMapper<Banner> {

}