package {{ cookiecutter.basePackage }}.biz.auth.mfa.question;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 安全问题 服务实现类
 */
@Slf4j
@Service
public class QuestionService extends ServiceImpl<QuestionMapper, Question> {

}
