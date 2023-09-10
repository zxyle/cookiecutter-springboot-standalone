package {{ cookiecutter.basePackage }}.biz.sys.acl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.PageVO;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * IP访问控制管理
 */
@Slf4j
@RestController
@RequestMapping("/sys")
@RequiredArgsConstructor
public class AclController {

    final AclMapper thisMapper;
    final AclService thisService;

    /**
     * 分页查询
     */
    @LogOperation(name = "IP访问控制分页查询", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:acl:list')")
    @GetMapping("/acl")
    public R<PageVO<Acl>> page(@Valid PaginationRequest request) {
        Page<Acl> page = thisService.page(request.toPageable());
        return R.page(page);
    }


    /**
     * 新增IP访问控制
     */
    @LogOperation(name = "新增IP访问控制", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:acl:add')")
    @PostMapping("/acl")
    public R<Acl> add(@Valid @RequestBody Acl entity) {
        Acl result = thisService.insert(entity);
        return R.ok(result);
    }


    /**
     * 按ID查询IP访问控制
     */
    @LogOperation(name = "按ID查询IP访问控制", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:acl:get')")
    @GetMapping("/acl/{id}")
    public R<Acl> get(@PathVariable Integer id) {
        Acl entity = thisService.queryById(id);
        return entity == null ? R.fail("IP访问控制不存在") : R.ok(entity);
    }

    /**
     * 按ID更新IP访问控制
     */
    @LogOperation(name = "按ID更新IP访问控制", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:acl:update')")
    @PutMapping("/acl/{id}")
    public R<Acl> update(@Valid @RequestBody Acl entity, @PathVariable Integer id) {
        entity.setId(id);
        Acl result = thisService.putById(entity);
        return result != null ? R.ok(result) : R.fail("更新IP访问控制失败");
    }

    /**
     * 按ID删除IP访问控制
     */
    @LogOperation(name = "按ID删除IP访问控制", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:acl:delete')")
    @DeleteMapping("/acl/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        boolean deleted = thisService.deleteById(id);
        return deleted ? R.ok("删除IP访问控制成功") : R.fail("删除IP访问控制失败");
    }
}