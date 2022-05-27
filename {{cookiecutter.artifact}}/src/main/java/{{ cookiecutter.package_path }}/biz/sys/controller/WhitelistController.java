package {{ cookiecutter.basePackage }}.biz.sys.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import {{ cookiecutter.basePackage }}.biz.sys.entity.Whitelist;
import {{ cookiecutter.basePackage }}.biz.sys.service.IWhitelistService;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.common.response.PageVO;
import {{ cookiecutter.basePackage }}.common.util.PageRequestUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * IP黑名单
 */
@RestController
@RequestMapping("/sys")
public class WhitelistController {

    IWhitelistService thisService;

    public WhitelistController(IWhitelistService thisService) {
        this.thisService = thisService;
    }

    /**
     * 分页查询
     */
    @GetMapping("/whitelists")
    public ApiResponse<PageVO<Whitelist>> list(@Valid PaginationRequest request) {
        IPage<Whitelist> page = PageRequestUtil.checkForMp(request);
        IPage<Whitelist> list = thisService.pageQuery(page);
        return PageRequestUtil.extractFromMp(list);
    }


    /**
     * 新增IP黑名单
     */
    @PostMapping("/whitelists")
    public ApiResponse<Whitelist> add(@Valid @RequestBody Whitelist entity) {
        boolean success = thisService.save(entity);
        if (success) {
            return new ApiResponse<>(entity);
        }
        return new ApiResponse<>();
    }


    /**
     * 按ID查询IP黑名单
     */
    @GetMapping("/whitelists/{id}")
    public ApiResponse<Whitelist> get(@PathVariable Long id) {
        return new ApiResponse<>(thisService.queryById(id));
    }

    /**
     * 按ID更新IP黑名单
     */
    @PutMapping("/whitelists/{id}")
    public ApiResponse<Object> update(@Valid @RequestBody Whitelist entity) {
        boolean success = thisService.updateById(entity);
        if (success) {
            return new ApiResponse<>("更新成功");
        }
        return new ApiResponse<>("更新失败");
    }

    /**
     * 按ID删除IP黑名单
     */
    @DeleteMapping("/whitelists/{id}")
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
    @GetMapping("/whitelists/export")
    public void export(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        String fileName = "IP白名单";
        String baseName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + baseName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), Whitelist.class).autoCloseStream(Boolean.TRUE).sheet("Sheet1").doWrite(thisService.list());
    }

}
