package {{ cookiecutter.basePackage }}.biz.auth.login;

/**
 *
 */
// aliyun https://help.aliyun.com/zh/pnvs/product-overview/what-is-the-number-certification-services
// 极光  https://www.jiguang.cn/identify
// 网易云信 https://m.yunxin.163.com/quicklogin
// 个推 https://www.getui.com/verification
// 百度云 https://growing.baidu.com/pnvs.html
// 腾讯云 https://cloud.tencent.com/product/nvs
// uniapp https://unicloud.dcloud.net.cn/pages/uni-login/app-config
public interface OneKeyLoginService {

    /**
     * 本机号码校验
     *
     * @param token token
     * @param phone 用户输入的手机号
     * @return 是否通过
     */
    boolean verify(String token, String phone);

    /**
     * 获取手机号
     *
     * @param token token
     * @return 明文手机号
     */
    String getPhone(String token);

}
