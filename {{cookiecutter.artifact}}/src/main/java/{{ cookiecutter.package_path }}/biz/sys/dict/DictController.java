package {{ cookiecutter.basePackage }}.biz.sys.dict;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典管理
 */
@RestController
@RequestMapping("/sys")
@RequiredArgsConstructor
public class DictController {

    final DictService thisService;
    final DictMapper thisMapper;

    /**
     * 新增字典条目
     */
    @LogOperation(name = "新增字典条目", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:dict:add')")
    @PostMapping("/dicts")
    public R<Dict> add(@Valid @RequestBody AddDictRequest req) {
        Dict dict = new Dict();
        BeanUtils.copyProperties(req, dict);
        // 如果没有传排序字段，则自动排序
        if (req.getDictSort() == null) {
            dict.setDictSort(thisMapper.findMaxSortNum(req.getDictType()) + 1);
        }
        Dict result = thisService.insert(dict);
        return result != null ? R.ok(result) : R.fail("新增字典条目失败");
    }

    /**
     * 按字典类型查询条目
     */
    @GetMapping("/dicts/dictType")
    public R<Map<String, List<Dict>>> list(@Valid MultiDictTypeRequest req) {
        Map<String, List<Dict>> map = new HashMap<>(req.getTypes().size());
        for (String type : req.getTypes()) {
            List<Dict> dicts = thisService.findDictsByType(type.trim());
            map.put(type, dicts);
        }
        return R.ok(map);
    }

    /**
     * 查询所有字典类型
     *
     * @param keyword 模糊查询关键字
     */
    @GetMapping("/dicts/dictTypes")
    public R<List<Dict>> allTypes(String keyword) {
        LambdaQueryWrapper<Dict> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Dict::getName, Dict::getDictType);
        if (StringUtils.isNotBlank(keyword)) {
            wrapper.and(i -> i.like(Dict::getName, keyword)
                    .or().like(Dict::getDictType, keyword));
        }
        wrapper.groupBy(Dict::getName, Dict::getDictType);
        List<Dict> dicts = thisService.list(wrapper);
        return R.ok(dicts);
    }

    /**
     * 删除字典条目
     */
    @LogOperation(name = "删除字典条目", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:dict:delete')")
    @DeleteMapping("/dicts/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        Dict result = thisService.getById(id);
        if (result == null) {
            return R.fail("删除失败，字典不存在");
        }

        boolean success = thisService.deleteDict(result.getDictType(), id);
        return success ? R.ok("删除字典成功") : R.fail("删除字典条目失败");
    }

    /**
     * 更新字典条目
     */
    @LogOperation(name = "更新字典条目", biz = "sys")
    @PutMapping("/dicts/{id}")
    public R<Void> update(@Valid @RequestBody UpdateDictRequest req, @PathVariable Integer id) {
        Dict result = thisService.getById(id);
        if (result == null) {
            return R.fail("更新失败，字典条目不存在");
        }

        Dict dict = new Dict();
        BeanUtils.copyProperties(req, dict);
        dict.setId(id);
        dict.setDictType(result.getDictType());

        boolean success = thisService.updateDict(dict);
        return success ? R.ok("更新字典条目成功") : R.fail("更新字典条目失败");
    }

    /**
     * 删除字典类型
     *
     * @param dictType 字典类型
     */
    @LogOperation(name = "删除字典类型", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:dict:delete')")
    @DeleteMapping("/dicts/dictType/{dictType}")
    public R<Void> deleteByDictType(@PathVariable String dictType) {
        boolean success = thisService.deleteByDictType(dictType);
        return success ? R.ok("删除字典类型成功") : R.fail("删除字典类型失败");
    }

}
