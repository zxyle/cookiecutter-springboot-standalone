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
    // 访问http://127.0.0.1:8090/post1
    @ResponseBody
    @RequestMapping(value = "/post1", method = RequestMethod.POST)
    public String post1() {
        return "这是一个RequestMapping方式的POST请求.";
    }

    // 访问http://127.0.0.1:8090/post2
    @ResponseBody
    @PostMapping("/post2")
    public String post2() {
        return "这是一个PostMapping方式的POST请求.";
    }

    // form-data格式
    @ResponseBody
    @PostMapping("/post3")
    public String post3(@RequestParam("name") String name) {
        return "Hello " + name;
    }

    // 接收json格式
    // POST http://127.0.0.1:8090/post4
    // json body {"name": "Tom", "id": 100}
    @PostMapping("/post4")
    @ResponseBody
    public User post4(@RequestBody User user) {
        System.out.println("上传过来的json数据是: " + user);
        return user;
    }
}
