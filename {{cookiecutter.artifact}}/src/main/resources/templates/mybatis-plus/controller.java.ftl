package ${table.packageName};

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${table.basePackageName}.common.aspect.LogOperation;
import ${table.basePackageName}.common.response.R;
import ${table.basePackageName}.common.request.BatchRequest;
import {{ cookiecutter.namespace }}.validation.Valid;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
<#if table.hasBaseController>
import ${table.baseControllerPath};
</#if>

import java.util.List;
import java.util.stream.Collectors;

<#if excel>
import java.io.IOException;
import {{ cookiecutter.namespace }}.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.BeanUtils;
import ${table.basePackageName}.common.excel.ExcelUtils;
</#if>

/**
 * ${table.comment}管理
<#if author??>
 *
 * @author ${author}
</#if>
 */
@Slf4j
@Validated
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
     * ${table.comment!}分页查询
     */
    @LogOperation(name = "${table.comment!}分页查询", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:list')")
    @GetMapping("${table.endpoint}")
    public R<Page<${table.className}>> page(@Valid ${table.className}PageRequest req) <#if excel>throws IOException</#if> {
        LambdaQueryWrapper<${table.className}> wrapper = new LambdaQueryWrapper<>();
        // 设置查询条件
        // if (StringUtils.isNotBlank(req.getKeyword())) {
        //     wrapper.and(i -> i.like(${table.className}::getName, req.getKeyword())
        //             .or().like(${table.className}::getName, req.getKeyword()));
        // }
        <#list table.columns as column>
        <#if column.javaType == "String">
        wrapper.eq(StringUtils.isNotBlank(req.get${column.property?cap_first}()), ${table.className}::get${column.property?cap_first}, req.get${column.property?cap_first}());
        <#else>
        wrapper.eq(req.get${column.property?cap_first}() != null, ${table.className}::get${column.property?cap_first}, req.get${column.property?cap_first}());
        </#if>
        </#list>
        wrapper.ge(req.getStartDate() != null, ${table.className}::getCreateTime, req.getStartDate());
        wrapper.le(req.getEndDate() != null, ${table.className}::getCreateTime, req.getEndDate());
        wrapper.ge(req.getStartTime() != null, ${table.className}::getCreateTime, req.getStartTime());
        wrapper.le(req.getEndTime() != null, ${table.className}::getCreateTime, req.getEndTime());
        wrapper.orderByDesc(${table.className}::getId);
        <#if excel>

        // Excel导出
        if (Boolean.TRUE.equals(req.getExport())) {
            // wrapper.last("limit 10000"); // 过多的数据导出会造成系统卡顿，建议设置限制条数
            if (CollectionUtils.isNotEmpty(req.getIds())) {
                wrapper.in(${table.className}::getId, req.getIds());
            }
            List<${table.className}> list = thisService.list(wrapper);
            List<${table.className}Export> exportList = IntStream.range(0, list.size())
                    .mapToObj(index -> {
                        ${table.className}Export export = new ${table.className}Export();
                        BeanUtils.copyProperties(list.get(index), export);
                        export.setSeq(index + 1);
                        return export;
                    }).collect(Collectors.toList());
            ExcelUtils.export(response, "${table.comment}", exportList, ${table.className}Export.class);
            return null;
        }

        </#if>
        Page<${table.className}> page = thisService.page(req.getPage(), wrapper);
        return R.ok(page);
    }

    /**
     * ${table.comment!}列表查询
     */
    @LogOperation(name = "${table.comment!}列表查询", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:list')")
    @GetMapping("${table.endpoint}/all")
    public R<List<${table.className}>> list(@Valid ${table.className}Request req) {
        LambdaQueryWrapper<${table.className}> wrapper = new LambdaQueryWrapper<>();
        // 补充查询条件
        wrapper.orderByDesc(${table.className}::getId);
        return R.ok(thisService.list(wrapper));
    }

    /**
     * 新增${table.comment!}
     */
    @LogOperation(name = "新增${table.comment!}", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:add')")
    @PostMapping("${table.endpoint}")
    public R<${table.className}> add(@Valid @RequestBody ${table.className}AddRequest req) {
        ${table.className} entity = new ${table.className}();
        BeanUtils.copyProperties(req, entity);
        ${table.className} result = thisService.insert(entity);
        return R.ok(result);
    }

    /**
     * 按ID查询${table.comment!}
     *
     * @param id ${table.comment!}id
     */
    @LogOperation(name = "按ID查询${table.comment!}", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:get')")
    @GetMapping("${table.endpoint}/{id}")
    public R<${table.className}> get(@PathVariable @Positive Integer id) {
        ${table.className} entity = thisService.findById(id);
        return entity == null ? R.fail("${table.comment!}不存在") : R.ok(entity);
    }

    /**
     * 按ID更新${table.comment!}
     *
     * @param id ${table.comment!}id
     */
    @LogOperation(name = "按ID更新${table.comment!}", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:update')")
    @PutMapping("${table.endpoint}/{id}")
    public R<${table.className}> update(@Valid @RequestBody ${table.className}UpdateRequest req, @PathVariable @Positive Integer id) {
        ${table.className} data = thisService.findById(id);
        if (data == null) {
            return R.fail("更新失败，${table.comment!}数据不存在");
        }

        ${table.className} entity = new ${table.className}();
        BeanUtils.copyProperties(req, entity);
        entity.setId(id);
        ${table.className} result = thisService.putById(entity);
        return result != null ? R.ok(result) : R.fail("更新${table.comment!}失败");
    }

    /**
     * 按ID删除${table.comment!}
     *
     * @param id ${table.comment!}id
     */
    @LogOperation(name = "按ID删除${table.comment!}", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:delete')")
    @DeleteMapping("${table.endpoint}/{id}")
    public R<Void> delete(@PathVariable @Positive Integer id) {
        ${table.className} entity = thisService.findById(id);
        if (entity == null) {
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
    @PostMapping("${table.endpoint}/batch")
    public R<Void> batchCreate(@Valid @RequestBody List<${table.className}AddRequest> requests) {
        List<${table.className}> list = requests.stream().map(req -> {
                ${table.className} item = new ${table.className}();
                BeanUtils.copyProperties(req, item);
                return item;
            }).collect(Collectors.toList());
        boolean success = thisService.saveBatch(list);
        return success ? R.ok("批量新增${table.comment!}成功") : R.fail("批量新增${table.comment!}失败");
    }

    /**
     * 批量更新${table.comment!}
     */
    @LogOperation(name = "批量更新${table.comment!}", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:update')")
    @PutMapping("${table.endpoint}/batch")
    public R<Void> batchUpdate(@Valid @RequestBody List<${table.className}UpdateRequest> requests) {
        List<${table.className}> list = requests.stream().map(req -> {
                ${table.className} item = new ${table.className}();
                BeanUtils.copyProperties(req, item);
                return item;
            }).collect(Collectors.toList());
        boolean success = thisService.updateBatchById(list);
        return success ? R.ok("批量更新${table.comment!}成功") : R.fail("批量更新${table.comment!}失败");
    }

    /**
     * 批量删除${table.comment!}
     */
    @LogOperation(name = "批量删除${table.comment!}", biz = "${table.biz}")
    @PreAuthorize("@ck.hasPermit('${table.biz}:${table.name}:delete')")
    @DeleteMapping("${table.endpoint}")
    public R<Void> batchDelete(@Valid BatchRequest req) {
        // boolean success = thisService.removeByIds(req.getIds());
        LambdaQueryWrapper<${table.className}> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(${table.className}::getId, req.getIds());
        // 为防止误删，这里只允许删除当前用户创建的记录
        // wrapper.eq(${table.className}::getUserId, getUserId());
        boolean success = thisService.remove(wrapper);
        return success ? R.ok("批量删除${table.comment!}成功") : R.fail("批量删除${table.comment!}失败");
    }
}