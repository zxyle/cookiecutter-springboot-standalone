package {{ cookiecutter.basePackage }}.biz.site.realname;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.validation.Valid;

/**
 * 实名认证管理
 */
@Slf4j
@RestController
@RequestMapping("/site")
@RequiredArgsConstructor
public class RealnameController extends AuthBaseController {

    final RealnameMapper thisMapper;
    final RealnameService thisService;

    /**
     * 分页查询
     */
    @LogOperation(name = "分页查询实名认证", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:realname:list')")
    @GetMapping("/realnames")
    public R<Page<Realname>> page(@Valid PaginationRequest req) {
        Page<Realname> page = thisService.page(req.getPage());
        return R.ok(page);
    }

    /**
     * 新增实名认证
     */
    @LogOperation(name = "新增实名认证", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:realname:add')")
    @PostMapping("/realnames")
    public R<Realname> add(@Valid @RequestBody RealnameRequest req) {
        Realname query = thisService.findByUserId(getUserId());
        if (query != null) {
            return R.fail("用户已实名认证");
        }

        Realname realname = new Realname();
        BeanUtils.copyProperties(req, realname);
        realname.setUserId(getUserId());
        realname.setStatus(VerificationStatusEnum.VERIFICATION_IN_PROGRESS);
        Realname result = thisService.insert(realname);
        return R.ok(result);
    }

    /**
     * 查询实名认证状态
     */
    @LogOperation(name = "查询实名认证状态", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:realname:get')")
    @GetMapping("/realnames/status")
    public R<Realname> get() {
        Integer userId = getUserId();
        Realname entity = thisService.findByUserId(userId);
        return entity == null ? R.fail(VerificationStatusEnum.UNVERIFIED.getDesc()) : R.ok(entity);
    }

}