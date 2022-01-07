package {{ cookiecutter.basePackage }}.biz.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// 处理get请求

@Controller
public class GetSampleController {
    // RequestMapping方式的GET请求
    // 访问http://127.0.0.1:8090/get1
    @ResponseBody
    @RequestMapping(value = "/get1", method = RequestMethod.GET)
    public String get1() {
        return "这是一个RequestMapping方式的GET请求.";
    }

    // GetMapping方式的GET请求
    // 访问http://127.0.0.1:8090/get2
    @ResponseBody
    @GetMapping("/get2")
    public String get2() {
        return "这是一个GetMapping方式的GET请求.";
    }

    // 获取url上的参数
    // 访问http://127.0.0.1:8090/get3?name=Tony
    @ResponseBody
    @GetMapping("/get3")
    public String get3(@RequestParam(name = "name", required = true) String name) {
        return "Hello " + name;
    }

    // 获取url路径
    // 访问http://127.0.0.1:8090/get4/100/Tony
    @ResponseBody
    @GetMapping("/get4/{id}/{name}")
    public String get4(@PathVariable("name") String name, @PathVariable("id") Integer id) {
        return "Hello " + name + ", your id is " + id;
    }
}