package {{ cookiecutter.basePackage }}.config.security;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 无级权限校验（默认为三级权限体系）
 */
public class WildcardPermission {

    /**
     * 权限层级
     */
    private final Integer level;

    /**
     * 默认权限层级（模块:资源:操作）
     */
    private static final Integer DEFAULT_LEVEL = 3;

    /**
     * 权限通配符
     */
    private static final String ASTERISK = "*";

    /**
     * 权限标识分隔符
     */
    private static final String DELIMITER = ":";

    private String[] parts;

    public String[] getParts() {
        return parts;
    }

    public void setParts(String[] parts) {
        this.parts = parts;
    }

    public WildcardPermission() {
        this(DEFAULT_LEVEL);
    }

    public WildcardPermission(Integer level) {
        this.level = level;
    }

    private boolean isAllowed(WildcardPermission wp) {
        for (int i = 0; i < parts.length; i++) {
            if (!ASTERISK.equals(parts[i]) && !parts[i].equalsIgnoreCase(wp.getParts()[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean isAllowed(String p) {
        return isAllowed(parse(p));
    }

    /**
     * 解析权限通配符
     *
     * @param permission 权限标识符
     */
    private WildcardPermission parse(String permission) {
        WildcardPermission wildcardPermission = new WildcardPermission();
        wildcardPermission.setParts(permission.split(DELIMITER, -1));
        return wildcardPermission;
    }


    /**
     * 在后面填充权限通配符
     *
     * @param wildcard 权限通配符
     * @return 完整权限通配符
     */
    private String padding(String wildcard) {
        int countDelimiter = StringUtils.countMatches(wildcard, DELIMITER);
        return wildcard + StringUtils.repeat(DELIMITER, (level - countDelimiter - 1));
    }

    /**
     * 格式化通配符，补齐权限层级
     *
     * @param wildcard 权限通配符
     */
    private WildcardPermission format(String wildcard) {
        WildcardPermission wp = new WildcardPermission();
        String[] strings = padding(wildcard).split(DELIMITER, -1);
        wp.setParts(Arrays.stream(strings)
                .map(part -> StringUtils.isEmpty(part) ? ASTERISK : part).toArray(String[]::new));
        return wp;
    }

    /**
     * 判断用户是否具有该权限
     *
     * @param permission         待比对权限
     * @param permissionPatterns 用户已拥有的权限列表
     * @return true-有权限 false-无权限
     */
    public boolean isPermit(String permission, List<String> permissionPatterns) {
        if (CollectionUtils.isEmpty(permissionPatterns) || StringUtils.isEmpty(permission)) {
            return false;
        }
        for (String p : permissionPatterns) {
            if (StringUtils.isBlank(p)) {
                continue;
            }

            if (format(p).isAllowed(permission)) {
                return true;
            }
        }
        return false;
    }

}
