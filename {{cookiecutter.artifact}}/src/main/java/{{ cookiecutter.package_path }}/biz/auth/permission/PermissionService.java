package {{ cookiecutter.basePackage }}.biz.auth.permission;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import {{ cookiecutter.basePackage }}.common.constant.AuthConst;
import {{ cookiecutter.basePackage }}.biz.auth.group.permission.GroupPermissionService;
import {{ cookiecutter.basePackage }}.biz.auth.group.role.GroupRoleService;
import {{ cookiecutter.basePackage }}.biz.auth.role.Role;
import {{ cookiecutter.basePackage }}.biz.auth.role.permission.RolePermissionService;
import {{ cookiecutter.basePackage }}.biz.auth.user.group.UserGroupService;
import {{ cookiecutter.basePackage }}.biz.auth.user.permission.UserPermissionService;
import {{ cookiecutter.basePackage }}.biz.auth.user.group.UserGroup;
import {{ cookiecutter.basePackage }}.biz.auth.group.permission.GroupPermissionMapper;
import {{ cookiecutter.basePackage }}.biz.auth.role.RoleMapper;
import {{ cookiecutter.basePackage }}.biz.auth.role.permission.RolePermissionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {{ cookiecutter.basePackage }}.biz.auth.user.role.UserRoleService;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 权限信息 服务实现类
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "PermissionCache")
public class PermissionService extends ServiceImpl<PermissionMapper, Permission> {

    final UserPermissionService userPermissionService;
    final UserRoleService userRoleService;
    final UserGroupService userGroupService;
    final GroupPermissionService groupPermissionService;
    final GroupPermissionMapper groupPermissionMapper;
    final RolePermissionService rolePermissionService;
    final RolePermissionMapper rolePermissionMapper;
    final StringRedisTemplate stringRedisTemplate;
    final GroupRoleService groupRoleService;
    final RoleMapper roleMapper;

    /**
     * 获取用户所有权限名称
     *
     * @param userId 用户ID
     */
    public List<Permission> getAllPermissions(Integer userId, List<Integer> groupIds) {
        // 用户直接拥有的权限
        List<Permission> permissions = new ArrayList<>(userPermissionService.findPermissionsByUserId(userId));
        // 用户所在用户组拥有的权限
        if (CollectionUtils.isNotEmpty(groupIds)) {
            permissions.addAll(groupPermissionMapper.findPermissionsByGroupIds(groupIds));
        }
        return permissions.stream().distinct().{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};
    }

    /**
     * 新增权限
     *
     * @param permission 权限信息
     */
    public boolean create(Permission permission) {
        // 设置排序
        if (permission.getParentId() != null && permission.getSort() == null) {
            Integer sort = baseMapper.findMaxSortByParentId(permission.getParentId());
            permission.setSort(sort == null ? 1 : sort + 1);
        }
        return save(permission);
    }

