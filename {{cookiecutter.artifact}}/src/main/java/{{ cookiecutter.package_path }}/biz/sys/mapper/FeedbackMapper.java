package {{ cookiecutter.basePackage }}.biz.sys.mapper;

import {{ cookiecutter.basePackage }}.biz.sys.entity.Feedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * 意见反馈 Mapper 接口
 */
@Repository
public interface FeedbackMapper extends BaseMapper<Feedback> {

}
