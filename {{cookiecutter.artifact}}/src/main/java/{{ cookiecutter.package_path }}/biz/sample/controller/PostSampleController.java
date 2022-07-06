package {{ cookiecutter.basePackage }}.biz.sample.controller;

import {{ cookiecutter.basePackage }}.biz.sample.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Post请求示例
 */
@Controller
@Slf4j
public class PostSampleController {

    /**
     * RequestMapping方式的POST请求
     */
    @ResponseBody
    @RequestMapping(value = "/post1", method = RequestMethod.POST)
    public String post1() {
        return "这是一个RequestMapping方式的POST请求.";
    }


    /**
     * PostMapping方式的POST请求
     */
    @ResponseBody
    @PostMapping("/post2")
    public String post2() {
        return "这是一个PostMapping方式的POST请求.";
    }

    /**
     * form-data格式
     *
     * @param name 姓名
     */
    @ResponseBody
    @PostMapping("/post3")
    public String post3(@RequestParam("name") String name) {
        return "Hello " + name;
    }

    /**
     * JSON请求体
     *
     * @param user json请求体
     */
    @PostMapping("/post4")
    @ResponseBody
    public User post4(@RequestBody User user) {
        log.info("json请求体: {}", user);
        return user;
    }
}
