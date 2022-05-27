package {{ cookiecutter.basePackage }}.biz.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import {{ cookiecutter.basePackage }}.biz.sys.entity.Blacklist;
import {{ cookiecutter.basePackage }}.biz.sys.service.IIpBlacklistService;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * IP黑名单管理
 */
@RestController
@RequestMapping("/sys")
public class IpBlacklistController {

    IIpBlacklistService thisService;

    public IpBlacklistController(IIpBlacklistService thisService) {
        this.thisService = thisService;
    }

    /**
     * 列表查询
     */
    @GetMapping("/blacklists")
    public ApiResponse<List<Blacklist>> list() {
        QueryWrapper<Blacklist> wrapper = new QueryWrapper<>();
        List<Blacklist> items = thisService.list(wrapper);
        return new ApiResponse<>(items);
    }

}
