package {{ cookiecutter.basePackage }}.biz.site.miniprogram;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import {{ cookiecutter.basePackage }}.common.util.JacksonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 微信小程序服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WeChatMiniProgramService {

    final StringRedisTemplate stringRedisTemplate;
    final ObjectMapper objectMapper;

    /**
     * 小程序 appId
     */
    @Value("${wechat.miniprogram.appId:wxxxx}")
    private String appId;

    /**
     * 小程序 appSecret
     */
    @Value("${wechat.miniprogram.appSecret:yyy}")
    private String appSecret;


    /**
     * 微信小程序登录,具体参考 <a href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-login/code2Session.html">...</a>
     *
     * @param jsCode 登录时获取的 code，可通过前端调用wx.login获取
     */
    public WechatLoginResponse login(String jsCode) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";

        Map<String, Object> params = new HashMap<>(4);
        params.put("appid", appId);
        params.put("secret", appSecret);
        params.put("js_code", jsCode);
        params.put("grant_type", "authorization_code");

        String body = HttpUtil.get(url, params);
        log.info(body);
        WechatLoginResponse response = JacksonUtil.deserialize(body, WechatLoginResponse.class);
        log.info("response: {}", response);
        return response;
    }

    /**
     * 解密用户敏感数据
     *
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param sessionKey    会话密钥
     * @param vi            加密算法的初始向量
     * @return 解密后的字符串数据
     * @throws Exception 异常
     */
    public String decryptData(String encryptedData, String sessionKey, String vi) throws Exception {
        byte[] encData = Base64.decode(encryptedData);
        byte[] iv = Base64.decode(vi);
        byte[] key = Base64.decode(sessionKey);
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        return new String(cipher.doFinal(encData), StandardCharsets.UTF_8);
    }

    /**
     * 获取接口调用凭据 <a href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/mp-access-token/getAccessToken.html">...</a>
     */
    public String getAccessToken() {
        String key = "wx:accessToken";
        String s = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(s)) {
            return s;
        }

        String url = "https://api.weixin.qq.com/cgi-bin/token";

        Map<String, Object> params = new HashMap<>(3);
        params.put("appid", appId);
        params.put("secret", appSecret);
        params.put("grant_type", "client_credential");
        String body = HttpUtil.get(url, params);
        log.info(body);
        AccessTokenResponse response = JacksonUtil.deserialize(body, AccessTokenResponse.class);
        if (response == null || StringUtils.isBlank(response.getAccessToken())) {
            return null;
        }

        // 将access_token 存到Redis中
        // {"access_token":"xxx","expires_in":7200}
        stringRedisTemplate.opsForValue().set(key, response.getAccessToken(), response.getExpiresIn(), TimeUnit.SECONDS);
        return response.getAccessToken();
    }

    /**
     * 获取用户信息
     *
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     */
    // https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-info/phone-number/getPhoneNumber.html
    // https://developers.weixin.qq.com/miniprogram/dev/api/open-api/user-info/wx.getUserProfile.html
    public WxUserInfo getUserInfo(String encryptedData, String sessionKey, String vi) {
        try {
            String decryptData = decryptData(encryptedData, sessionKey, vi);
            return JacksonUtil.deserialize(decryptData, WxUserInfo.class);
        } catch (Exception e) {
            log.error("decryptData error", e);
            return null;
        }
    }

    /**
     * 发送订阅消息
     *
     * @param openid 用户ID
     * @param templateId 模板ID
     * @param variables 模板变量
     * @param page       跳转页面路由
     */
    // https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/mp-message-management/subscribe-message/sendMessage.html
    public void sendTemplateMessage(String openid, String page, String templateId, Map<String, String> variables) {
        String accessToken = getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + accessToken;
        SubscribeMessage message = new SubscribeMessage();
        if (StringUtils.isNotBlank(page)) {
            message.setPage(page);
        }
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
            String response = doPostRequest(url, json);
            log.info("sendTemplateMessage response: {}", response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String doPostRequest(String url, String json) {
        try (HttpResponse response = HttpRequest.post(url)
                .header("Accept", "application/json")
                .body(json)
                .execute()){
            if (response.getStatus() != 200) {
                throw new RuntimeException("response status: " + response.getStatus());
            }

            return response.body();
        }
    }
}