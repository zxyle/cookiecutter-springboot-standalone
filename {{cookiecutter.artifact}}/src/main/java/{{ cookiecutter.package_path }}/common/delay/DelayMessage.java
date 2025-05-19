package {{ cookiecutter.basePackage }}.common.delay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 延时消息封装类
 *
 * @author Xiang Zheng
 * @version v2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DelayMessage {

    /**
     * 消息内容
     */
    private String content;

    /**
     * 重试次数
     */
    private int retryCount;
}