package {{ cookiecutter.basePackage }}.biz.sys.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import {{ cookiecutter.basePackage }}.biz.sys.entity.Sql;
import {{ cookiecutter.basePackage }}.biz.sys.mapper.SqlMapper;
import {{ cookiecutter.basePackage }}.biz.sys.request.SqlRequest;
import {{ cookiecutter.basePackage }}.biz.sys.service.ISqlService;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.common.response.PageVO;
import {{ cookiecutter.basePackage }}.common.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * SQL执行
 */
@RestController
@RequestMapping("/sys")
public class SqlController {

    ISqlService thisService;

    @Autowired
    SqlMapper sqlMapper;

    public SqlController(ISqlService thisService) {
        this.thisService = thisService;
    }

    @PostMapping("/sqls/execute")
    public List<Map<String, Object>> execute(@Valid @RequestBody SqlRequest request) {
        return sqlMapper.execute(request.getSql());
    }

    // 执行sql


    // 获取所有表


    // 获取表前100条记录

    // 获取系统参数

    /**
     * 分页查询
     */
    @GetMapping("/sqls")
    public ApiResponse<PageVO<Sql>> list(@Valid PaginationRequest request) {
        IPage<Sql> page = PageRequestUtil.checkForMp(request);
        IPage<Sql> list = thisService.pageQuery(page);
        return PageRequestUtil.extractFromMp(list);
    }


    /**
     * 新增SQL执行
     */
    @PostMapping("/sqls")
    public ApiResponse<Sql> add(@Valid @RequestBody Sql entity) {
        boolean success = thisService.save(entity);
        if (success) {
            return new ApiResponse<>(entity);
        }
        return new ApiResponse<>();
    }


    /**
     * 按ID查询SQL执行
     */
    @GetMapping("/sqls/{id}")
    public ApiResponse<Sql> get(@PathVariable Long id) {
        return new ApiResponse<>(thisService.queryById(id));
    }

    /**
     * 按ID更新SQL执行
     */
    @PutMapping("/sqls/{id}")
    public ApiResponse<Object> update(@Valid @RequestBody Sql entity) {
        boolean success = thisService.updateById(entity);
        if (success) {
            return new ApiResponse<>("更新成功");
        }
        return new ApiResponse<>("更新失败");
    }

    /**
     * 按ID删除SQL执行
     */
    @DeleteMapping("/sqls/{id}")
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
    @GetMapping("/sqls/export")
    public void export(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        String fileName = "SQL执行";
        String baseName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + baseName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), Sql.class).autoCloseStream(Boolean.TRUE).sheet("Sheet1").doWrite(thisService.list());
    }

}
