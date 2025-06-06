package {{ cookiecutter.basePackage }}.biz.auth.password;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import {{ cookiecutter.basePackage }}.biz.auth.mfa.question.AddAnswerRequest;
import {{ cookiecutter.basePackage }}.biz.auth.password.request.*;
import {{ cookiecutter.basePackage }}.biz.auth.password.response.PasswordComplexityResponse;
import {{ cookiecutter.basePackage }}.biz.auth.password.response.ResetPasswordResponse;
import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.biz.auth.mfa.question.Answer;
import {{ cookiecutter.basePackage }}.biz.auth.login.LoginService;
import {{ cookiecutter.basePackage }}.biz.auth.mfa.question.AnswerService;
import {{ cookiecutter.basePackage }}.biz.auth.user.User;
import {{ cookiecutter.basePackage }}.biz.auth.user.UserService;
import {{ cookiecutter.basePackage }}.biz.auth.mfa.AccountUtil;
import {{ cookiecutter.basePackage }}.biz.sys.captcha.ValidateService;
import {{ cookiecutter.basePackage }}.biz.sys.setting.SettingService;
import {{ cookiecutter.basePackage }}.biz.sys.captcha.CaptchaUtil;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.validation.Valid;
import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 密码管理
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/password")
public class PasswordController extends AuthBaseController {

    final StringRedisTemplate stringRedisTemplate;
    final UserService userService;
    final SettingService setting;
    final PasswordService thisService;
    final LoginService loginService;
    final ValidateService validateService;
    final AnswerService answerService;

    /**
     * 使用旧密码方式修改密码
     */
    @LogOperation(name = "使用旧密码方式修改密码", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:password:change')")
    @PostMapping("/change")
    public R<Void> change(@Valid @RequestBody ChangeByOldRequest req) {
        User user = getLoggedInUser();

        if (thisService.isMatch(req.getOldPassword(), user.getPassword())) {
            // 判断新密码是否和旧密码一致
            if (!setting.get("pwd.enable-same").isReal() && thisService.isMatch(req.getNewPassword(), user.getPassword())) {
                return R.fail("修改失败，密码不符合规则");
            }

            boolean success = thisService.change(user.getId(), req.getNewPassword(), ChangePasswordEnum.CHANGE);
            // 退出当前登录状态
            boolean isLoggedOut = loginService.logout(user.getId());
            return R.result(success && isLoggedOut);
        }

        // 如果有人通过此接口反复尝试爆破旧密码，这里会记录尝试次数，超过一定次数后会锁定用户，提示用户被锁定，联系管理员解锁
        // 并记录日志，用户通过管理员重置密码或自行找回密码
        String key = "pwd:change:" + user.getId();
        Long times = stringRedisTemplate.opsForValue().increment(key);
        int retryTimes = times == null ? 1 : times.intValue();
        int maxRetry = setting.get("pwd.change-max-retry-times").getIntValue();
        if (maxRetry > retryTimes) {
            Integer remainTimes = maxRetry - retryTimes;
            String message = String.format("修改失败，旧密码可能不正确，还可重试%d次", remainTimes);
            return R.fail(message);
        }

        userService.locked(user.getId());
        return R.fail("尝试次数过多，账号已被锁定，请联系管理员");
    }

    /**
     * 忘记/找回密码（通过短信或邮件验证码方式）
     */
    @PostMapping("/forget/code")
    public R<Void> forget(@Valid @RequestBody ForgetRequest req) {
        String account = req.getAccount();
        String key = "code:" + account;
        if (!validateService.validate(key, req.getCode())) {
            return R.fail("找回密码失败，验证码可能已过期或错误");
        }

        // 获取用户ID
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AccountUtil.isEmail(account), User::getEmail, account);
        wrapper.eq(AccountUtil.isMobile(account), User::getMobile, account);
        User user = userService.getOne(wrapper);
        if (null == user) {
            return R.fail("找回密码失败，用户不存在");
        }

        // 修改密码
        boolean success = thisService.change(user.getId(), req.getNewPassword(), ChangePasswordEnum.FORGET);
        if (!success) {
            return R.fail("找回密码失败");
        }
        // 用户可能已经在某处登录，退出登录
        loginService.logout(user.getId());
        userService.unlock(user.getId());
        return R.ok("找回密码成功, 请重新登录");
    }

