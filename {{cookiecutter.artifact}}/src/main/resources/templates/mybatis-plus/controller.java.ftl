package ${table.packageName};

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${table.basePackageName}.common.aspect.LogOperation;
import ${table.basePackageName}.common.response.R;
import {{ cookiecutter.namespace }}.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
<#if table.hasBaseController>
import ${table.baseControllerPath};
</#if>

import java.util.List;

<#if excel>
import com.alibaba.excel.EasyExcelFactory;
import java.net.URLEncoder;
import org.springframework.http.MediaType;
import java.io.IOException;
import {{ cookiecutter.namespace }}.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.BeanUtils;
</#if>

/**
 * ${table.comment}管理
<#if author??>
 *
 * @author ${author}
</#if>
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
    <#if excel>
    final HttpServletResponse response;
    </#if>

    /**
     * 分页查询
     */
    @LogOperation(name = "分页查询${table.comment!}", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:list')")
    @GetMapping("${table.endpoint}")
    public R<Page<${table.className}>> page(@Valid ${table.className}PageRequest req) <#if excel>throws IOException</#if> {
        LambdaQueryWrapper<${table.className}> wrapper = new LambdaQueryWrapper<>();
        // 设置查询条件
        // wrapper.eq(${table.className}::getId, 1);
        <#if excel>

        // Excel导出功能，不需要可以删除
        if (req.isExport()) {
            // wrapper.last("limit 10000"); // 过多的数据导出会造成系统卡顿，建议设置限制条数
            List<${table.className}> list = thisService.list(wrapper);
            List<${table.className}Export> exportList = IntStream.range(0, list.size())
                    .mapToObj(index -> {
                        ${table.className}Export export = new ${table.className}Export();
                        BeanUtils.copyProperties(list.get(index), export);
                        export.setSeq(index + 1);
                        return export;
                    }).collect(Collectors.toList());
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            String fileName = "${table.comment}";
            String baseName = URLEncoder.encode(fileName, "UTF-8").replace("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + baseName + ".xlsx");
            EasyExcelFactory.write(response.getOutputStream(), ${table.className}Export.class)
                    .autoCloseStream(Boolean.TRUE).sheet("Sheet1").doWrite(exportList);
            return null;
        }

        </#if>
        Page<${table.className}> page = thisService.page(req.toPageable(), wrapper);
        return R.ok(page);
    }

    /**
     * ${table.comment!}列表查询
     */
    // 当数据量不大时，需要查出全部数据，可以使用此接口，不需要可以删除
    // @LogOperation(name = "${table.comment!}列表查询", biz = "${table.biz}")
    // @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:list')")
    // @GetMapping("${table.endpoint}")
    // public R<List<${table.className}>> list() {
    //     LambdaQueryWrapper<${table.className}> wrapper = new LambdaQueryWrapper<>();
    //     // 补充查询条件
    //     return R.ok(thisService.list(wrapper));
    // }

    /**
     * 新增${table.comment!}
     */
    @LogOperation(name = "新增${table.comment!}", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:add')")
    @PostMapping("${table.endpoint}")
    public R<${table.className}> add(@Valid @RequestBody ${table.className} entity) {
        ${table.className} result = thisService.insert(entity);
        return R.ok(result);
    }

    /**
     * 按ID查询${table.comment!}
     */
    @LogOperation(name = "按ID查询${table.comment!}", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:get')")
    @GetMapping("${table.endpoint}/{id}")
    public R<${table.className}> get(@PathVariable Integer id) {
        ${table.className} entity = thisService.findById(id);
        return entity == null ? R.fail("${table.comment!}不存在") : R.ok(entity);
    }

    /**
     * 按ID更新${table.comment!}
     */
    @LogOperation(name = "按ID更新${table.comment!}", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:update')")
    @PutMapping("${table.endpoint}/{id}")
    public R<${table.className}> update(@Valid @RequestBody ${table.className} entity, @PathVariable Integer id) {
        if (thisService.findById(id) == null) {
            return R.fail("更新失败，${table.comment!}数据不存在");
        }

        entity.setId(id);
        ${table.className} result = thisService.putById(entity);
        return result != null ? R.ok(result) : R.fail("更新${table.comment!}失败");
    }

    /**
     * 按ID删除${table.comment!}
     */
    @LogOperation(name = "按ID删除${table.comment!}", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:delete')")
    @DeleteMapping("${table.endpoint}/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        if (thisService.findById(id) == null) {
            return R.fail("删除失败，${table.comment!}数据不存在");
        }

        boolean deleted = thisService.deleteById(id);
        return deleted ? R.ok("删除${table.comment!}成功") : R.fail("删除${table.comment!}失败");
    }

    /**
     * 批量新增${table.comment!}
     */
    @LogOperation(name = "批量新增${table.comment!}", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:add')")
    @PostMapping("${table.endpoint}/batch-create")
    public R<Void> batchCreate(@Valid @RequestBody List<${table.className}> list) {
        boolean success = thisService.saveBatch(list);
        return success ? R.ok("批量新增成功") : R.fail("批量新增失败");
    }

    /**
     * 批量更新${table.comment!}
     */
    @LogOperation(name = "批量更新${table.comment!}", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:update')")
    @PutMapping("${table.endpoint}/batch-update")
    public R<Void> batchUpdate(@Valid @RequestBody List<${table.className}> list) {
        boolean success = thisService.updateBatchById(list);
        return success ? R.ok("批量更新成功") : R.fail("批量更新失败");
    }

    /**
     * 批量删除${table.comment!}
     */
    @LogOperation(name = "批量删除${table.comment!}", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:delete')")
    @DeleteMapping("${table.endpoint}/batch-delete")
    public R<Void> batchDelete(@Valid @RequestBody List<Integer> ids) {
        boolean success = thisService.removeByIds(ids);
        return success ? R.ok("批量删除成功") : R.fail("批量删除失败");
    }
}