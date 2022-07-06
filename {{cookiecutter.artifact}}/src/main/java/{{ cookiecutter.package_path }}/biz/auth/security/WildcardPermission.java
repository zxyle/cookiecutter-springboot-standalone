package {{ cookiecutter.basePackage }}.biz.auth.security;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.List;

/**
 * 无级权限校验（默认为三级权限体系）
 */
public class WildcardPermission {

    private final Integer level;

    private static final String ASTERISK = "*";
    private static final Integer DEFAULT_LEVEL = 3;

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
            if (!parts[i].equals(ASTERISK) && !parts[i].equalsIgnoreCase(wp.getParts()[i]))
                return false;
        }
        return true;
    }

    private boolean isAllowed(String p) {
        return isAllowed(parse(p));
    }

    private WildcardPermission parse(String permission) {
        WildcardPermission wildcardPermission = new WildcardPermission();
        wildcardPermission.setParts(permission.split(DELIMITER, -1));
        return wildcardPermission;
    }


    /**
     * 填充权限通配符
     *
     * @param wildcard 权限通配符
     * @return 完整权限通配符
     */
    private String padding(String wildcard) {
        int countDelimiter = StrUtil.count(wildcard, DELIMITER);
        return wildcard + StrUtil.repeat(DELIMITER, (level - countDelimiter - 1));
    }

    /**
     * 格式化通配符
     *
     * @param wildcard 权限通配符
     */
    private WildcardPermission format(String wildcard) {
        WildcardPermission wp = new WildcardPermission();
        String[] strings = padding(wildcard).split(DELIMITER, -1);
        wp.setParts(Arrays.stream(strings)
                .map(part -> StrUtil.isEmpty(part) ? ASTERISK : part).toArray(String[]::new));
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
        if (permissionPatterns == null || permissionPatterns.size() == 0 || permission == null || permission.equals(""))
            return false;

        for (String p : permissionPatterns) {
            if (StrUtil.isBlank(p)) continue;

            if (format(p).isAllowed(permission)) {
                return true;
            }
        }
        return false;
    }

}
