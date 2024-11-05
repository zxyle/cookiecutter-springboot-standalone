package {{ cookiecutter.basePackage }}.biz.site.info;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统信息管理
 */
@RestController
@RequestMapping("/site")
@RequiredArgsConstructor
public class InfoController {

    final InfoService thisService;

    /**
     * 获取系统信息列表
     */
    @GetMapping("/infos")
    public R<Map<String, String>> list() {
        LambdaQueryWrapper<Info> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Info::getParamKey, Info::getParamValue);
        List<Info> params = thisService.list(wrapper);
        Map<String, String> map = new HashMap<>(params.size());
        params.forEach(info -> map.put(info.getParamKey(), info.getParamValue()));
        return R.ok(map);
    }

    /**
     * 新增系统信息
     */
    @LogOperation(name = "新增系统信息", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:info:add')")
    @PostMapping("/infos")
    public R<Info> add(@Valid @RequestBody Info entity) {
        Info info = thisService.insert(entity);
        return R.ok(info);
    }

    /**
     * 更新系统信息
     */
    @LogOperation(name = "按ID更新系统信息", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:info:update')")
    @PutMapping("/infos")
    public R<Info> update(@Valid @RequestBody Info entity) {
        Info info = thisService.putById(entity);
        return R.ok(info);
    }

    /**
     * 删除系统信息
     *
     * @param paramKey 键名
     */
    @LogOperation(name = "按ID删除系统信息", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:info:delete')")
    @DeleteMapping("/infos/{paramKey}")
    public R<Void> delete(@PathVariable String paramKey) {
        boolean success = thisService.deleteById(paramKey);
        return success ? R.ok("删除系统信息成功") : R.fail("删除系统信息失败");
    }

    /**
     * 用户协议 user-agreement, 隐私政策 privacy-policy, 合作协议 cooperation-agreement
     *
     * @param paramKey 键名
     */
    @GetMapping(value = "/infos/html/{paramKey}", produces = "text/html; charset=UTF-8")
    public String getUserAgreement(@PathVariable String paramKey) {
        Info one = thisService.getByKey(paramKey);
        if (one == null) {
            return "暂无内容";
        }

        return one.getParamValue();
    }

}
