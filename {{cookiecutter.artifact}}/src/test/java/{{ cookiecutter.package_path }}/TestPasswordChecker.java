package {{ cookiecutter.basePackage }};

import cn.hutool.core.lang.Assert;
import {{ cookiecutter.basePackage }}.biz.auth.password.PasswordChecker;
import org.junit.jupiter.api.Test;

class TestPasswordChecker {

    @Test
    void testPwd() {
        String pwd = "128123413";
        Assert.equals(2, PasswordChecker.checkPasswordComplexity(pwd));
        pwd = "Password123!";
        Assert.equals(5, PasswordChecker.checkPasswordComplexity(pwd));
    }
}
