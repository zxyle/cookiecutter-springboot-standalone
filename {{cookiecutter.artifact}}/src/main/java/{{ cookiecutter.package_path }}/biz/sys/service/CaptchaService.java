package {{ cookiecutter.basePackage }}.biz.sys.service;

public interface CaptchaService {

    CaptchaPair generate();
}
