package {{ cookiecutter.basePackage }}.biz.sys.service.impl;

import cn.hutool.http.HttpRequest;
import {{ cookiecutter.basePackage }}.biz.sys.service.MonitoringAlarmService;
import {{ cookiecutter.basePackage }}.common.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * 钉钉告警发送
 * <a href="https://open.dingtalk.com/document/robots/custom-robot-access">...</a>
 */
@Service
public class DingTalkAlarmServiceImpl implements MonitoringAlarmService {

    @Value("${dingtalk.access-token}")
    private String accessToken;

    @Override
    public boolean sendAlarm(String msg) {
        String content = "{\"msgtype\": \"text\",\"text\": {\"content\":" + "\"" + msg + "\"}}";
        String result = HttpRequest.post("https://oapi.dingtalk.com/robot/send?access_token=" + accessToken)
                .body(content)
                .timeout(2000)
                .execute()
                .body();
        DingTalkResponse response = JacksonUtil.deserialize(result, DingTalkResponse.class);
        return response != null && response.getErrcode()== 0 && response.getErrmsg().equals("ok");

    }
}
