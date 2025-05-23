package {{ cookiecutter.basePackage }}.biz.site.feedback;

import com.alibaba.excel.EasyExcelFactory;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.common.exception.DataNotFoundException;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.servlet.http.HttpServletResponse;
import {{ cookiecutter.namespace }}.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 意见反馈管理
 */
@RestController
@RequestMapping("/site")
@RequiredArgsConstructor
public class FeedbackController {

    final FeedbackService thisService;

    /**
     * 意见反馈列表分页查询
     */
    @LogOperation(name = "意见反馈列表分页查询", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:feedback:list')")
    @GetMapping("/feedbacks")
    public R<Page<Feedback>> list(@Valid PaginationRequest req, HttpServletResponse response) throws IOException {
        LambdaQueryWrapper<Feedback> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(req.getStartTime() != null, Feedback::getCreateTime, req.getStartTime());
        wrapper.le(req.getEndTime() != null, Feedback::getCreateTime, req.getEndTime());

        // 数据导出
        if (req.getExport()) {
            // wrapper.last("limit 10000"); // 过多的数据导出会造成系统卡顿
            List<Feedback> list = thisService.list(wrapper);
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            String fileName = "意见反馈";
            String baseName = URLEncoder.encode(fileName, "UTF-8").replace("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + baseName + ".xlsx");
            EasyExcelFactory.write(response.getOutputStream(), Feedback.class)
                    .autoCloseStream(Boolean.TRUE).sheet("Sheet1").doWrite(list);
            return null;
        }

        Page<Feedback> page = thisService.page(req.getPage(), wrapper);
        return R.ok(page);
    }

    /**
     * 新增意见反馈
     */
    @PostMapping("/feedbacks")
    public R<Feedback> add(@Valid @RequestBody Feedback entity) {
        boolean success = thisService.save(entity);
        return success ? R.ok(entity) : R.fail("新增意见反馈失败");
    }

    /**
     * 按ID查询意见反馈
     */
    @LogOperation(name = "按ID查询意见反馈", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:feedback:get')")
    @GetMapping("/feedbacks/{id}")
    public R<Feedback> get(@PathVariable Integer id) {
        Feedback entity = thisService.findById(id);
        return entity == null ? R.fail("数据不存在") : R.ok(entity);
    }

    /**
     * 按ID更新意见反馈
     */
    @LogOperation(name = "按ID更新意见反馈", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:feedback:update')")
    @PutMapping("/feedbacks/{id}")
    public R<Void> update(@PathVariable Integer id, @Valid @RequestBody Feedback entity) {
        entity.setId(id);
        checkId(id);
        boolean updated = thisService.updateById(entity);
        return updated ? R.ok("更新意见反馈成功") : R.fail("更新意见反馈失败");
    }

    /**
     * 按ID删除意见反馈
     */
    @LogOperation(name = "按ID删除意见反馈", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:feedback:delete')")
    @DeleteMapping("/feedbacks/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        boolean deleted = thisService.deleteById(id);
        return deleted ? R.ok("删除意见反馈成功") : R.fail("删除意见反馈失败");
    }

    public void checkId(Integer id) {
        Feedback entity = thisService.getById(id);
        if (entity == null) {
            throw new DataNotFoundException("数据不存在: " + id);
        }
    }
}
