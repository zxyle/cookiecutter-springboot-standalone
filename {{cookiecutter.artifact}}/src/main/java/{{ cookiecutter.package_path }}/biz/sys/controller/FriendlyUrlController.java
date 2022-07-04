package {{ cookiecutter.basePackage }}.biz.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import {{ cookiecutter.basePackage }}.biz.sys.entity.FriendlyUrl;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import {{ cookiecutter.basePackage }}.biz.sys.service.IFriendlyUrlService;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    /**
     * 添加友链
     */
    @PostMapping("/urls")
    public ApiResponse<FriendlyUrl> add(@Valid @RequestBody FriendlyUrl entity) {
        thisService.save(entity);
        return new ApiResponse<>(entity);
    }

    /**
     * 删除友链
     *
     * @param id 友链ID
     */
    @DeleteMapping("/urls/{id}")
    public ApiResponse<FriendlyUrl> delete(@PathVariable("id") Long id) {
        FriendlyUrl entity = thisService.getById(id);
        if (entity == null) {
            return new ApiResponse<>("友链不存在", false);
        }
        thisService.removeById(id);
        return new ApiResponse<>("删除成功", true);
    }

    /**
     * 更新友链
     *
     * @param id     友链ID
     * @param entity 友链实体
     * @return 友链实体
     */
    @PutMapping("/urls/{id}")
    public ApiResponse<FriendlyUrl> update(@PathVariable("id") Long id, @Valid @RequestBody FriendlyUrl entity) {
        FriendlyUrl oldEntity = thisService.getById(id);
        if (oldEntity == null) {
            return new ApiResponse<>("友链不存在", false);
        }
        thisService.updateById(entity);
        return new ApiResponse<>(entity);
    }


}
