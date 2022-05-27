package {{ cookiecutter.basePackage }}.biz.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import {{ cookiecutter.basePackage }}.biz.sys.entity.FriendlyUrl;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import {{ cookiecutter.basePackage }}.biz.sys.service.IFriendlyUrlService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 友链管理
 */
@RestController
@RequestMapping("/sys/friendly")
public class FriendlyUrlController {

    public static final int ENABLE = 1;

    IFriendlyUrlService thisService;

    public FriendlyUrlController(IFriendlyUrlService thisService) {
        this.thisService = thisService;
    }

    /**
     * 获取友链列表
     */
    @GetMapping("/urls")
    @Cacheable(cacheNames = "urlCache")
    public ApiResponse<List<FriendlyUrl>> list() {
        QueryWrapper<FriendlyUrl> wrapper = new QueryWrapper<>();
        wrapper.select("content", "url");
        wrapper.eq("status", ENABLE);
        wrapper.orderByAsc("sort");
        List<FriendlyUrl> urls = thisService.list(wrapper);
        return new ApiResponse<>(urls);
    }

}
