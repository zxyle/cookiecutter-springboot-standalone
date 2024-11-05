package {{ cookiecutter.basePackage }}.biz.sys.service;

/**
 * 告警信息发送
 */
public interface MonitoringAlarmService {

    /**
     * 发送告警消息
     *
     * @param content 告警内容
     * @return 是否发送成功
     */
    boolean sendAlarm(String content);
}
