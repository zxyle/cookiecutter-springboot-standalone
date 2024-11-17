package {{ cookiecutter.basePackage }};

import {{ cookiecutter.basePackage }}.common.constant.RegexConst;
import org.junit.jupiter.api.Test;
import org.wildfly.common.Assert;

/**
 * 正则表达式单元测试
 */
class TestRegex {

    @Test
    void testQQ() {
        String qq = "1281234139";
        Assert.assertTrue(qq.matches(RegexConst.REGEX_QQ));
    }

    @Test
    void testMobile() {
        Assert.assertTrue("18688888888".matches(RegexConst.REGEX_MOBILE));
        Assert.assertTrue("17176673457".matches(RegexConst.REGEX_MOBILE));
        Assert.assertTrue("16257198265".matches(RegexConst.REGEX_MOBILE));
    }

    @Test
    void testIdNum() {
        String idCard = "420102199010101111";
        Assert.assertTrue(idCard.matches(RegexConst.REGEX_ID_NUM));
    }

    @Test
    void testChinese() {
        String chinese = "中文";
        Assert.assertTrue(chinese.matches(RegexConst.REGEX_CHINESE));
        chinese = "中文123";
        Assert.assertFalse(chinese.matches(RegexConst.REGEX_CHINESE));
    }

    @Test
    void testDate() {
        Assert.assertTrue("2021-01-01".matches(RegexConst.REGEX_DATE));
        Assert.assertFalse("2021-13-01".matches(RegexConst.REGEX_DATE));
        Assert.assertFalse("2021-12-32".matches(RegexConst.REGEX_DATE));
        Assert.assertFalse("0000-12-31".matches(RegexConst.REGEX_DATE));
        Assert.assertFalse("3333-12-31".matches(RegexConst.REGEX_DATE));
    }

    @Test
    void testTime() {
        Assert.assertTrue("12:00:00".matches(RegexConst.REGEX_TIME));
        Assert.assertFalse("25:00:00".matches(RegexConst.REGEX_TIME));
        Assert.assertFalse("12:60:00".matches(RegexConst.REGEX_TIME));
        Assert.assertFalse("12:00:60".matches(RegexConst.REGEX_TIME));
    }

    @Test
    void testDateTime() {
        Assert.assertTrue("2021-01-01 12:00:00".matches(RegexConst.REGEX_DATETIME));
        Assert.assertFalse("2021-13-01 12:00:00".matches(RegexConst.REGEX_DATETIME));
        Assert.assertFalse("2021-12-32 12:00:00".matches(RegexConst.REGEX_DATETIME));
        Assert.assertFalse("2021-01-01 25:00:00".matches(RegexConst.REGEX_DATETIME));
        Assert.assertFalse("2021-01-01 12:60:00".matches(RegexConst.REGEX_DATETIME));
        Assert.assertFalse("2021-01-01 12:00:60".matches(RegexConst.REGEX_DATETIME));
    }

}
