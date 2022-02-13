package {{ cookiecutter.basePackage }}.biz.sys.controller;

import {{ cookiecutter.basePackage }}.biz.sys.entity.TDict;
import {{ cookiecutter.basePackage }}.biz.sys.request.DictRequest;
import {{ cookiecutter.basePackage }}.biz.sys.service.ITDictService;
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
public class TDictController {

    private final ITDictService dictService;

    Cache<String, List<TDict>> dictListCache;

    @Autowired
    public TDictController(ITDictService dictService, Cache<String, List<TDict>> dictListCache) {
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
        IPage<TDict> dictIPage = dictService.getPageList(request);
        return ResponseEntity.ok(dictIPage);
    }

    /**
     * 字典列表
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        List<TDict> dictList = dictListCache.getIfPresent("dictList");
        if (null != dictList) {
            return ResponseEntity.ok(dictList);
        }

        QueryWrapper<TDict> queryWrapper = new QueryWrapper<>();
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
        QueryWrapper<TDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_type", dictType);
        queryWrapper.orderByAsc("dict_sort");
        List<TDict> dictList = dictService.list(queryWrapper);
        return ResponseEntity.ok(dictList);
    }

}
