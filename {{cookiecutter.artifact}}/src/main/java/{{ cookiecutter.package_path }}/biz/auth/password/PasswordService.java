package {{ cookiecutter.basePackage }}.biz.auth.password;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import {{ cookiecutter.basePackage }}.biz.auth.password.history.PasswordHistoryService;
import {{ cookiecutter.basePackage }}.biz.auth.user.User;
import {{ cookiecutter.basePackage }}.biz.auth.user.UserService;
import {{ cookiecutter.basePackage }}.biz.sys.setting.SettingService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 密码服务实现类
 */
@Service
@RequiredArgsConstructor
public class PasswordService {

    final UserService userService;
    final SettingService setting;
    final PasswordHistoryService passwordHistoryService;
    final PasswordEncoder passwordEncoder;

    /**
     * 修改密码
     *
     * @param userId 用户ID
     * @param newPwd 加密过后的新密码
     * @param policy 密码修改策略
     * @return true: 修改成功; false: 修改失败
     */
    public boolean change(Integer userId, String newPwd, ChangePasswordEnum policy) {
        User user = userService.findById(userId);

        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getPwd, passwordEncoder.encode(newPwd.trim()));
        updateWrapper.set(User::getPwdChangeTime, LocalDateTime.now());
        // 只有用户主动修改密码，页面才不会提示修改初始密码, 才会解除密码过期限制
        if (user.getMustChangePwd() && "user".equals(policy.getEditedBy())) {
            updateWrapper.set(User::getMustChangePwd, 0);
            updateWrapper.set(User::getExpireTime, null);
        }
        updateWrapper.eq(User::getId, userId);

        boolean success = userService.update(updateWrapper);
        if (success && setting.get("pwd.enable-history").isReal()) {
            // 记录密码修改日志
            passwordHistoryService.insert(user, newPwd, policy);
        }
        return success;
    }

    /**
     * 校验明文密码是否与加密后的密码匹配
     *
     * @param raw     明文密码
     * @param encoded 加密后的密码
     * @return true: 匹配; false: 不匹配
     */
    public boolean isMatch(String raw, String encoded) {
        if (StringUtils.isBlank(raw) || StringUtils.isBlank(encoded)) {
            return false;
        }

        return passwordEncoder.matches(raw, encoded);
    }
}
