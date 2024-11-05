package {{ cookiecutter.basePackage }}.biz.auth.mfa.question;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.biz.sys.captcha.ValidateService;
import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.R;
import {{ cookiecutter.basePackage }}.common.validation.Add;
import {{ cookiecutter.basePackage }}.common.validation.Update;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.validation.Valid;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 安全问题管理
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class QuestionController extends AuthBaseController {

    final QuestionService thisService;
    final QuestionMapper thisMapper;
    final AnswerService answerService;
    final ValidateService validateService;
    final StringRedisTemplate stringRedisTemplate;
    private final Random random = new Random();

    /**
     * 安全问题分页查询
     */
    @LogOperation(name = "安全问题分页查询", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:question:list')")
    @GetMapping("/questions")
    public R<Page<Question>> page(@Valid PaginationRequest req) {
        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Question::getId, Question::getAsk);
        wrapper.like(req.getKeyword() != null, Question::getAsk, req.getKeyword());
        Page<Question> page = thisService.page(req.getPage(), wrapper);
        return R.ok(page);
    }

    /**
     * 查询所有安全问题
     */
    @PreAuthorize("@ck.hasPermit('auth:question:list')")
    @GetMapping("/questions/list")
    public R<List<Question>> list() {
        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Question::getId, Question::getAsk);
        return R.ok(thisService.list(wrapper));
    }

    /**
     * 新增安全问题
     */
    @LogOperation(name = "新增安全问题", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:question:add')")
    @PostMapping("/questions")
    public R<Question> add(@Valid @RequestBody Question entity) {
        boolean success = thisService.save(entity);
        return success ? R.ok(entity) : R.fail("新增安全问题失败");
    }

    /**
     * 按ID删除安全问题
     */
    @LogOperation(name = "按ID删除安全问题", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:question:delete')")
    @DeleteMapping("/questions/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        if (answerService.isAlreadyUsed(id)) {
            return R.fail("该安全问题已经被使用，无法删除");
        }

        boolean success = thisService.removeById(id);
        return success ? R.ok("删除安全问题成功") : R.fail("删除安全问题失败");
    }

    /**
     * 随机查询用户设置的安全问题
     */
    @LogOperation(name = "随机查询用户设置的安全问题", biz = "auth")
    @GetMapping("/questions/random")
    public R<Question> get() {
        List<Question> questions = thisMapper.findQuestionsByUserId(getUserId());
        if (CollectionUtils.isEmpty(questions)) {
            return R.fail("请先设置安全问题");
        }

        // 随机查询出一个问题
        Question question = questions.get(random.nextInt(questions.size()));
        stringRedisTemplate.opsForValue().set("question:" + getUserId(), String.valueOf(question.getId()));
        return R.ok(question);
    }

    /**
     * 用户设置安全问题
     */
    @LogOperation(name = "用户设置安全问题", biz = "auth")
    @PostMapping("/answers")
    public R<Void> add(@Validated(Add.class) @RequestBody AddAnswerRequest req) {
        List<Question> questions = thisMapper.findQuestionsByUserId(getUserId());
        if (CollectionUtils.isNotEmpty(questions)) {
            return R.fail("用户已经设置过安全问题");
        }

        boolean success = saveAnswers(req, getUserId());
        return success ? R.ok("成功设置安全问题") : R.fail("新增安全问题失败");
    }

    /**
     * 用户修改安全问题（需先校验验证码）
     */
    @LogOperation(name = "用户修改安全问题", biz = "auth")
    @PutMapping("/answers")
    public R<Void> update(@Validated(Update.class) @RequestBody AddAnswerRequest req) {
        String key = "code:" + getUserId();
        if (!validateService.validate(key, req.getCode())) {
            return R.fail("验证码错误");
        }

        LambdaQueryWrapper<Answer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Answer::getUserId, getUserId());
        boolean removed = answerService.remove(wrapper);
        boolean saved = saveAnswers(req, getUserId());
        return (removed && saved) ? R.ok("成功修改密保问题") : R.fail("修改密保问题失败");
    }

    /**
     * 保存用户安全问题答案
     *
     * @param req 包含用户安全问题答案请求
     * @param userId 用户ID
     * @return 是否保存成功
     */
    private boolean saveAnswers(AddAnswerRequest req, Integer userId) {
        List<Answer> answers = req.getAnswers().stream().map(ans -> {
            Answer answer = new Answer();
            answer.setUserId(userId);
            answer.setQuestionId(ans.getQuestionId());
            answer.setSecret(DigestUtil.md5Hex(ans.getAnswer().trim()));  // md5加密，防止数据库泄露
            return answer;
        }).{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};
        return answerService.saveBatch(answers);
    }

}
