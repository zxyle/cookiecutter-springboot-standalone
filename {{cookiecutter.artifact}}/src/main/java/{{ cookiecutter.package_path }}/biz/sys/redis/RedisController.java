package {{ cookiecutter.basePackage }}.biz.sys.redis;

import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.common.response.R;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.annotation.Resource;
import {{ cookiecutter.namespace }}.validation.Valid;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis管理
 */
@RestController
@RequestMapping("/sys")
public class RedisController {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    /**
     * 获取key列表
     *
     * @param pattern 通配符，默认 *
     */
    @LogOperation(name = "获取key列表", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:key:list')")
    @GetMapping("/keys")
    public R<Set<String>> list(@RequestParam(defaultValue = "*") String pattern) {
        Set<String> keys = stringRedisTemplate.keys(pattern);
        return R.ok(keys);
    }

    /**
     * 删除key
     *
     * @param key redis key
     */
    @LogOperation(name = "删除key", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:key:delete')")
    @DeleteMapping("/keys/{key}")
    public R<String> delete(@PathVariable String key) {
        Boolean hasKey = stringRedisTemplate.hasKey(key);
        if (Boolean.FALSE.equals(hasKey)) {
            return R.fail("删除失败，key不存在");
        }
        Boolean delete = stringRedisTemplate.delete(key);
        return Boolean.TRUE.equals(delete) ? R.ok("删除key成功") : R.fail("删除key失败");
    }

    /**
     * 获取key的值
     *
     * @param key key
     */
    @LogOperation(name = "获取key的值", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:key:get')")
    @GetMapping("/keys/{key}")
    public R<RedisKeyResponse> get(@PathVariable String key) {
        Boolean hasKey = stringRedisTemplate.hasKey(key);
        if (Boolean.FALSE.equals(hasKey)) {
            return R.fail("获取失败，key不存在");
        }

        // 获取key基本信息
        RedisKeyResponse keyResponse = new RedisKeyResponse(
                key,
                stringRedisTemplate.opsForValue().get(key),
                stringRedisTemplate.getExpire(key),
                stringRedisTemplate.type(key)
        );

        return R.ok(keyResponse);
    }

    /**
     * 设置key的值
     */
    @LogOperation(name = "设置key的值", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:key:set')")
    @PostMapping("/keys")
    public R<Void> set(@Valid @RequestBody KeySetRequest req) {
        if (!"string".equalsIgnoreCase(req.getType())) {
            return R.fail("设置失败，目前只支持string类型");
        }
        stringRedisTemplate.opsForValue().set(req.getName(), req.getValue());

        if (req.getExpire() != null) {
            stringRedisTemplate.expire(req.getName(), req.getExpire(), TimeUnit.SECONDS);
        }
        return R.ok("设置key成功");
    }

    /**
     * key 重命名
     *
     * @param oldKey 旧key
     * @param newKey 新key
     */
    @LogOperation(name = "key重命名", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:key:rename')")
    @PutMapping("/keys/{oldKey}/rename/{newKey}")
    public R<Void> rename(@PathVariable String oldKey, @PathVariable String newKey) {
        Boolean hasKey = stringRedisTemplate.hasKey(oldKey);
        if (Boolean.FALSE.equals(hasKey)) {
            return R.fail("重命名失败，key不存在");
        }

        stringRedisTemplate.rename(oldKey, newKey);
        return R.ok("重命名key成功");
    }

    /**
     * 设置过期时间
     *
     * @param key     key
     * @param seconds 过期时间，单位秒
     */
    @LogOperation(name = "设置过期时间", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:key:expire')")
    @PutMapping("/keys/{key}/expire/{seconds}")
    public R<Void> expire(@PathVariable String key, @PathVariable Long seconds) {
        Boolean hasKey = stringRedisTemplate.hasKey(key);
        if (Boolean.FALSE.equals(hasKey)) {
            return R.fail("设置过期时间，key不存在");
        }

        Boolean expire = stringRedisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        return Boolean.FALSE.equals(expire) ? R.fail("设置过期时间失败") : R.ok("设置过期时间成功");
    }

}
