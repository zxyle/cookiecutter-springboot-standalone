package {{ cookiecutter.basePackage }}.biz.site.release;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.validation.Valid;
import java.util.List;

/**
 * 发布版本管理
 */
@RestController
@RequestMapping("/site")
@RequiredArgsConstructor
public class ReleaseController {

    final ReleaseService thisService;

    /**
     * 获取版本列表
     */
    @PreAuthorize("@ck.hasPermit('site:release:list')")
    @GetMapping("/releases")
    public R<List<Release>> list() {
        LambdaQueryWrapper<Release> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Release::getId, Release::getVersion, Release::getDescription);
        wrapper.orderByDesc(Release::getCreateTime);
        List<Release> releases = thisService.list(wrapper);
        return R.ok(releases);
    }

    /**
     * 新增发布版本
     */
    @LogOperation(name = "新增发布版本", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:release:add')")
    @PostMapping("/releases")
    public R<Release> add(@Valid @RequestBody Release entity) {
        Release release = thisService.insert(entity);
        return release != null ? R.ok(release) : R.fail("新增发布版本失败");
    }

    /**
     * 按ID查询发布版本
     */
    @LogOperation(name = "按ID查询发布版本", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:release:get')")
    @GetMapping("/releases/{id}")
    public R<Release> get(@PathVariable Integer id) {
        Release entity = thisService.findById(id);
        return entity == null ? R.fail("版本不存在") : R.ok(entity);
    }

    /**
     * 按ID更新发布版本
     */
    @LogOperation(name = "按ID更新发布版本", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:release:update')")
    @PutMapping("/releases/{id}")
    public R<Release> update(@Valid @RequestBody Release entity, @PathVariable Integer id) {
        entity.setId(id);
        Release release = thisService.putById(entity);
        return release != null ? R.ok(release) : R.fail("更新发布版本失败");
    }

    /**
     * 按ID删除发布版本
     */
    @LogOperation(name = "按ID删除发布版本", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:release:delete')")
    @DeleteMapping("/releases/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        boolean deleted = thisService.deleteById(id);
        return deleted ? R.ok("删除发布版本成功") : R.fail("删除发布版本失败");
    }

}
