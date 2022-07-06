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

    /**
     * 检查用户拥有的权限是否包含指定的权限
     *
     * @param permission 权限字符串
     * @return 是否有权限
     */
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

        List<String> patterns = Arrays.asList(value.split(AuthConstant.DELIMITER));
        return wildcardPermission.isPermit(permission, patterns);
    }
}
