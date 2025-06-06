package {{ cookiecutter.basePackage }}.biz.auth.password.history;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {{ cookiecutter.basePackage }}.biz.auth.user.User;
import {{ cookiecutter.basePackage }}.biz.auth.password.ChangePasswordEnum;
import {{ cookiecutter.basePackage }}.biz.sys.setting.SettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 密码历史表 服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordHistoryService extends ServiceImpl<PasswordHistoryMapper, PasswordHistory> {

    final SettingService setting;

    /**
     * 记录密码历史
     *
     * @param user   用户
     * @param newPwd 新密码
     * @param policy 密码策略
     */
    @Async
    public void insert(User user, String newPwd, ChangePasswordEnum policy) {
        long currentCount = countHistory(user.getId()) + 1;
        int count = setting.get("pwd.history-count").getIntValue();
        if (count != 0 && currentCount > count) {
            removeHistory(user.getId(), currentCount - count);
        }

        PasswordHistory history = new PasswordHistory();
        history.setUserId(user.getId());
        history.setAfterPwd(newPwd);
        history.setBeforePwd(user.getPassword());
        history.setEditedBy(policy.getEditedBy());
        history.setKind(policy.getKind());
        save(history);
    }

    /**
     * 获取历史记录数量
     *
     * @param userId 用户ID
     */
    public Long countHistory(Integer userId) {
        LambdaQueryWrapper<PasswordHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PasswordHistory::getUserId, userId);
        return count(wrapper);
    }

    /**
     * 删除历史记录
     *
     * @param userId      用户ID
     * @param deleteCount 删除数量
     */
    public boolean removeHistory(Integer userId, Long deleteCount) {
        LambdaQueryWrapper<PasswordHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PasswordHistory::getUserId, userId);
        wrapper.orderByDesc(PasswordHistory::getCreateTime);
        wrapper.last(deleteCount != null, "limit " + deleteCount);
        return remove(wrapper);
    }

    /**
     * 清除密码历史
     *
     * @param userId 用户ID
     * @return true: 清除成功; false: 清除失败
     */
    public boolean clear(Integer userId) {
        return removeHistory(userId, null);
    }
}
