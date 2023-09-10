package {{ cookiecutter.basePackage }}.biz.site.feedback;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * 意见反馈 Mapper 接口
 */
@Repository
public interface FeedbackMapper extends BaseMapper<Feedback> {

}
