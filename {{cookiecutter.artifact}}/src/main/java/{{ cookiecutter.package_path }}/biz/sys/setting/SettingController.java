package {{ cookiecutter.basePackage }}.biz.sys.setting;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.validation.Valid;

/**
 * 系统设置管理
 */
@RestController
@RequestMapping("/sys")
@RequiredArgsConstructor
public class SettingController {

    final SettingService thisService;

    /**
     * 系统设置分页查询
     */
    @LogOperation(name = "系统设置分页查询", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:setting:list')")
    @GetMapping("/settings")
    public R<Page<Setting>> page(@Valid PaginationRequest req) {
        Page<Setting> page = thisService.page(req.getPage());
        return R.ok(page);
    }

    /**
     * 新增系统设置
     */
    @LogOperation(name = "新增系统设置", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:setting:add')")
    @PostMapping("/settings")
    public R<Setting> add(@Valid @RequestBody Setting entity) {
        boolean success = thisService.save(entity);
        return success ? R.ok(entity) : R.fail("新增系统设置失败");
    }

    /**
     * 按ID查询系统设置
     */
    @LogOperation(name = "按ID查询系统设置", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:setting:get')")
    @GetMapping("/settings/{id}")
    public R<Setting> get(@PathVariable Integer id) {
        Setting entity = thisService.findById(id);
        return entity == null ? R.fail("设置不存在") : R.ok(entity);
    }

    /**
     * 按ID更新系统设置
     */
    @LogOperation(name = "按ID更新系统设置", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:setting:update')")
    @PutMapping("/settings/{id}")
    public R<Void> update(@Valid @RequestBody Setting entity, @PathVariable Integer id) {
        Setting setting = thisService.findById(id);
        if (setting == null) {
            return R.fail("设置不存在");
        }

        entity.setId(id);
        String optionValue = entity.getOptionValue();
        Item item = thisService.update(setting.getOptionLabel(), optionValue);
        return item != null ? R.ok("更新系统设置成功") : R.fail("更新系统设置失败");
    }

    /**
     * 按ID删除系统设置
     */
    @LogOperation(name = "按ID删除系统设置", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:setting:delete')")
    @DeleteMapping("/settings/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        Setting setting = thisService.findById(id);
        if (setting == null) {
            return R.fail("设置不存在");
        }

        boolean deleted = thisService.deleteById(setting.getOptionLabel(), id);
        return deleted ? R.ok("删除系统设置成功") : R.fail("删除系统设置失败");
    }

    /**
     * 恢复默认设置
     *
     * @param id          设置ID (二选一)
     * @param optionLabel 设置名称 (二选一)
     */
    @LogOperation(name = "恢复默认设置", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:setting:update')")
    @PostMapping("/settings/restore")
    public R<Void> restore(Integer id, String optionLabel) {
        Setting setting = null;
        if (id != null) {
            setting = thisService.findById(id);
        }

        if (StringUtils.isNotBlank(optionLabel)) {
            LambdaQueryWrapper<Setting> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(Setting::getOptionLabel, Setting::getDefaultValue);
            wrapper.eq(Setting::getOptionLabel, optionLabel);
            setting = thisService.getOne(wrapper);
        }

        if (setting == null) {
            return R.fail("设置不存在");
        }

        Item item = thisService.update(setting.getOptionLabel(), setting.getDefaultValue());
        return item != null ? R.ok("恢复默认设置成功") : R.fail("恢复默认设置失败");
    }

}
