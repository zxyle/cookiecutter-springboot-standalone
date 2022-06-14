package {{ cookiecutter.basePackage }}.common.controller;

import {{ cookiecutter.basePackage }}.biz.auth.entity.User;
import {{ cookiecutter.basePackage }}.biz.auth.entity.UserGroup;
import {{ cookiecutter.basePackage }}.biz.auth.security.LoginUser;
import {{ cookiecutter.basePackage }}.biz.auth.service.IGroupService;
import {{ cookiecutter.basePackage }}.biz.auth.service.IUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 权限模块通用方法
 */
@Component
public class AuthBaseController {

    @Autowired
    IGroupService groupService;

    @Autowired
    IUserGroupService userGroupService;

    /**
     * 获取当前登录用户ID
     */
    public Long getUserId() {
        return getLoggedInUser().getId();
    }

    /**
     * 获取当前登录用户信息
     */
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return loginUser.getUser();
    }

    /**
     * 是否为平台超级管理员
     */
    public boolean isSuper() {
        User user = getLoggedInUser();
        return user.getIsSuper().equals(1);
    }

    /**
     * 获取当前登录用户名
     */
    public String getCurrentUsername() {
        User user = getLoggedInUser();
        return user.getLoginName();
    }

    /**
     * 获取当前登录用户所在的用户组ID
     */
    public Long getCurrentGroup() {
        User user = getLoggedInUser();
        Long userId = user.getId();
        // TODO 目前只支持一个用户对应一个用户组
        List<UserGroup> groups = userGroupService.queryRelation(userId, 0L);
        return groups.get(0).getGroupId();
    }

    /**
     * 获取当前用户组以及子用户组ID
     */
    public List<String> getSubGroupIds() {
        Long groupId = getCurrentGroup();
        return groupService.getSubGroups(groupId);
    }

    /**
     * 判断该用户组是否是其子用户组
     */
    public boolean isSubGroup(Long groupId) {
        List<String> subGroups = getSubGroupIds();
        return subGroups.contains(String.valueOf(groupId));
    }

}
