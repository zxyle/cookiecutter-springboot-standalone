package {{ cookiecutter.basePackage }}.biz.auth.app;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.biz.auth.user.User;
import {{ cookiecutter.basePackage }}.biz.auth.user.UserService;
import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.R;
import {{ cookiecutter.basePackage }}.common.util.JacksonUtil;
import {{ cookiecutter.basePackage }}.common.validation.Add;
import {{ cookiecutter.basePackage }}.common.validation.Update;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.servlet.http.HttpServletRequest;
import {{ cookiecutter.namespace }}.validation.Valid;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 应用管理
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AppController extends AuthBaseController {

    final AppService thisService;
    final StringRedisTemplate stringRedisTemplate;
    final UserService userService;
    final HttpServletRequest servletRequest;

    /**
     * 应用分页查询
     */
    @LogOperation(name = "应用分页查询", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:app:list')")
    @GetMapping("/apps")
    public R<Page<App>> page(@Valid PaginationRequest req) {
        LambdaQueryWrapper<App> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(req.getKeyword())) {
            wrapper.and(i -> i.like(App::getName, req.getKeyword())
                    .or().like(App::getDescription, req.getKeyword()));
        }
        Page<App> page = thisService.page(req.getPage(), wrapper);
        page.getRecords().forEach(app -> app.setAppSecret(null));
        return R.ok(page);
    }

    /**
     * 新增应用
     */
    @LogOperation(name = "新增应用", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:app:add')")
    @PostMapping("/apps")
    public R<App> add(@Validated(Add.class) @RequestBody AppRequest req) {
        App entity = new App();
        BeanUtils.copyProperties(req, entity);
        entity.setAppSecret(IdUtil.fastSimpleUUID());
        App app = thisService.insert(entity);
        return app != null ? R.ok(app) : R.fail("新增应用失败");
    }

    /**
     * 按ID查询应用
     */
    @LogOperation(name = "按ID查询应用", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:app:get')")
    @GetMapping("/apps/{id}")
    public R<App> get(@PathVariable Integer id) {
        App result = thisService.findById(id);
        if (result != null) {
            result.setAppSecret(null);
        }
        return result == null ? R.fail("应用不存在") : R.ok(result);
    }

    /**
     * 按ID更新应用
     */
    @LogOperation(name = "按ID更新应用", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:app:update')")
    @PutMapping("/apps/{id}")
    public R<App> update(@Validated(Update.class) @RequestBody AppRequest req, @PathVariable Integer id) {
        App entity = new App();
        BeanUtils.copyProperties(req, entity);
        entity.setId(id);
        App result = thisService.putById(entity);
        if (result != null) {
            result.setAppSecret(null);
        }
        return result != null ? R.ok(result) : R.fail("更新应用失败");
    }

    /**
     * 按ID删除应用
     */
    @LogOperation(name = "按ID删除应用", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:app:delete')")
    @DeleteMapping("/apps/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        boolean deleted = thisService.deleteById(id);
        return deleted ? R.ok("删除应用成功") : R.fail("删除应用失败");
    }

    /**
     * 重新生成密钥
     *
     * @param id 应用id
     */
    @LogOperation(name = "重新生成密钥", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:app:update')")
    @GetMapping("/apps/{id}/regenerateSecret")
    public R<App> regenerateSecret(@PathVariable Integer id) {
        App app = thisService.findById(id);
        if (app == null) {
            return R.fail("应用不存在");
        }

        app.setAppSecret(IdUtil.fastSimpleUUID());
        App result = thisService.putById(app);
        return result != null ? R.ok(result) : R.fail("重新生成密钥失败");
    }

    /**
     * 应用上架
     *
     * @param id 应用id
     */
    @LogOperation(name = "应用上架", biz = "auth")
    @GetMapping("/apps/{id}/publish")
    public R<Void> publish(@PathVariable Integer id) {
        App app = thisService.findById(id);
        if (app == null) {
            return R.fail("应用不存在");
        }
        if (app.getEnabled()) {
            return R.fail("应用已上架");
        }

        app.setEnabled(true);  // 启用
        App result = thisService.putById(app);
        return result != null ? R.ok("应用上架成功") : R.fail("应用上架失败");
    }

    /**
     * 应用下架
     *
     * @param id 应用id
     */
    @LogOperation(name = "应用下架", biz = "auth")
    @GetMapping("/apps/{id}/revoke")
    public R<Void> revoke(@PathVariable Integer id) {
        App app = thisService.findById(id);
        if (app == null) {
            return R.fail("应用不存在");
        }
        if (!app.getEnabled()) {
            return R.fail("应用已下架");
        }

        app.setEnabled(false);  // 禁用
        App result = thisService.putById(app);
        return result != null ? R.ok("应用下架成功") : R.fail("应用下架失败");
    }

    /**
     * 应用跳转
     *
     * @param id 应用id
     */
    @LogOperation(name = "应用跳转", biz = "auth")
    @GetMapping("/apps/{id}/redirect")
    public R<RedirectResponse> redirect(@PathVariable Integer id) {
        // 根据appId查询应用
        App app = thisService.findById(id);
        if (app == null) {
            return R.fail("应用不存在");
        }
        if (app.getEnabled()) {
            return R.fail("应用已下架");
        }

        // 生成临时授权码, 存入redis, 30秒过期
        String authCode = UUID.randomUUID().toString();
        String key = "authCode:" + authCode;
        String value = String.format("%s:%s", app.getAppSecret(), getUserId());
        stringRedisTemplate.opsForValue().set(key, value, 30, TimeUnit.SECONDS);

        // 查询出跳转后拼接生成authCode
        String redirectUrl = app.getRedirectUrl() + "?authCode=" + authCode;

        // 根据应用的redirectUrl跳转
        return R.ok(new RedirectResponse(redirectUrl, authCode));
    }

    /**
     * 根据临时授权码获取用户详情
     */
    @LogOperation(name = "根据临时授权码获取用户详情", biz = "auth")
    @PostMapping("/apps/currentUser")
    public R<User> check(@Valid @RequestBody CheckRequest req) {
        String appKey = servletRequest.getHeader("appKey");
        String timestamp = servletRequest.getHeader("timestamp");
        String sign = servletRequest.getHeader("sign");
        if (StringUtils.isAnyBlank(appKey, timestamp, sign)) {
            return R.fail("请求头缺少参数");
        }

        // 检查时间戳是否过期，5分钟内有效
        long now = System.currentTimeMillis();
        long interval = now - Long.parseLong(timestamp);
        if (interval > 5 * 60 * 1000) {
            return R.fail("请求已过期");
        }

        // 检查授权码是否过期
        String key = "authCode:" + req.getAuthCode();
        String value = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(value)) {
            return R.fail("授权码已过期");
        }

        // 校验签名是否正确
        String[] split = value.split(":");
        String body = JacksonUtil.serialize(req);
        String appSecret = split[0];
        String md5Hex = DigestUtils.md5Hex(appKey + appSecret + body + timestamp);
        if (!md5Hex.equalsIgnoreCase(sign)) {
            return R.fail("签名错误");
        }

        Integer userId = Integer.valueOf(split[1]);
        User user = userService.findById(userId);
        return R.ok(user);
    }

}
