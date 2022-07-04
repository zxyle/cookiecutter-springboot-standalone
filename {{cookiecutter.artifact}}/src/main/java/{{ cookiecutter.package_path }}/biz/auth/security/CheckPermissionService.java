package {{ cookiecutter.basePackage }}.biz.auth.security;

import {{ cookiecutter.basePackage }}.biz.auth.constant.AuthConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;


@Service("ck")
public class CheckPermissionService {

    private final WildcardPermission wildcardPermission = new WildcardPermission();

    @Resource
    StringRedisTemplate stringRedisTemplate;

    public boolean hasPermit(String permission) {
        if (StringUtils.isEmpty(permission)) {
            return false;
        }

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        String key = "permissions:" + userId;
        String value = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(value))
            return false;

        List<String> permissions = Arrays.asList(value.split(AuthConstant.DELIMITER));
        return wildcardPermission.isPermit(permission, permissions);
    }
}
