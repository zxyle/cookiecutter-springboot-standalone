package {{ cookiecutter.basePackage }}.biz.site.miniprogram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * 微信公众号模板消息
 */
@Data
public class TemplateMessage {

    /**
     * 接收者openid
     */
    private String touser;

    /**
     * 模板ID
     */
    @JsonProperty("template_id")
    private String templateId;

    /**
     * 模板跳转链接（海外账号没有跳转能力）
     */
    private String url;

    /**
     * 跳小程序所需数据，不需跳小程序可不用传该数据
     */
    private Miniprogram miniprogram;

    /**
     * 防重入id。对于同一个openid + client_msg_id, 只发送一条消息,10分钟有效,超过10分钟不保证效果。若无防重入需求，可不填
     */
    @JsonProperty("client_msg_id")
    private String clientMsgId;

    /**
     * 模板数据
     */
    private Map<String, Map<String, String>> data;

    @Data
    public static class Miniprogram {

        /**
         * 所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系，暂不支持小游戏）
         */
        private String appid;

        /**
         * 所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar），要求该小程序已发布，暂不支持小游戏
         */
        private String pagepath;
    }

}
