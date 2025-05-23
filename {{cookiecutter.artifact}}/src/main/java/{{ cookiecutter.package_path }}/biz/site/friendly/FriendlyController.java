package {{ cookiecutter.basePackage }}.biz.site.friendly;

import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.validation.Valid;
import java.util.List;

/**
 * 友情链接管理
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/site/friendly")
public class FriendlyController {

    final FriendlyService thisService;

    /**
     * 获取友链列表
     */
    @GetMapping("/links")
    public R<List<Friendly>> list() {
        return R.ok(thisService.all());
    }

    /**
     * 添加友链
     */
    @LogOperation(name = "添加友链", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:friendly:add')")
    @PostMapping("/links")
    public R<Friendly> add(@Valid @RequestBody Friendly entity) {
        Friendly result = thisService.insert(entity);
        return R.ok(result);
    }

    /**
     * 删除友链
     *
     * @param id 友链ID
     */
    @LogOperation(name = "删除友链", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:friendly:delete')")
    @DeleteMapping("/links/{id}")
    public R<Void> delete(@PathVariable("id") Integer id) {
        Friendly friendly = thisService.findById(id);
        if (friendly == null) {
            return R.fail("删除失败，友链不存在");
        }

        boolean deleted = thisService.deleteById(id);
        return deleted ? R.ok("删除友链成功") : R.fail("删除友链失败");
    }

    /**
     * 更新友链
     *
     * @param id     友链ID
     * @param entity 友链实体
     * @return 友链实体
     */
    @LogOperation(name = "更新友链", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:friendly:update')")
    @PutMapping("/links/{id}")
    public R<Friendly> update(@PathVariable Integer id, @Valid @RequestBody Friendly entity) {
        Friendly friendly = thisService.findById(id);
        if (friendly == null) {
            return R.fail("更新失败，友链不存在");
        }

        entity.setId(id);
        Friendly result = thisService.putById(entity);
        return result != null ? R.ok(result) : R.fail("更新友链失败");
    }

}
