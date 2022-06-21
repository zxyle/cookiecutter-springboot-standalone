package {{ cookiecutter.basePackage }}.biz.sample.controller;

import {{ cookiecutter.basePackage }}.biz.sample.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Post请求示例
 */
@Controller
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
        System.out.println("上传过来的json数据是: " + user);
        return user;
    }
}
