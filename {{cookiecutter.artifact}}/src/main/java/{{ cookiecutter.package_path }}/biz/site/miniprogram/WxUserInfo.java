package {{ cookiecutter.basePackage }}.biz.site.miniprogram;

import lombok.Data;

/**
 * 小程序用户信息
 */
@Data
public class WxUserInfo {

    /**
     * 用户绑定的手机号（国外手机号会有区号）
     */
    private String phoneNumber;

    /**
     * 没有区号的手机号
     */
    private String purePhoneNumber;

    /**
     * 区号
     */
    private String countryCode;

    /**
     * 数据水印
     */
    private Watermark watermark;


    @Data
    public static class Watermark {

        /**
         * 小程序appid
         */
        private String appid;

        /**
         * 用户获取手机号操作的时间戳
         */
        private Long timestamp;
    }

}
