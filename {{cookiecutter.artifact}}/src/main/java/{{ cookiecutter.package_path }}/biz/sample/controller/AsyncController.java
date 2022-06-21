package {{ cookiecutter.basePackage }}.biz.sample.controller;

import {{ cookiecutter.basePackage }}.biz.sample.service.AsyncService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 异步任务示例
 */
@RestController
public class AsyncController {

    AsyncService asyncService;

    public AsyncController(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    /**
     * 任务处理
     */
    @GetMapping("/process")
    public String hello() {
        // 此处调用异步任务
        asyncService.process();
        return "success.";
    }

}
