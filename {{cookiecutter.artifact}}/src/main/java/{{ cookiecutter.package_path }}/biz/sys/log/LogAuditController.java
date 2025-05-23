package {{ cookiecutter.basePackage }}.biz.sys.log;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import {{ cookiecutter.namespace }}.validation.Valid;

/**
 * 日志审计
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/logs")
public class LogAuditController {

    final LoginLogService loginLogService;
    final OperateLogService operateLogService;

    /**
     * 登录日志分页查询
     */
    @LogOperation(name = "登录日志分页查询", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:login:list')")
    @GetMapping("/login")
    public R<Page<LoginLog>> list(@Valid LoginLogRequest req) {
        LambdaQueryWrapper<LoginLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(req.getAccount()), LoginLog::getAccount, req.getAccount());
        wrapper.eq(req.getSuccess() != null, LoginLog::getSuccess, req.getSuccess());
        Page<LoginLog> page = loginLogService.page(req.getPage(), wrapper);
        return R.ok(page);
    }

    /**
     * 操作日志分页查询
     */
    @LogOperation(name = "操作日志分页查询", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:operate:list')")
    @GetMapping("/operate")
    public R<Page<OperateLog>> page(@Valid OperateLogRequest req) {
        LambdaQueryWrapper<OperateLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(req.getUserId() != null, OperateLog::getUserId, req.getUserId());
        wrapper.eq(StringUtils.isNotBlank(req.getBiz()), OperateLog::getBiz, req.getBiz());
        wrapper.eq(StringUtils.isNotBlank(req.getTraceId()), OperateLog::getTraceId, req.getTraceId());
        wrapper.between(req.getStartTime() != null && req.getEndTime() != null,
                OperateLog::getOperateTime, req.getStartTime(), req.getEndTime());
        wrapper.eq(req.getSuccess() != null, OperateLog::getSuccess, req.getSuccess());
        Page<OperateLog> page = operateLogService.page(req.getPage(), wrapper);
        return R.ok(page);
    }
}
