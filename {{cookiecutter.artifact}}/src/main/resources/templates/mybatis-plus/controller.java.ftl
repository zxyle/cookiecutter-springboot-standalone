package ${table.packageName};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${table.basePackageName}.common.aspect.LogOperation;
import ${table.basePackageName}.common.request.PaginationRequest;
import ${table.basePackageName}.common.response.R;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
<#if table.hasBaseController>
import ${table.baseControllerPath};
</#if>

import java.util.List;

/**
 * ${table.comment}管理
 */
@Slf4j
@RestController
@RequestMapping("/${table.biz}")
@RequiredArgsConstructor
<#if table.hasBaseController>
public class ${className} extends ${table.baseControllerClassName} {
<#else>
public class ${className} {
</#if>

    final ${table.className}Mapper thisMapper;
    final ${table.className}Service thisService;

    /**
     * 分页查询
     */
    @LogOperation(name = "分页查询${table.comment!}", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:list')")
    @GetMapping("/${table.name}s")
    public R<Page<${table.className}>> page(@Valid PaginationRequest request) {
        Page<${table.className}> page = thisService.page(request.toPageable());
        return R.ok(page);
    }

    /**
     * ${table.comment!}列表查询
     */
    // 当数据量不大时，需要查出全部数据，可以使用此接口，不需要可以删除
    // @LogOperation(name = "${table.comment!}列表查询", biz = "${table.biz}")
    // @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:list')")
    // @GetMapping("/${table.name}s")
    // public R<List<${table.className}>> list() {
    //     return R.ok(thisService.list());
    // }


    /**
     * 新增${table.comment!}
     */
    @LogOperation(name = "新增${table.comment!}", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:add')")
    @PostMapping("/${table.name}s")
    public R<${table.className}> add(@Valid @RequestBody ${table.className} entity) {
        ${table.className} result = thisService.insert(entity);
        return R.ok(result);
    }


    /**
     * 按ID查询${table.comment!}
     */
    @LogOperation(name = "按ID查询${table.comment!}", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:get')")
    @GetMapping("/${table.name}s/{id}")
    public R<${table.className}> get(@PathVariable Integer id) {
        ${table.className} entity = thisService.findById(id);
        return entity == null ? R.fail("数据不存在") : R.ok(entity);
    }

    /**
     * 按ID更新${table.comment!}
     */
    @LogOperation(name = "按ID更新${table.comment!}", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:update')")
    @PutMapping("/${table.name}s/{id}")
    public R<${table.className}> update(@Valid @RequestBody ${table.className} entity, @PathVariable Integer id) {
        entity.setId(id);
        ${table.className} result = thisService.putById(entity);
        return result != null ? R.ok(result) : R.fail("更新${table.comment!}失败");
    }

    /**
     * 按ID删除${table.comment!}
     */
    @LogOperation(name = "按ID删除${table.comment!}", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:delete')")
    @DeleteMapping("/${table.name}s/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        boolean deleted = thisService.deleteById(id);
        return deleted ? R.ok("删除${table.comment!}成功") : R.fail("删除${table.comment!}失败");
    }

    /**
     * 批量新增
     */
    @LogOperation(name = "批量新增${table.comment!}", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:add')")
    @PostMapping("/${table.name}s/batch-create")
    public R<Void> batchCreate(@Valid @RequestBody List<${table.className}> list) {
        boolean success = thisService.saveBatch(list);
        return success ? R.ok("批量新增成功") : R.fail("批量新增失败");
    }

    /**
     * 批量更新
     */
    @LogOperation(name = "批量更新${table.comment!}", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:update')")
    @PutMapping("/${table.name}s/batch-update")
    public R<Void> batchUpdate(@Valid @RequestBody List<${table.className}> list) {
        boolean success = thisService.updateBatchById(list);
        return success ? R.ok("批量更新成功") : R.fail("批量更新失败");
    }

    /**
     * 批量删除
     */
    @LogOperation(name = "批量删除${table.comment!}", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:delete')")
    @DeleteMapping("/${table.name}s/batch-delete")
    public R<Void> batchDelete(@Valid @RequestBody List<Integer> ids) {
        boolean success = thisService.removeByIds(ids);
        return success ? R.ok("批量删除成功") : R.fail("批量删除失败");
    }
}