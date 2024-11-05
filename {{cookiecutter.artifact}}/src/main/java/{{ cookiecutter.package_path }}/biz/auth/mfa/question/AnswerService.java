package {{ cookiecutter.basePackage }}.biz.auth.mfa.question;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 安全问题答案 服务实现类
 */
@Slf4j
@Service
public class AnswerService extends ServiceImpl<AnswerMapper, Answer> {

    /**
     * 查询是否已经被使用
     *
     * @param questionId 问题ID
     * @return 是否已经被使用 true:已经被使用 false:未被使用
     */
    public boolean isAlreadyUsed(Integer questionId) {
        LambdaQueryWrapper<Answer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Answer::getQuestionId, questionId);
        return exists(wrapper);
    }

    /**
     * 查询用户的安全问题答案
     *
     * @param userId     用户ID
     * @param questionId 问题ID
     * @return 安全问题答案
     */
    public Answer findByUserId(Integer userId, Integer questionId) {
        LambdaQueryWrapper<Answer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Answer::getUserId, userId);
        wrapper.eq(Answer::getQuestionId, questionId);
        return getOne(wrapper);
    }
}
