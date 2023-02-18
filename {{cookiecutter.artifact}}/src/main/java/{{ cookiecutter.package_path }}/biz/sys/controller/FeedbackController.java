package {{ cookiecutter.basePackage }}.biz.sys.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import {{ cookiecutter.basePackage }}.common.listener.AbstractListener;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.common.response.PageVO;
import {{ cookiecutter.basePackage }}.common.util.PageRequestUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import {{ cookiecutter.basePackage }}.biz.sys.entity.Feedback;
import {{ cookiecutter.basePackage }}.biz.sys.service.IFeedbackService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 意见反馈
 */
@RestController
@RequestMapping("/sys")
public class FeedbackController {

    IFeedbackService thisService;

    public FeedbackController(IFeedbackService thisService) {
        this.thisService = thisService;
    }

    /**
     * 意见反馈列表分页查询
     */
    @PreAuthorize("@ck.hasPermit('sys:feedbacks:list')")
    @GetMapping("/feedbacks")
    public ApiResponse<PageVO<Feedback>> list(@Valid PaginationRequest request) {
        IPage<Feedback> page = PageRequestUtil.checkForMp(request);
        IPage<Feedback> list = thisService.pageQuery(page);
        return PageRequestUtil.extractFromMp(list);
    }


    /**
     * 新增意见反馈
     */
    // @PreAuthorize("@ck.hasPermit('sys:feedbacks:add')")
    @PostMapping("/feedbacks")
    public ApiResponse<Feedback> add(@Valid @RequestBody Feedback entity) {
        boolean success = thisService.save(entity);
        if (success) {
            return new ApiResponse<>(entity);
        }
        return new ApiResponse<>("新增失败", false);
    }


    /**
     * 按ID查询意见反馈
     */
    @PreAuthorize("@ck.hasPermit('sys:feedbacks:get')")
    @GetMapping("/feedbacks/{id}")
    public ApiResponse<Feedback> get(@PathVariable Long id) {
        Feedback entity = thisService.queryById(id);
        if (entity == null) {
            return new ApiResponse<>("数据不存在", false);
        }
        return new ApiResponse<>(entity);
    }

    /**
     * 按ID更新意见反馈
     */
    @PreAuthorize("@ck.hasPermit('sys:feedbacks:update')")
    @PutMapping("/feedbacks/{id}")
    public ApiResponse<Object> update(@Valid @RequestBody Feedback entity, @PathVariable Long id) {
        entity.setId(id);
        boolean success = thisService.updateById(entity);
        if (success) {
            return new ApiResponse<>("更新成功");
        }
        return new ApiResponse<>("更新失败", false);
    }

    /**
     * 按ID删除意见反馈
     */
    @PreAuthorize("@ck.hasPermit('sys:feedbacks:delete')")
    @DeleteMapping("/feedbacks/{id}")
    public ApiResponse<Object> delete(@PathVariable Long id) {
        boolean success = thisService.removeById(id);
        if (success) {
            return new ApiResponse<>("删除成功");
        }
        return new ApiResponse<>("删除失败", false);
    }

    /**
     * Excel数据导出意见反馈
     */
    @PreAuthorize("@ck.hasPermit('sys:feedbacks:export')")
    @GetMapping("/feedbacks/export")
    public void export(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        String fileName = "意见反馈";
        String baseName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + baseName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), Feedback.class).autoCloseStream(Boolean.TRUE).sheet("Sheet1").doWrite(thisService.list());
    }


    /**
     * Excel数据导入意见反馈
     */
    @PreAuthorize("@ck.hasPermit('sys:feedbacks:upload')")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ApiResponse<Object> upload(@RequestParam("file") MultipartFile file) throws IOException {
        File dest = new File("/tmp/" + file.getOriginalFilename());
        file.transferTo(dest);
        AbstractListener<Feedback> abstractListener = new AbstractListener<>();
        abstractListener.setService(thisService);
        EasyExcel.read(dest.getAbsolutePath(), AbstractListener.class, abstractListener).sheet().doRead();
        return new ApiResponse<>("导入成功");
    }

}
