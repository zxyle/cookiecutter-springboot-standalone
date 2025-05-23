package {{ cookiecutter.basePackage }}.biz.auth.token;

import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.biz.auth.user.User;
import {{ cookiecutter.basePackage }}.biz.auth.user.register.RegisterResponse;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.response.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import {{ cookiecutter.namespace }}.annotation.Resource;
import java.time.Duration;


/**
 * Token管理
 */
@RestController
@RequestMapping("/auth/token")
public class TokenController extends AuthBaseController {

    /**
     * token刷新窗口期
     */
    private static final int WINDOW_MINUTES = 30;

    /**
     * token刷新次数限制
     */
    private static final int MAX_REFRESH_COUNT = 5;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    /**
     * 刷新/续约token
     */
    @LogOperation(name = "刷新/续约token", biz = "auth")
    @PreAuthorize("@ck.hasPermit('auth:token:renew')")
    @RequestMapping("/renew")
    public R<RegisterResponse> renew() {
        User user = getLoggedInUser();
        RegisterResponse response = refreshToken(user.getId().toString());
        return response == null ? R.fail("令牌刷新次数过多") : R.ok(response);
    }

    /**
     * 防止token被过度重复刷新, 记录token刷新次数
     *
     * @param userId 用户id
     */
    private RegisterResponse refreshToken(String userId) {
        RegisterResponse response = new RegisterResponse();
        String key = "refresh:" + userId;
        String s = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(s)) {
            response.setToken(JwtUtil.createJwt(userId));
            stringRedisTemplate.opsForValue().increment(key);
            stringRedisTemplate.expire(key, Duration.ofMinutes(WINDOW_MINUTES));
        } else if (Integer.parseInt(s) < MAX_REFRESH_COUNT) {
            response.setToken(JwtUtil.createJwt(userId));
            stringRedisTemplate.opsForValue().increment(key);
        } else {
            return null;
        }
        return response;
    }
}
