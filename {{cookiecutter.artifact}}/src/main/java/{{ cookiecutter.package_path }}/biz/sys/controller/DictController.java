package {{ cookiecutter.basePackage }}.biz.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import {{ cookiecutter.basePackage }}.biz.sys.entity.Dict;
import {{ cookiecutter.basePackage }}.biz.sys.service.IDictService;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.common.response.PageVO;
import {{ cookiecutter.basePackage }}.common.util.PageRequestUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 字典管理
 */
@RestController
@RequestMapping("/sys")
public class DictController {

    IDictService thisService;

    public DictController(IDictService thisService) {
        this.thisService = thisService;
    }

    /**
     * 分页查询
     */
    @Cacheable(cacheNames = "dictCache")
    @GetMapping("/dicts")
    public ApiResponse<PageVO<Dict>> list(@Valid PaginationRequest request) {
        IPage<Dict> page = PageRequestUtil.checkForMp(request);
        IPage<Dict> list = thisService.page(page);
        return PageRequestUtil.extractFromMp(list);
    }


    /**
     * 新增字典
     */
    @Cacheable(cacheNames = "dictCache")
    @PostMapping("/dicts")
    public ApiResponse<Dict> add(@Valid @RequestBody Dict entity) {
        boolean success = thisService.save(entity);
        if (success) {
            return new ApiResponse<>(entity);
        }
        return new ApiResponse<>();
    }


    /**
     * 按ID查询字典
     */
    @Cacheable(cacheNames = "dictCache", key = "#id")
    @GetMapping("/dicts/{id}")
    public ApiResponse<Dict> get(@PathVariable Long id) {
        return new ApiResponse<>(thisService.getById(id));
    }

    /**
     * 按类型查询字典
     */
    @GetMapping("/dicts/dictType/{dictType}")
    public ApiResponse<List<Dict>> getByDictType(@PathVariable String dictType) {
        List<Dict> dicts = thisService.listAllDicts(dictType);
        return new ApiResponse<>(dicts);
    }


    /**
     * 按ID更新字典
     */
    @PutMapping("/dicts/{id}")
    public ApiResponse<Object> update(@Valid @RequestBody Dict entity) {
        boolean success = thisService.updateById(entity);
        if (success) {
            return new ApiResponse<>("更新成功");
        }
        return new ApiResponse<>("更新失败");
    }

    /**
     * 按ID删除字典
     */
    @DeleteMapping("/dicts/{id}")
    public ApiResponse<Object> delete(@PathVariable Long id) {
        boolean success = thisService.removeById(id);
        if (success) {
            return new ApiResponse<>("删除成功");
        }
        return new ApiResponse<>("删除失败");
    }

}
