package {{ cookiecutter.basePackage }}.biz.site.banner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.validation.Valid;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;


/**
 * Banner管理
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/site")
@RequiredArgsConstructor
public class BannerController extends AuthBaseController {

    final BannerMapper thisMapper;
    final BannerService thisService;

    /**
     * 分页查询
     */
    @LogOperation(name = "分页查询", biz = "site")
    // @PreAuthorize("@ck.hasPermit('site:banner:list')")
    @GetMapping("/banners")
    public R<Page<Banner>> page(@Valid BannerPageRequest req) {
        LambdaQueryWrapper<Banner> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(req.getPosition()), Banner::getPosition, req.getPosition());
        wrapper.eq(req.getStatus() != null, Banner::getStatus, req.getStatus());
        wrapper.orderByDesc(Banner::getId);
        Page<Banner> page = thisService.page(req.getPage(), wrapper);
        return R.ok(page);
    }

    /**
     * 新增Banner
     */
    @LogOperation(name = "新增", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:banner:add')")
    @PostMapping("/banners")
    public R<Banner> add(@Valid @RequestBody BannerAddRequest req) {
        Banner entity = new Banner();
        BeanUtils.copyProperties(req, entity);
        Banner result = thisService.insert(entity);
        return R.ok(result);
    }

    /**
     * 按ID查询Banner
     *
     * @param id id
     */
    @LogOperation(name = "按ID查询", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:banner:get')")
    @GetMapping("/banners/{id}")
    public R<Banner> get(@PathVariable @Positive Integer id) {
        Banner entity = thisService.findById(id);
        return entity == null ? R.fail("不存在") : R.ok(entity);
    }

    /**
     * 按ID更新Banner
     *
     * @param id id
     */
    @LogOperation(name = "按ID更新", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:banner:update')")
    @PutMapping("/banners/{id}")
    public R<Banner> update(@Valid @RequestBody BannerUpdateRequest req, @PathVariable @Positive Integer id) {
        Banner data = thisService.findById(id);
        if (data == null) {
            return R.fail("更新失败，数据不存在");
        }

        Banner entity = new Banner();
        BeanUtils.copyProperties(req, entity);
        entity.setId(id);
        Banner result = thisService.putById(entity);
        return result != null ? R.ok(result) : R.fail("更新失败");
    }

    /**
     * 按ID删除Banner
     *
     * @param id id
     */
    @LogOperation(name = "按ID删除", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:banner:delete')")
    @DeleteMapping("/banners/{id}")
    public R<Void> delete(@PathVariable @Positive Integer id) {
        Banner entity = thisService.findById(id);
        if (entity == null) {
            return R.fail("删除失败，数据不存在");
        }

        boolean deleted = thisService.deleteById(id);
        return deleted ? R.ok("删除成功") : R.fail("删除失败");
    }

}