    /**
     * 忘记/找回密码（通过密保问题方式）
     */
    @PostMapping("/forget/question")
    public R<Void> forgetByQuestion(@Valid @RequestBody ForgetByQuestionRequest req) {
        User user = userService.findByAccount(req.getAccount());
        if (null == user) {
            return R.fail("找回密码失败，用户不存在");
        }

        // 校验密保问题
        List<AddAnswerRequest.AnswerRequest> answers = req.getAnswers();
        AddAnswerRequest.AnswerRequest answerRequest = answers.get(0);

        // 判断密保问题是否与随机生成的密保问题一致
        Integer questionId = answerRequest.getQuestionId();
        String s = stringRedisTemplate.opsForValue().get("question:" + user.getId());
        if (StringUtils.isBlank(s) || !Integer.valueOf(s).equals(questionId)) {
            return R.fail("找回密码失败，密保问题不存在");
        }
        stringRedisTemplate.delete("question:" + user.getId());

        // 判断密保问题答案是否正确
        Answer answer = answerService.findByUserId(user.getId(), questionId);
        if (null == answer) {
            return R.fail("找回密码失败，密保问题不存在");
        }
        if (!DigestUtil.md5Hex(answerRequest.getAnswer().trim()).equalsIgnoreCase(answer.getSecret())) {
            return R.fail("找回密码失败，密保问题答案错误");
        }

        // 回答正确，修改密码
        boolean success = thisService.change(user.getId(), req.getNewPassword(), ChangePasswordEnum.FORGET);
        if (!success) {
            return R.fail("找回密码失败");
        }
        // 用户可能已经在某处登录，退出登录
        loginService.logout(user.getId());
        userService.unlock(user.getId());
        return R.ok("找回密码成功, 请重新登录");
    }

    /**
     * 重置密码（支持系统管理员、用户组管理员重置密码）
     */
    @LogOperation(name = "重置密码", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:password:reset')")
    @Secured({"ROLE_admin", "ROLE_group-admin"})
    @PostMapping("/reset")
    public R<ResetPasswordResponse> reset(@Valid @RequestBody ResetPasswordRequest req) {
        if (!groupService.isAllowed(getUserId(), req.getUserId(), null)) {
            return R.fail("重置密码失败，没有权限");
        }

        // 考虑新密码来源 1.前端用户传入 2.后端随机生成 3.系统配置(需以明文保存，不安全)
        Integer userId = req.getUserId();
        String rawPassword = req.getPassword();
        rawPassword = StringUtils.isBlank(rawPassword) ?
                CaptchaUtil.randCode(setting.get("pwd.min-length").getIntValue(), setting.get("pwd.chars").getStr()) : rawPassword;
        boolean success = thisService.change(userId, rawPassword, ChangePasswordEnum.RESET);

        if (success) {
            // 退出当前登录状态
            loginService.logout(userId);
            userService.unlock(userId);
            return R.ok(new ResetPasswordResponse(rawPassword));
        }
        return R.fail("重置密码失败");
    }

    /**
     * 初次登录后修改密码
     */
    @LogOperation(name = "初次登录后修改密码", biz = "auth")
    @PostMapping("/init")
    public R<Void> init(@RequestBody InitPasswordRequest req) {
        User user = getLoggedInUser();

        // 初次登录，最后登录时间在1分钟内，不需要校验旧密码, 直接修改密码
        Duration duration = Duration.between(user.getLastLoginTime(), LocalDateTime.now());
        if (duration.toMinutes() < 1) {
            boolean success = thisService.change(user.getId(), req.getPassword(), ChangePasswordEnum.CHANGE);
            return R.result(success);
        }

        return R.fail("密码修改失败");
    }

    /**
     * 密码复杂度计算（0-5分）
     *
     * @param password 待检测密码|lHfxoPrKOaWjSqwN
     */
    @PreAuthorize("@ck.hasPermit('auth:password:complexity')")
    @GetMapping("/complexity")
    public R<PasswordComplexityResponse> complexity(@NotBlank String password) {
        int score = PasswordChecker.checkPasswordComplexity(password);
        return R.ok(new PasswordComplexityResponse(score));
    }

    /**
     * 生成随机密码
     */
    @PreAuthorize("@ck.hasPermit('auth:password:random')")
    @GetMapping("/random")
    public R<List<String>> random(@Valid RandomRequest req) {
        List<String> list = new ArrayList<>(req.getCount());
        for (int i = 0; i < req.getCount(); i++) {
            String pwd = CaptchaUtil.randCode(req.getLength(), req.getChars());
            list.add(pwd);
        }
        return R.ok(list);
    }

}
