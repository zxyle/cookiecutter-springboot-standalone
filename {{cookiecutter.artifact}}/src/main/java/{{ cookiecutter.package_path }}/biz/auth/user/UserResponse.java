package {{ cookiecutter.basePackage }}.biz.auth.user;

import cn.hutool.core.util.DesensitizedUtil;
import {{ cookiecutter.basePackage }}.biz.auth.group.Group;
import {{ cookiecutter.basePackage }}.biz.auth.permission.Permission;
import {{ cookiecutter.basePackage }}.biz.auth.role.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * 用户信息响应
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserResponse extends User {

    private static final long serialVersionUID = 1L;

    /**
     * 角色列表
     */
    private List<Role> roles;

    /**
     * 用户组列表
     */
    private List<Group> groups;

    /**
     * 权限列表
     */
    private List<Permission> permissions;

    public UserResponse() {
    }

    public UserResponse(User user) {
        BeanUtils.copyProperties(user, this);
    }

    /**
     * 重写父类的get方法，对敏感信息进行脱敏
     *
     * @return 脱敏后邮箱
     */
    @Override
    public String getEmail() {
        if (StringUtils.isNotBlank(super.getEmail())) {
            super.setEmail(DesensitizedUtil.email(super.getEmail()));
        }

        return super.getEmail();
    }

    @Override
    public String getMobile() {
        if (StringUtils.isNotBlank(super.getMobile())) {
            super.setMobile(DesensitizedUtil.mobilePhone(super.getMobile()));
        }

        return super.getMobile();
    }
}
