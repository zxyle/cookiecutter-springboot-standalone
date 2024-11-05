package {{ cookiecutter.basePackage }}.biz.auth.mfa.question;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 安全问题 Mapper 接口
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    /**
     * 根据用户ID查询安全问题列表
     *
     * @param userId 用户ID
     * @return 安全问题列表
     */
    List<Question> findQuestionsByUserId(Integer userId);

}
