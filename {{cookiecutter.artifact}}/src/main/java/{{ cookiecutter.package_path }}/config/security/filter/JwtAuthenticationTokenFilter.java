package {{ cookiecutter.basePackage }}.config.security.filter;

import {{ cookiecutter.basePackage }}.common.constant.AuthConst;
import {{ cookiecutter.basePackage }}.biz.auth.user.UserService;
import {{ cookiecutter.basePackage }}.config.security.LoginUser;
import {{ cookiecutter.basePackage }}.biz.auth.token.JwtUtil;
import {{ cookiecutter.basePackage }}.biz.sys.setting.SettingService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import lombok.RequiredArgsConstructor;

import {{ cookiecutter.namespace }}.servlet.FilterChain;
import {{ cookiecutter.namespace }}.servlet.ServletException;
import {{ cookiecutter.namespace }}.servlet.http.HttpServletRequest;
import {{ cookiecutter.namespace }}.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * JWT 过滤器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    final UserService userService;
    final SettingService setting;
    final StringRedisTemplate stringRedisTemplate;
    final RedisScript<List> userPermissionsScript;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 获取token, 不存在则放行
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(token) || request.getServletPath().startsWith("/auth/login/")) {
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        }

        // 从Bearer token中获取jwt
        token = token.substring(AuthConst.AUTH_SCHEME.length());

        // 解析jwt, 解析失败则放行
        String userId;
        try {
            Claims claims = JwtUtil.parseJwt(token);
            userId = claims.getSubject();
        } catch (Exception ignored) {
            log.error("解析JWT失败: {}", token);
            filterChain.doFilter(request, response);
            return;
        }

        // 从redis中获取用户权限信息(根据jwt里存在的用户id)
        List<String> permissions;
        String key = String.format("user:%s:permissions", userId);
        String PermissionStr = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(PermissionStr)) {
            permissions = Arrays.asList(PermissionStr.split(AuthConst.DELIMITER));
        } else {
            List execute = stringRedisTemplate.execute(
                    userPermissionsScript,
                    Collections.singletonList(userId) // KEYS[1] = userId
            );
            permissions = (List<String>) execute;

            // 写入到缓存中，避免频繁执行smembers查询，并设置过期时间（考虑权限变更频率）
            // 注意：这里的过期时间需要根据实际情况调整，比如根据用户的权限变更频率来设置过期时间
            if (!permissions.isEmpty()) {
                stringRedisTemplate.opsForValue().set(key, String.join(AuthConst.DELIMITER, permissions),
                        1, TimeUnit.HOURS);
            }
        }

        // 获取权限信息封装到Authentication中
        Integer expireDays = setting.get("pwd.expire-days").getIntValue();
        LoginUser loginUser = new LoginUser(permissions, userService.findById(Integer.valueOf(userId)), expireDays);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 放行
        filterChain.doFilter(request, response);
    }
}
