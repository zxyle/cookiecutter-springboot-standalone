package {{ cookiecutter.basePackage }}.common.delay;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;


/**
 * 延时队列 消息处理示例
 *
 * @author Xiang Zheng
 * @version v2
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SendSmsActionListener {

    private static final String QUEUE = "sms";

    /**
     * 发送短信
     */
    @EventListener
    public void handleSmsEvent(DelayMessageEvent event) {
        String message = event.getMessage();
        String queue = event.getQueue();
        if (StringUtils.isBlank(queue) || !QUEUE.equals(queue)) {
            return;
        }

        log.info("接收到消息: {}", message);
        String[] split = message.split(":");
    }

}