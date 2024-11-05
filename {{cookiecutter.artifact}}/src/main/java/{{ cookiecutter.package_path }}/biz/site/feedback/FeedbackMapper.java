package {{ cookiecutter.basePackage }}.biz.site.feedback;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 意见反馈 Mapper 接口
 */
@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback> {

}
