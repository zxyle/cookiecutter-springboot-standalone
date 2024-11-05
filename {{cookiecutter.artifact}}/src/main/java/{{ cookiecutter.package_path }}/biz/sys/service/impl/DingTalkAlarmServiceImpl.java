package {{ cookiecutter.basePackage }}.biz.sys.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import {{ cookiecutter.basePackage }}.biz.sys.setting.SettingService;
import {{ cookiecutter.basePackage }}.biz.sys.service.MonitoringAlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * 钉钉告警发送
 * <a href="https://open.dingtalk.com/document/robots/custom-robot-access">接口文档</a>
 */
@Service
@RequiredArgsConstructor
public class DingTalkAlarmServiceImpl implements MonitoringAlarmService {

    final SettingService setting;

    /**
     * 发送告警消息
     *
     * @param msg 告警内容
     * @return 是否发送成功
     */
    @Override
    public boolean sendAlarm(String msg) {
        String accessToken = setting.get("dingtalk.access-token").getStr();
        String content = "{\"msgtype\": \"text\",\"text\": {\"content\":" + "\"" + msg + "\"}}";
        String url = "https://oapi.dingtalk.com/robot/send?access_token=" + accessToken;
        try (HttpResponse res = HttpRequest.post(url)
                .body(content)
                .timeout(2000).execute()) {

            return res.getStatus() == 200 && res.body().contains("ok");
        }
    }
}
