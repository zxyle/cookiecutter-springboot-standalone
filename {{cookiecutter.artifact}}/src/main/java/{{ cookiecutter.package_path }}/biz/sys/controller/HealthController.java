package {{ cookiecutter.basePackage }}.biz.sys.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统健康状态
 */
@RestController
public class HealthController {

    /**
     * 系统状态
     */
    @GetMapping("/status")
    public String status() {
        return "It worked!";
    }

    /**
     * 回声测试
     */
    @GetMapping("/ping")
    public Map<String, String> ping() {
        Map<String, String> map = new HashMap<>();
        map.put("ping", "pong");
        return map;
    }
}
