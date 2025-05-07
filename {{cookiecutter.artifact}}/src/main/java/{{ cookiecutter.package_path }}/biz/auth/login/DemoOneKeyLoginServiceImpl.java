package {{ cookiecutter.basePackage }}.biz.auth.login;

import org.springframework.stereotype.Service;

/**
 * 一键登录服务伪实现
 **/
@Service
public class DemoOneKeyLoginServiceImpl implements OneKeyLoginService {

    @Override
    public boolean verify(String token, String phone) {
        return false;
    }

    @Override
    public String getPhone(String token) {
        return "";
    }
}
