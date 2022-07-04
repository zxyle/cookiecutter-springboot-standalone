package {{ cookiecutter.basePackage }};

import {{ cookiecutter.basePackage }}.common.constant.RegexConsts;
import org.junit.jupiter.api.Test;

public class TestRegex {

    @Test
    public void testQQ() {
        String qq = "128123413";
        System.out.println(qq.matches(RegexConsts.REGEX_QQ));
    }

    @Test
    public void testMobile() {
        String mobile = "18688888888";
        System.out.println(mobile.matches(RegexConsts.REGEX_MOBILE));
    }

    @Test
    public void testIdCard() {
        String idCard = "420102199010101111";
        System.out.println(idCard.matches(RegexConsts.REGEX_IDCARD));
    }

    @Test
    public void testChinese() {
        String chinese = "中文";
        System.out.println(chinese.matches(RegexConsts.REGEX_CHINESE));
    }


}
