package {{ cookiecutter.basePackage }}.config.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;


@Service("ck")
public class CheckPermissionService {

    private final WildcardPermission wildcardPermission = new WildcardPermission();

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

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        Collection<GrantedAuthority> authorities = authentication.getAuthorities();

        return wildcardPermission.isPermit(permission, authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
    }
}
