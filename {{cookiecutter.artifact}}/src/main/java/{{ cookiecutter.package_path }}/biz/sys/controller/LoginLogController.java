package {{ cookiecutter.basePackage }}.biz.sys.controller;

import org.springframework.web.bind.annotation.*;
import {{ cookiecutter.basePackage }}.biz.sys.mapper.LoginLogMapper;
import {{ cookiecutter.basePackage }}.biz.sys.service.ILoginLogService;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录日志
 */
@RestController
@RequestMapping("/sys/login")
public class LoginLogController {

    LoginLogMapper thisMapper;

    ILoginLogService thisService;

    public LoginLogController(LoginLogMapper thisMapper, ILoginLogService thisService) {
        this.thisMapper = thisMapper;
        this.thisService = thisService;
    }

    /**
     * 分页查询
     */
    @GetMapping("/logs")
    public void list() {
    }

}
