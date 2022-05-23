package {{ cookiecutter.basePackage }}.biz.sys.service;

/**
 * 告警信息发送
 */
public interface MonitoringAlarmService {

    // 发送告警消息
    boolean sendAlarm(String content);
}
