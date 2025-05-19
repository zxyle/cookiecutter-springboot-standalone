package {{ cookiecutter.basePackage }}.common.delay;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 延时消息事件
 *
 * @author Xiang Zheng
 * @version v2
 */
@Getter
public class DelayMessageEvent extends ApplicationEvent {

    /**
     * 延时消息队列名称
     */
    private final String queue;

    /**
     * 延时消息内容
     */
    private final String message;

    public DelayMessageEvent(Object source, String queue, String message) {
        super(source);
        this.queue = queue;
        this.message = message;
    }
}