package {{ cookiecutter.basePackage }}.biz.user.controller;

import org.springframework.web.bind.annotation.*;
import {{ cookiecutter.basePackage }}.biz.user.request.LoginRequest;
import {{ cookiecutter.basePackage }}.biz.user.response.LoginResponse;
import {{ cookiecutter.basePackage }}.base.response.ApiResponse;

import java.util.Date;

/**
 * 用户接口
 *
 * @author {{ cookiecutter.author_name }} on 2022/01/21.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 用户登陆
     *
     * @param user 用户信息
     * @apiNote 通过用户名和密码进行用户登录
     */
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse data = new LoginResponse();
        data.setAge(50);
        data.setBirthday(new Date());
        data.setGender("男");
        data.setUsername("张三");
        return new ApiResponse<>(data);
    }

}
