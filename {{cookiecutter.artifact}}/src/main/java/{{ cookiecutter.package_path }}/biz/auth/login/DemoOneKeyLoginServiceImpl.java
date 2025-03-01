package {{ cookiecutter.basePackage }}.biz.auth.login;

import org.springframework.stereotype.Service;

@Service
public class DemoOneKeyLoginServiceImpl implements OneKeyLoginService{
    @Override
    public boolean verify(String token, String phone) {
        return false;
    }

    @Override
    public String getPhone(String token) {
        return "";
    }
}
