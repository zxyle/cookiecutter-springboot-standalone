package {{ cookiecutter.basePackage }}.biz.auth.service;

public interface LoginService {

    /**
     * 退出登录
     */
    boolean logout();
}
