package {{ cookiecutter.basePackage }};

import {{ cookiecutter.basePackage }}.common.util.RateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 百分比计算工具类单元测试
 */
class TestRate {

    @Test
    void test01() {
        Assertions.assertEquals("100%", RateUtil.cal(1, 1));
        Assertions.assertEquals("20%", RateUtil.cal(4, 20));
    }
}
