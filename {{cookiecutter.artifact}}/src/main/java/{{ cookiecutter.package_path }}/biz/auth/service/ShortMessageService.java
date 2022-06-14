package {{ cookiecutter.basePackage }}.biz.auth.service;


public interface ShortMessageService {

    boolean send(String mobile, String code);
}
