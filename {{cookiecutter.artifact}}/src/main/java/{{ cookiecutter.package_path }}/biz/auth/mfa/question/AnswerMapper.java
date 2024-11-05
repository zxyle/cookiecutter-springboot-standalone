package {{ cookiecutter.basePackage }}.biz.auth.mfa.question;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 安全问题答案 Mapper 接口
 */
@Mapper
public interface AnswerMapper extends BaseMapper<Answer> {

}
