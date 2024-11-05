package {{ cookiecutter.basePackage }}.biz.sys.log;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 操作日志 服务实现类
 */
@Slf4j
@Service
public class OperateLogService extends ServiceImpl<OperateLogMapper, OperateLog> {

    /**
     * 异步保存操作日志
     */
    @Async("applicationTaskExecutor")
    public void saveLog(OperateLog operateLog) {
        this.save(operateLog);
    }
}
