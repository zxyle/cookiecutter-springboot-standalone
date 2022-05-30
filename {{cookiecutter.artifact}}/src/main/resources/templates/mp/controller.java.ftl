package ${package.Controller};

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import {{ cookiecutter.basePackage }}.common.listener.AbstractListener;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.common.response.PageVO;
import {{ cookiecutter.basePackage }}.common.util.PageRequestUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import org.springframework.web.multipart.MultipartFile;
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
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * ${table.comment!}
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
     * 分页查询
     */
    @GetMapping("/${table.entityPath}s")
    public ApiResponse<PageVO<${entity}>> list(@Valid PaginationRequest request) {
        IPage<${entity}> page = PageRequestUtil.checkForMp(request);
        IPage<${entity}> list = thisService.pageQuery(page);
        return PageRequestUtil.extractFromMp(list);
    }


    /**
     * 新增${table.comment!}
     */
    @PostMapping("/${table.entityPath}s")
    public ApiResponse<${entity}> add(@Valid @RequestBody ${entity} entity) {
        boolean success = thisService.save(entity);
        if (success) {
        return new ApiResponse<>(entity);
        }
        return new ApiResponse<>();
    }


    /**
     * 按ID查询${table.comment!}
     */
    @GetMapping("/${table.entityPath}s/{id}")
    public ApiResponse<${entity}> get(@PathVariable Long id) {
        return new ApiResponse<>(thisService.queryById(id));
    }

    /**
     * 按ID更新${table.comment!}
     */
    @PutMapping("/${table.entityPath}s/{id}")
    public ApiResponse<Object> update(@Valid @RequestBody ${entity} entity) {
        boolean success = thisService.updateById(entity);
        if (success) {
            return new ApiResponse<>("更新成功");
        }
        return new ApiResponse<>("更新失败");
    }

    /**
     * 按ID删除${table.comment!}
     */
    @DeleteMapping("/${table.entityPath}s/{id}")
    public ApiResponse<Object> delete(@PathVariable Long id) {
        boolean success = thisService.removeById(id);
        if (success) {
            return new ApiResponse<>("删除成功");
        }
        return new ApiResponse<>("删除失败");
    }

    /**
     * Excel导出
     */
    @GetMapping("/${table.entityPath}s/export")
    public void export(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        String fileName = "${table.comment!}";
        String baseName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + baseName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), ${entity}.class).autoCloseStream(Boolean.TRUE).sheet("Sheet1").doWrite(thisService.list());
    }


    /**
     * Excel数据导入
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        File dest = new File("/tmp/" + file.getOriginalFilename());
        file.transferTo(dest);
        AbstractListener<${entity}> abstractListener = new AbstractListener<>();
        abstractListener.setService(thisService);
        EasyExcel.read(dest.getAbsolutePath(), ${entity}.class, abstractListener).sheet().doRead();
        return "写入数据成功";
    }

}
