package {{ cookiecutter.basePackage }}.biz.sys.log;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * 日志清理定时任务
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class CleanLogTask {

    // 默认清理一年前的日志
    private static final int DEFAULT_SAVE_DAYS = 365;

    final LoginLogService loginLogService;
    final OperateLogService operateLogService;

    /**
     * 每天3点清理登录日志
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void cleanLoginLog() {
        // delete from sys_login_log where login_time < '2020-01-01 00:00:00'
        LocalDateTime startTime = LocalDateTime.now().minusDays(DEFAULT_SAVE_DAYS);
        LambdaQueryWrapper<LoginLog> wrapper = Wrappers.<LoginLog>lambdaQuery()
                .lt(LoginLog::getLoginTime, startTime);

        if (loginLogService.count(wrapper) == 0) {
            log.info("没有需要清理{}之前的登录日志", startTime);
            return;
        }

        boolean removed = loginLogService.remove(wrapper);
        log.info("清理{}之前的登录日志 {}", startTime, removed ? "成功" : "失败");
    }

    /**
     * 每天4点清理操作日志
     */
    @Scheduled(cron = "0 0 4 * * ?")
    public void cleanOperateLog() {
        // delete from sys_operate_log where operate_time < '2020-01-01 00:00:00'
        LocalDateTime startTime = LocalDateTime.now().minusDays(DEFAULT_SAVE_DAYS);
        LambdaQueryWrapper<OperateLog> wrapper = Wrappers.<OperateLog>lambdaQuery()
                .lt(OperateLog::getOperateTime, startTime);

        if (operateLogService.count(wrapper) == 0) {
            log.info("没有需要清理{}之前的操作日志", startTime);
            return;
        }

        boolean removed = operateLogService.remove(wrapper);
        log.info("清理{}之前的操作日志 {}", startTime, removed ? "成功" : "失败");
    }
}
