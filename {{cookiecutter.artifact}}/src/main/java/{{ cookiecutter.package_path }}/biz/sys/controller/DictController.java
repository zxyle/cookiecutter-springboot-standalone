package {{ cookiecutter.basePackage }}.biz.sys.controller;

import {{ cookiecutter.basePackage }}.biz.sys.entity.Dict;
import {{ cookiecutter.basePackage }}.biz.sys.request.DictRequest;
import {{ cookiecutter.basePackage }}.biz.sys.service.IDictService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 字典数据
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    private final IDictService dictService;

    Cache<String, List<Dict>> dictListCache;

    @Autowired
    public DictController(IDictService dictService, Cache<String, List<Dict>> dictListCache) {
        this.dictService = dictService;
        this.dictListCache = dictListCache;
    }

    /**
     * 字典分页
     * @param request
     * @return
     */
    @GetMapping("/pages")
    public ResponseEntity<Object> list(DictRequest request) {
        IPage<Dict> dictIPage = dictService.getPageList(request);
        return ResponseEntity.ok(dictIPage);
    }

    /**
     * 字典列表
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        List<Dict> dictList = dictListCache.getIfPresent("dictList");
        if (null != dictList) {
            return ResponseEntity.ok(dictList);
        }

        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.orderByAsc("dict_sort");

        dictList = dictService.list();
        dictListCache.put("dictList", dictList);

        return ResponseEntity.ok(dictList);
    }

    /**
     * 获取单个字典键值
     * @param dictType
     * @return
     */
    @GetMapping("/get/{dict_type}")
    public ResponseEntity<Object> getListByType(@NotEmpty @PathVariable("dict_type") String dictType) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_type", dictType);
        queryWrapper.orderByAsc("dict_sort");
        List<Dict> dictList = dictService.list(queryWrapper);
        return ResponseEntity.ok(dictList);
    }

}
