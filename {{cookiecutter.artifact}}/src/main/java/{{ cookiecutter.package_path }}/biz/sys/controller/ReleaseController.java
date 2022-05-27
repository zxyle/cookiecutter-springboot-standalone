package {{ cookiecutter.basePackage }}.biz.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import {{ cookiecutter.basePackage }}.biz.sys.entity.Release;
import {{ cookiecutter.basePackage }}.biz.sys.service.IReleaseService;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 发布版本管理
 */
@RestController
@RequestMapping("/sys")
public class ReleaseController {

    IReleaseService thisService;

    public ReleaseController(IReleaseService thisService) {
        this.thisService = thisService;
    }

    /**
     * 获取版本列表
     */
    @GetMapping("/releases")
    public ApiResponse<List<Release>> list() {
        QueryWrapper<Release> wrapper = new QueryWrapper<>();
        wrapper.select("version", "description");
        wrapper.orderByDesc("create_time");
        List<Release> releases = thisService.list(wrapper);
        return new ApiResponse<>(releases);
    }

}
