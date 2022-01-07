package {{ cookiecutter.basePackage }};

import {{ cookiecutter.basePackage }}.biz.sample.entity.User;
import {{ cookiecutter.basePackage }}.biz.sample.mapper.UserMapper;
import {{ cookiecutter.basePackage }}.common.enums.SexEnum;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class {{ cookiecutter.app_name }}ApplicationTests {

  @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    void testInsert() {
        User user = new User();
        user.setUsername("AA");
        user.setAge(20);
        user.setEmail("1111111@qq.com");
        user.setSex(SexEnum.MAN);

        int result = userMapper.insert(user);
        System.out.println(result); // 受影响的行数
        System.out.println(user); // 看到id自动填充
        System.out.println(user.getId());
    }

    @Test
    void testUpdate() {
        User user = new User();
        user.setId(1374544255980167170L);
        user.setUsername("TT");
        user.setAge(1999);
        user.setEmail("10001@qq.com");

        int result = userMapper.updateById(user);
        System.out.println(result); // 受影响的行数
        System.out.println(user); // 看到id自动填充
    }

    // 测试乐观锁
    @Test
    void testOptimisticLocker() {
        User user = userMapper.selectById(2L);
        System.out.println(user);
        user.setUsername("BB");
        user.setEmail("110@qq.com");
        userMapper.updateById(user);

    }

    @Test
    void testPage() {
        // 参数1: 当前页 参数2: 页面大小
        Page<User> page = new Page<>(2, 5);

        userMapper.selectPage(page, null);
        page.getRecords().forEach(System.out::println);
        System.out.println("总条数:" + page.getTotal());
    }


    // 逻辑删除
    @Test
    void testDelete() {
        userMapper.deleteById(1L);
    }

}
