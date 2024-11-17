package {{ cookiecutter.basePackage }};

import {{ cookiecutter.basePackage }}.config.security.WildcardPermission;
import org.junit.jupiter.api.Test;
import org.wildfly.common.Assert;

import java.util.Arrays;

class TestWildcardPermission {

    @Test
    void test01() {
        WildcardPermission wildcard = new WildcardPermission();
        Assert.assertTrue(wildcard.isPermit("sys:group:delete", Arrays.asList(":")));
        Assert.assertTrue(wildcard.isPermit("sys:group:delete", Arrays.asList("::")));
        Assert.assertTrue(wildcard.isPermit("sys:group:delete", Arrays.asList("*")));
        Assert.assertTrue(wildcard.isPermit("sys:group:delete", Arrays.asList("*:*")));
        Assert.assertTrue(wildcard.isPermit("sys:group:delete", Arrays.asList("*:*:*")));
        Assert.assertTrue(wildcard.isPermit("sys:group:delete", Arrays.asList("sys:*")));
        Assert.assertTrue(wildcard.isPermit("sys:group:delete", Arrays.asList("sys:*:*")));
        Assert.assertTrue(wildcard.isPermit("sys:group:delete", Arrays.asList("sys:group")));
        Assert.assertTrue(wildcard.isPermit("sys:group:delete", Arrays.asList("sys:group:")));
        Assert.assertTrue(wildcard.isPermit("sys:group:delete", Arrays.asList("sys:group:*")));
        Assert.assertTrue(wildcard.isPermit("sys:group:delete", Arrays.asList("sys::")));
        Assert.assertTrue(wildcard.isPermit("sys:group:delete", Arrays.asList("sys:")));
        Assert.assertTrue(wildcard.isPermit("sys:group:delete", Arrays.asList("sys")));
        Assert.assertTrue(wildcard.isPermit("sys:group:delete", Arrays.asList("sys:group:delete")));
        Assert.assertTrue(wildcard.isPermit("sys:group:delete", Arrays.asList("*:group")));
        Assert.assertTrue(wildcard.isPermit("sys:group:delete", Arrays.asList("*:group:")));
        Assert.assertTrue(wildcard.isPermit("sys:group:delete", Arrays.asList("*:group:*")));
        Assert.assertTrue(wildcard.isPermit("sys:group:delete", Arrays.asList("*:GROUP:*")));
        Assert.assertTrue(wildcard.isPermit("sys:group:delete", Arrays.asList("*:*:delete")));
        Assert.assertTrue(wildcard.isPermit("sys:group:delete", Arrays.asList("::delete")));
        Assert.assertTrue(wildcard.isPermit("sys:group:delete", Arrays.asList("sys::delete")));

        Assert.assertFalse(wildcard.isPermit("sys:group:delete", Arrays.asList("auTh:GROUP:*")));
        Assert.assertFalse(wildcard.isPermit("sys:group:delete", Arrays.asList("sys:role:*")));
        Assert.assertFalse(wildcard.isPermit("sys:group:delete", Arrays.asList()));
        Assert.assertFalse(wildcard.isPermit(null, Arrays.asList()));
        Assert.assertFalse(wildcard.isPermit(null, null));
        Assert.assertFalse(wildcard.isPermit("", Arrays.asList()));
        Assert.assertFalse(wildcard.isPermit("", null));
        Assert.assertFalse(wildcard.isPermit("sys:group:delete", Arrays.asList("sys:group:add")));
        Assert.assertFalse(wildcard.isPermit("sys:group:delete", Arrays.asList("SYS:GROUP:ADD")));
        Assert.assertFalse(wildcard.isPermit("sys:group:delete", Arrays.asList("*:*:add")));
        Assert.assertFalse(wildcard.isPermit("sys:group:delete", Arrays.asList("*:*:ADD")));
        Assert.assertFalse(wildcard.isPermit("sys:group:delete", Arrays.asList("")));
    }


    @Test
    void test02() {
        WildcardPermission wildcard = new WildcardPermission(4);
        Assert.assertTrue(wildcard.isPermit("manager:sys:group:delete", Arrays.asList("*")));
        Assert.assertTrue(wildcard.isPermit("manager:sys:group:delete", Arrays.asList(":")));
        Assert.assertTrue(wildcard.isPermit("manager:sys:group:delete", Arrays.asList("::")));
        Assert.assertTrue(wildcard.isPermit("manager:sys:group:delete", Arrays.asList(":::")));
        Assert.assertFalse(wildcard.isPermit("manager:sys:group:delete", Arrays.asList("employee:sys:group:delete")));
        Assert.assertFalse(wildcard.isPermit("manager:sys:group:delete", Arrays.asList("manager:auth:group:delete")));
        Assert.assertFalse(wildcard.isPermit("manager:sys:group:delete", Arrays.asList("manager:sys:role:delete")));
        // Assert.assertFalse(wildcard.isPermit("manager:sys:group", Arrays.asList("manager:sys:group:add")));
    }

}
