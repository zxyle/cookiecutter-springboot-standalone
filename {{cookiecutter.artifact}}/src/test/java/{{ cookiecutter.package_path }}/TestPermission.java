package {{ cookiecutter.basePackage }};

import {{ cookiecutter.basePackage }}.biz.auth.permission.PermissionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestPermission {

    @Autowired
    PermissionService permissionService;


    @Test
    void t1() {
        Integer userId = 10;
        // System.out.println(permissionService.getAllPermissions(userId));
        System.out.println(permissionService.getSecurityPermissions(userId));
    }
}
