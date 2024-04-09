package {{ cookiecutter.basePackage }}.biz.sys.verification;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 验证码发送记录管理
 */
@Slf4j
@RestController
@RequestMapping("/sys")
@RequiredArgsConstructor
public class VerificationController {

    final VerificationMapper thisMapper;
    final VerificationService thisService;

    /**
     * 分页查询
     */
    @LogOperation(name = "分页查询验证码发送记录", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:verification:list')")
    @GetMapping("/verifications")
    public R<Page<Verification>> page(@Valid PaginationRequest req) {
        Page<Verification> page = thisService.page(req.toPageable(Verification.class));
        return R.ok(page);
    }

}
