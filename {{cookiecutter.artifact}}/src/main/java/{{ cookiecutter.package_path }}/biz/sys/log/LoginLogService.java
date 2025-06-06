package {{ cookiecutter.basePackage }}.biz.sys.log;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 登录日志 服务实现类
 */
@Service
public class LoginLogService extends ServiceImpl<LoginLogMapper, LoginLog> {

    /**
     * 异步保存登录日志
     *
     * @param loginLog 登录日志
     */
    @Async("applicationTaskExecutor")
    public void saveLogAsync(LoginLog loginLog) {
        if (loginLog == null) {
            return;
        }

        // 如果 ua 字段超过255个字符，截取前255个字符, 避免插入数据库失败
        if (StringUtils.isNotBlank(loginLog.getUa())) {
            loginLog.setUa(StringUtils.substring(loginLog.getUa(), 0, 255));
        }

        loginLog.setLoginTime(LocalDateTime.now());
        this.save(loginLog);
    }

}
