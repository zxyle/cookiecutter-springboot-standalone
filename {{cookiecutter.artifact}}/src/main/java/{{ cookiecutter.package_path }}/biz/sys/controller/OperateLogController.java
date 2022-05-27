package {{ cookiecutter.basePackage }}.biz.sys.controller;

import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 操作日志
 */
@RestController
@RequestMapping("/sys/log")
public class OperateLogController {

    /**
     * 获取操作日志列表
     *
     */
    @PostMapping("/list")
    public void list(@Valid @RequestBody PaginationRequest request) {

    }
}