    /**
     * 删除权限
     *
     * @param permissionId 权限ID
     */
    @CacheEvict(key = "#permissionId")
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Integer permissionId) {
        boolean s1 = groupPermissionService.deleteRelation(0, permissionId);
        boolean s2 = rolePermissionService.deleteRelation(0, permissionId);
        boolean s3 = userPermissionService.deleteRelation(0, permissionId);
        boolean s4 = removeById(permissionId);
        return (s1 && s2) && (s3 && s4);
    }

    /**
     * 查询用户所有权限码和用户所有角色码
     *
     * @param userId 用户ID
     */
    public List<String> getSecurityPermissions(Integer userId) {
        // 查询用户所在用户组
        List<UserGroup> groups = userGroupService.queryRelation(userId, 0);
        List<Integer> groupIds = groups.stream().map(UserGroup::getGroupId).{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};

        // 查询用户所有角色
        List<Role> roles = userRoleService.findRolesByUserId(userId);
        if (CollectionUtils.isNotEmpty(groupIds)) {
            roles.addAll(groupRoleService.findRolesByGroupIds(groupIds));
        }
        List<Integer> roleIds = roles.stream().map(Role::getId).distinct().{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};

        // 查询角色关联的权限
        List<Permission> allPermissions = new ArrayList<>(rolePermissionMapper.findPermissionsByRoleIds(roleIds));

        // 查询用户直接和用户组关联的权限
        List<Permission> permissions = new ArrayList<>(getAllPermissions(userId, groupIds));

        // 获取子权限
        List<Permission> permissionList = listAll();
        for (Permission permission : permissions) {
            allPermissions.addAll(getAllChildren(permissionList, permission.getId()));
        }
        allPermissions.addAll(permissions);

        // 组装权限码和角色码
        List<String> list = new ArrayList<>(allPermissions.size() + roles.size());
        list.addAll(allPermissions.stream().map(Permission::getCode).distinct().{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %});
        list.addAll(roles.stream().map(e -> "ROLE_" + e.getCode()).distinct().{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %});
        return list;
    }

    /**
     * 查询用户所有权限码和用户所有角色码（V2版本）
     *
     * @param userId 用户ID
     */
    public SecurityPermissions getSecurityPermissions2(Integer userId) {
        List<Integer> permissionIds = baseMapper.findPermissionIds(userId);

        List<Role> roles = roleMapper.findRolesByUserId(userId);
        List<String> roleCodes = roles.stream().map(Role::getCode).distinct().collect(Collectors.toList());
        List<Integer> roleIds = roles.stream().map(Role::getId).distinct().collect(Collectors.toList());

        // 获取子权限
        List<Permission> permissionList = listAll();
        List<Permission> children = new ArrayList<>();
        for (Integer permissionId : permissionIds) {
            Optional<Permission> optionalPermission = permissionList.stream().filter(p -> p.getId().equals(permissionId)).findFirst();
            optionalPermission.ifPresent(children::add);
            children.addAll(getAllChildren(permissionList, permissionId));
        }

        // 组装权限码和角色码
        List<String> list = new ArrayList<>(roleCodes.size() + children.size());
        list.addAll(children.stream().map(Permission::getCode).distinct().collect(Collectors.toList()));
        list.addAll(roleCodes.stream().map(e -> AuthBaseController.ROLE_PREFIX + e).distinct().collect(Collectors.toList()));
        return new SecurityPermissions(list, roleIds);
    }

    /**
     * 重新将新的权限码和角色码加载到redis中
     *
     * @param userId 用户ID
     */
    @Async
    public void refreshPermissions(Integer userId) {
        String key = AuthConst.KEY_PREFIX + userId;
        Boolean hasKey = stringRedisTemplate.hasKey(key);
        // 只对已登录用户进行权限刷新
        if (Boolean.TRUE.equals(hasKey)) {
            // 将权限码和角色码存入redis
            List<String> permissions = getSecurityPermissions(userId);
            String join = String.join(AuthConst.DELIMITER, permissions);
            stringRedisTemplate.opsForValue().set(key, join, 1, TimeUnit.DAYS);
        }
    }

    /**
     * 获取持有该权限的用户
     *
     * @param code 权限码
     */
    public List<Integer> holdPermission(String code) {
        List<Integer> users = new ArrayList<>();

        Set<String> keys = stringRedisTemplate.keys(AuthConst.KEY_PREFIX);
        if (null == keys) {
            return users;
        }

        for (String key : keys) {
            Integer userId = Integer.valueOf(key.split(":")[1]);
            String value = stringRedisTemplate.opsForValue().get(key);
            if (StringUtils.isNotBlank(value)) {
                List<String> permissions = Arrays.asList(value.split(AuthConst.DELIMITER));
                if (permissions.contains(code)) {
                    users.add(userId);
                }
            }
        }
        return users;
    }

    /**
     * 获取权限树
     *
     * @param list             待组装权限列表
     * @param rootPermissionId 根节点权限ID
     */
    public List<Tree<Integer>> getTree(List<Permission> list, Integer rootPermissionId) {
        TreeNodeConfig config = new TreeNodeConfig();
        config.setNameKey("name");
        config.setIdKey("id");
        config.setWeightKey("sort");
        return TreeUtil.build(list, rootPermissionId, config, (object, tree) -> {
            tree.setId(object.getId());// 必填属性
            tree.setParentId(object.getParentId());// 必填属性
            tree.setName(object.getName());
            // 扩展属性 ...
            tree.putExtra("path", object.getPath());
            tree.putExtra("kind", object.getKind());
            tree.putExtra("sort", object.getSort());
            tree.putExtra("code", object.getCode());
            tree.putExtra("createTime", object.getCreateTime());
        });
    }

    /**
     * 是否正在被使用
     *
     * @param permissionId 权限ID
     * @return true:正在被使用 false:未被使用
     */
    public boolean isAlreadyUsed(Integer permissionId) {
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Permission::getParentId, permissionId);
        if (exists(wrapper)) {
            return true;
        }

        Long userPermissions = userPermissionService.countRelation(null, permissionId);
        if (userPermissions > 0) {
            return true;
        }

        Long rolePermissions = rolePermissionService.countRelation(null, permissionId);
        if (rolePermissions > 0) {
            return true;
        }

        Long groupPermissions = groupPermissionService.countRelation(null, permissionId);
        return groupPermissions > 0;
    }

    /**
     * 递归获取所有子权限
     *
     * @param permissions 完整权限列表
     * @param rootId      根节点ID
     */
    public List<Permission> getAllChildren(List<Permission> permissions, Integer rootId) {
        List<Permission> children = new ArrayList<>();
        for (Permission permission : permissions) {
            if (permission.getParentId().equals(rootId)) {
                children.add(permission);
                children.addAll(getAllChildren(permissions, permission.getId()));
            }
        }
        return children;
    }

    public List<Permission> listAll() {
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Permission::getId, Permission::getCode, Permission::getParentId);
        return list(wrapper);
    }
}