package {{ cookiecutter.basePackage }}.biz.sys.controller;

import {{ cookiecutter.basePackage }}.biz.sys.entity.Param;
import {{ cookiecutter.basePackage }}.biz.sys.service.IParamService;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统信息
 */
@RestController
@Slf4j
@RequestMapping("/system")
public class SystemController {

    @Autowired
    IParamService paramService;

    /**
     * 系统信息
     */
    @GetMapping("/info")
    public ApiResponse<List<Param>> info() {
        // 系统信息、ICP备案、版本、联系邮箱
        List<Param> list = paramService.list();
        return new ApiResponse<>(list);
    }

}
