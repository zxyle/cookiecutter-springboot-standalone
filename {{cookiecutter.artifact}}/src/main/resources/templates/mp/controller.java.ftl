package ${package.Controller};

import com.alibaba.excel.EasyExcelFactory;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import {{ cookiecutter.basePackage }}.common.request.OrderPageRequest;
import {{ cookiecutter.basePackage }}.common.response.R;
import {{ cookiecutter.basePackage }}.common.response.PageVO;
import {{ cookiecutter.basePackage }}.common.util.EntityUtil;
import {{ cookiecutter.basePackage }}.common.util.PageRequestUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * ${table.comment!}管理
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("/${package.ModuleName}")
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    ${table.serviceName} thisService;

    public ${table.controllerName}(${table.serviceName} thisService) {
        this.thisService = thisService;
    }

    /**
     * ${table.comment!}分页查询
     */
    @PreAuthorize("@ck.hasPermit('${package.ModuleName}:${table.entityPath}s:list')")
    @GetMapping("/${table.entityPath}s")
    public R<PageVO<${entity}>> page(@Valid OrderPageRequest request, HttpServletResponse response) throws IOException {
        QueryWrapper<${entity}> wrapper = new QueryWrapper<>();
        wrapper.orderBy(EntityUtil.getFields(${entity}.class).contains(request.getField()),
                request.getOrder(), request.getField());
        IPage<${entity}> page = PageRequestUtil.checkForMp(request);
        IPage<${entity}> list = thisService.pageQuery(page, wrapper);

        // 数据导出Excel功能，不需要可以删除
        if (request.isExport()) {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            String fileName = "${table.comment!}";
            String baseName = URLEncoder.encode(fileName, "UTF-8").replace("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + baseName + ".xlsx");
            EasyExcelFactory.write(response.getOutputStream(), ${entity}.class)
                    .autoCloseStream(Boolean.TRUE).sheet("Sheet1").doWrite(list.getRecords());
            return null;
        }
        return PageRequestUtil.extractFromMp(list);
    }


    /**
     * ${table.comment!}列表查询
     */
    // 当数据量不大时，需要查出全部数据，可以使用此接口，不需要可以删除
    @PreAuthorize("@ck.hasPermit('${package.ModuleName}:${table.entityPath}s:list')")
    @GetMapping("/${table.entityPath}s")
    public R<List<${entity}>> list() {
        QueryWrapper<${entity}> wrapper = new QueryWrapper<>();
        return R.ok(thisService.list(wrapper));
    }


    /**
     * 新增${table.comment!}
     */
    @PreAuthorize("@ck.hasPermit('${package.ModuleName}:${table.entityPath}s:add')")
    @PostMapping("/${table.entityPath}s")
    public R<${entity}> add(@Valid @RequestBody ${entity} entity) {
        boolean success = thisService.save(entity);
        return success ? R.ok(entity) : R.fail("新增失败");
    }


    /**
     * 按ID查询${table.comment!}
     */
    @PreAuthorize("@ck.hasPermit('${package.ModuleName}:${table.entityPath}s:get')")
    @GetMapping("/${table.entityPath}s/{id}")
    public R<${entity}> get(@PathVariable Long id) {
        ${entity} entity = thisService.queryById(id);
        return entity == null ? R.fail("数据不存在") : R.ok(entity);
    }

    /**
     * 按ID更新${table.comment!}
     */
    @PreAuthorize("@ck.hasPermit('${package.ModuleName}:${table.entityPath}s:update')")
    @PutMapping("/${table.entityPath}s/{id}")
    public R<Object> update(@Valid @RequestBody ${entity} entity, @PathVariable Long id) {
        entity.setId(id);
        boolean success = thisService.updateById(entity);
        return success ? R.ok("更新成功") : R.fail("更新失败");
    }

    /**
     * 按ID删除${table.comment!}
     */
    @PreAuthorize("@ck.hasPermit('${package.ModuleName}:${table.entityPath}s:delete')")
    @DeleteMapping("/${table.entityPath}s/{id}")
    public R<Object> delete(@PathVariable Long id) {
        boolean success = thisService.removeById(id);
        return success ? R.ok("删除成功") : R.fail("删除失败");
    }

}
