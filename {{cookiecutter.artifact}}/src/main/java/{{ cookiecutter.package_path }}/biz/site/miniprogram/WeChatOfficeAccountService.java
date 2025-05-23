package {{ cookiecutter.basePackage }}.biz.site.miniprogram;

import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 微信公众号服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WeChatOfficeAccountService {

    final StringRedisTemplate stringRedisTemplate;
    final ObjectMapper objectMapper;

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.appSecret}")
    private String appSecret;

    // 获取access_token， 与小程序获取方法相同
    // https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html


    /**
     * 发送模板消息
     */
    // https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Template_Message_Interface.html#%E5%8F%91%E9%80%81%E6%A8%A1%E6%9D%BF%E6%B6%88%E6%81%AF
    public void sendTemplateMessage(String openid, String templateId, Map<String, String> variables) {
        String accessToken = "";
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;
        TemplateMessage message = new TemplateMessage();
        message.setTouser(openid);
        message.setTemplateId(templateId);
        message.setData(
                variables.entrySet().stream().collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> Collections.singletonMap("value", entry.getValue())
                        )
                )
        );

        try {
            String json = objectMapper.writeValueAsString(message);
            String response = HttpUtil.post(url, json);
            log.info("sendTemplateMessage response: {}", response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
