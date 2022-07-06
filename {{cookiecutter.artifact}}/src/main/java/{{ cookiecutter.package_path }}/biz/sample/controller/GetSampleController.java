package {{ cookiecutter.basePackage }}.biz.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Get请求示例
 */
@Controller
public class GetSampleController {

    /**
     * RequestMapping方式的GET请求
     */
    @ResponseBody
    @RequestMapping(value = "/get1", method = RequestMethod.GET)
    public String get1() {
        return "这是一个RequestMapping方式的GET请求.";
    }

    /**
     * GetMapping方式的GET请求
     */
    @ResponseBody
    @GetMapping("/get2")
    public String get2() {
        return "这是一个GetMapping方式的GET请求.";
    }


    /**
     * 获取url上的参数（/get3?name=Tony）
     *
     * @param name 姓名
     */
    @ResponseBody
    @GetMapping("/get3")
    public String get3(@RequestParam(name = "name", required = false) String name) {
        return "Hello " + (name == null ? "陌生人" : name);
    }

    /**
     * 获取url路径参数（/get4/100/Tony）
     *
     * @param name 姓名
     * @param id   用户ID
     */
    @ResponseBody
    @GetMapping("/get4/{id}/{name}")
    public String get4(@PathVariable("name") String name, @PathVariable("id") Integer id) {
        return "Hello " + name + ", your id is " + id;
    }
}
