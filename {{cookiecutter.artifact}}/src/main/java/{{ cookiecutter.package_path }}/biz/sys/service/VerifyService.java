package {{ cookiecutter.basePackage }}.biz.sys.service;

public interface VerifyService {

    /**
     * 校验验证码
     *
     * @param captchaId 验证码ID
     * @param captcha   验证码
     * @return 校验结果
     */
    boolean verify(String captcha, String captchaId);
}
