SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for auth_group
-- ----------------------------
DROP TABLE IF EXISTS `auth_group`;
CREATE TABLE `auth_group` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户组名称',
  `parent_id` bigint unsigned NOT NULL COMMENT '上级用户组ID',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '描述信息',
  `sort` smallint unsigned NOT NULL DEFAULT '1' COMMENT '显示顺序',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_name_parent_id` (`name`,`parent_id`) USING BTREE COMMENT '同级不允许同名用户组'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户组';

-- ----------------------------
-- Records of auth_group
-- ----------------------------
BEGIN;
INSERT INTO `auth_group` (`id`, `name`, `parent_id`, `description`, `sort`) VALUES (1, '根用户组', 0, '根用户组', 1);
INSERT INTO `auth_group` (`id`, `name`, `parent_id`, `description`, `sort`) VALUES (2, '管理员组', 1, '管理员组', 1);
COMMIT;

-- ----------------------------
-- Table structure for auth_group_permission
-- ----------------------------
DROP TABLE IF EXISTS `auth_group_permission`;
CREATE TABLE `auth_group_permission` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `group_id` bigint unsigned NOT NULL COMMENT '用户组ID',
  `permission_id` bigint unsigned NOT NULL COMMENT '权限ID',
  `remark` varchar(255) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_group_id_permission_id` (`group_id`,`permission_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户组权限';

-- ----------------------------
-- Records of auth_group_permission
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for auth_group_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_group_role`;
CREATE TABLE `auth_group_role` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `group_id` bigint unsigned NOT NULL COMMENT '用户组ID',
  `role_id` bigint unsigned NOT NULL COMMENT '角色ID',
  `remark` varchar(255) DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_group_id_role_id` (`group_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户组角色关联';

-- ----------------------------
-- Records of auth_group_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for auth_password_history
-- ----------------------------
DROP TABLE IF EXISTS `auth_password_history`;
CREATE TABLE `auth_password_history` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
  `kind` enum('initial','reset','forget','change') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码修改类型： 初始密码、重置密码、找回密码、修改密码',
  `edited_by` enum('user','admin') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改人',
  `after_pwd` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改后密码',
  `before_pwd` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改前密码',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='密码历史表';

-- ----------------------------
-- Records of auth_password_history
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for auth_permission
-- ----------------------------
DROP TABLE IF EXISTS `auth_permission`;
CREATE TABLE `auth_permission` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限名称',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限代码',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '描述信息',
  `parent_id` bigint unsigned NOT NULL DEFAULT '1' COMMENT '父级权限ID',
  `kind` tinyint unsigned NOT NULL COMMENT '权限类型（1：页面/路由，2：接口/功能 3：按钮/组件）',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '页面路由（用于前端控制）',
  `sort` smallint unsigned NOT NULL DEFAULT '1' COMMENT '显示顺序',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限';

-- ----------------------------
-- Records of auth_permission
-- ----------------------------
BEGIN;
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (1, '上帝权限', '*', NULL, 0, 0, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (2, 'auth模块所有权限', 'auth:*', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (3, '用户管理', 'auth:user:*', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (4, '分页查询用户', 'auth:user:list', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (5, '新增用户', 'auth:user:add', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (6, '按ID查询用户', 'auth:user:get', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (7, '按ID删除用户', 'auth:user:delete', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (8, '禁用用户', 'auth:user:disable', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (9, '启用用户', 'auth:user:enable', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (10, '用户踢下线', 'auth:user:kick', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (11, '用户注册', 'auth:user:register', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (12, '用户登录', 'auth:user:login', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (13, '退出登录', 'auth:user:logout', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (14, '检查用户名占用', 'auth:user:check', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (15, '随机生成用户名', 'auth:user:random', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (16, '密码管理', 'auth:password:*', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (17, '使用旧密码方式修改密码', 'auth:password:change', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (18, '忘记/找回密码（通过短信或邮件验证码）', 'auth:password:forget', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (19, '重置密码', 'auth:password:reset', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (20, '密码复杂度计算', 'auth:password:complexity', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (21, '随机生成密码', 'auth:password:random', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (22, '用户信息管理', 'auth:profile:*', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (23, '获取当前用户信息', 'auth:profile:get', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (24, '更新当前用户信息', 'auth:profile:update', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (25, '角色管理', 'auth:roles:*', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (26, '角色列表查询', 'auth:roles:list', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (27, '所有角色', 'auth:roles:all', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (28, '新增角色', 'auth:roles:add', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (29, '按ID查询角色', 'auth:roles:get', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (30, '按ID更新角色', 'auth:roles:update', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (31, '按ID删除角色', 'auth:roles:delete', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (32, '用户组管理', 'auth:groups:*', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (33, '用户组查询', 'auth:groups:list', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (34, '获取用户组树状结构', 'auth:groups:tree', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (35, '新增用户组', 'auth:groups:add', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (36, '按ID查询用户组', 'auth:groups:get', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (37, '按ID更新用户组', 'auth:groups:update', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (38, '按ID删除用户组', 'auth:groups:delete', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (39, '用户组迁移', 'auth:groups:migrate', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (40, '权限管理', 'auth:permissions:*', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (41, '获取权限树', 'auth:permissions:tree', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (42, '查询用户拥有所有权限信息', 'auth:permissions:list', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (43, '新增权限', 'auth:permissions:add', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (44, '按ID查询权限', 'auth:permissions:get', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (45, '按ID更新权限', 'auth:permissions:update', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (46, '按ID删除权限', 'auth:permissions:delete', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (47, 'Token管理', 'auth:token:*', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (48, '刷新/续约token', 'auth:token:renew', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (49, 'sys模块所有权限', 'sys:*', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (50, '友链管理', 'sys:friendly:*', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (51, '获取友链列表', 'sys:friendly:list', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (52, '添加友链', 'sys:friendly:add', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (53, '删除友链', 'sys:friendly:delete', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (54, '更新友链', 'sys:friendly:update', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (55, '字典管理', 'sys:dict:*', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (56, '新增字典', 'sys:dict:add', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (57, '按ID查询字典', 'sys:dict:get', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (58, '按ID更新字典', 'sys:dict:update', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (59, '按ID删除字典', 'sys:dict:delete', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (60, '系统信息管理', 'sys:info:*', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (61, '新增系统信息', 'sys:info:add', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (62, '按ID更新系统信息', 'sys:info:update', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (63, '按ID删除系统信息', 'sys:info:delete', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (64, '白名单管理', 'sys:whitelist:*', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (65, '白名单列表分页查询', 'sys:whitelist:list', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (66, '新增IP白名单', 'sys:whitelist:add', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (67, '按ID查询IP白名单', 'sys:whitelist:get', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (68, '按ID更新IP白名单', 'sys:whitelist:update', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (69, '按ID删除IP白名单', 'sys:whitelist:delete', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (70, 'Excel导出白名单', 'sys:whitelist:export', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (71, '黑名单管理', 'sys:blacklist:*', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (72, '黑名单列表分页查询', 'sys:blacklist:list', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (73, '新增IP黑名单', 'sys:blacklist:add', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (74, '按ID查询IP黑名单', 'sys:blacklist:get', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (75, '按ID更新IP黑名单', 'sys:blacklist:update', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (76, '按ID删除IP黑名单', 'sys:blacklist:delete', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (77, 'Excel导出黑名单', 'sys:blacklist:export', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (78, '发布版本管理', 'sys:release:*', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (79, '发布版本分页查询', 'sys:release:list', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (80, '新增发布版本', 'sys:release:add', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (81, '按ID查询发布版本', 'sys:release:get', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (82, '按ID更新发布版本', 'sys:release:update', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (83, '按ID删除发布版本', 'sys:release:delete', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (84, '系统设置管理', 'sys:setting:*', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (85, '系统设置分页查询', 'sys:setting:list', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (86, '新增系统设置', 'sys:setting:add', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (87, '按ID查询系统设置', 'sys:setting:get', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (88, '按ID更新系统设置', 'sys:setting:update', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (89, '按ID删除系统设置', 'sys:setting:delete', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (90, 'redis管理', 'sys:keys:*', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (91, '获取key列表', 'sys:keys:list', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (92, '删除key', 'sys:keys:delete', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (93, '获取key的值', 'sys:keys:get', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (94, '设置key的值', 'sys:keys:set', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (95, 'key重命名', 'sys:keys:rename', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (96, '设置过期时间', 'sys:keys:expire', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (97, '验证码发送记录管理', 'sys:verification:*', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (98, '验证码发送记录分页查询', 'sys:verification:list', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (99, '新增验证码发送记录', 'sys:verification:add', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (100, '按ID查询验证码发送记录', 'sys:verification:get', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (101, '按ID更新验证码发送记录', 'sys:verification:update', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (102, '按ID删除验证码发送记录', 'sys:verification:delete', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (103, '登录记录管理', 'sys:login:*', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (104, '登录记录分页查询', 'sys:login:list', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (105, '新增登录记录', 'sys:login:add', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (106, '按ID查询登录记录', 'sys:login:get', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (107, '按ID更新登录记录', 'sys:login:update', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (108, '按ID删除登录记录', 'sys:login:delete', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (109, '操作记录管理', 'sys:operate:*', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (110, '操作记录分页查询', 'sys:operate:list', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (111, '新增操作记录', 'sys:operate:add', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (112, '按ID查询操作记录', 'sys:operate:get', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (113, '按ID更新操作记录', 'sys:operate:update', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (114, '按ID删除操作记录', 'sys:operate:delete', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (115, '定时任务管理', 'sys:task:*', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (116, '定时任务分页查询', 'sys:task:list', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (117, '新增定时任务', 'sys:task:add', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (118, '按ID查询定时任务', 'sys:task:get', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (119, '按ID更新定时任务', 'sys:task:update', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (120, '按ID删除定时任务', 'sys:task:delete', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (121, '意见反馈所有权限', 'sys:feedbacks:*', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (122, '意见反馈列表分页查询', 'sys:feedbacks:list', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (123, '新增意见反馈', 'sys:feedbacks:add', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (124, '按ID查询意见反馈', 'sys:feedbacks:get', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (125, '按ID更新意见反馈', 'sys:feedbacks:update', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (126, '按ID删除意见反馈', 'sys:feedbacks:delete', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (127, 'Excel数据导出意见反馈', 'sys:feedbacks:export', NULL, 1, 2, NULL, 1);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`, `parent_id`, `kind`, `path`, `sort`) VALUES (128, 'Excel数据导入意见反馈', 'sys:feedbacks:upload', NULL, 1, 2, NULL, 1);
COMMIT;

-- ----------------------------
-- Table structure for auth_profile
-- ----------------------------
DROP TABLE IF EXISTS `auth_profile`;
CREATE TABLE `auth_profile` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `age` tinyint unsigned DEFAULT NULL COMMENT '年龄',
  `gender` enum('男','女') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '性别',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '头像',
  `nickname` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '昵称/名字/真实姓名（只用于展示）',
  `qq` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'QQ',
  `wechat` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '微信',
  `weibo` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '微博',
  `intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '简介',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '地址',
  `region` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '地区',
  `school` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '学校',
  `education` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '学历',
  `major` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '专业',
  `company` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公司',
  `position` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '职位',
  `industry` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '行业',
  `profession` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '职业',
  `telephone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '固定电话',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息';

-- ----------------------------
-- Records of auth_profile
-- ----------------------------
BEGIN;
INSERT INTO `auth_profile` (`id`, `user_id`, `birthday`, `age`, `gender`, `avatar`, `nickname`, `qq`, `wechat`, `weibo`, `intro`, `address`, `region`, `school`, `education`, `major`, `company`, `position`, `industry`, `profession`, `telephone`) VALUES (1, 1, '2023-01-01', 0, '男', NULL, NULL, NULL, NULL, NULL, NULL, '杭州市', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `auth_profile` (`id`, `user_id`, `birthday`, `age`, `gender`, `avatar`, `nickname`, `qq`, `wechat`, `weibo`, `intro`, `address`, `region`, `school`, `education`, `major`, `company`, `position`, `industry`, `profession`, `telephone`) VALUES (2, 2, '2023-01-01', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for auth_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色代码',
  `description` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_code` (`code`) USING BTREE,
  UNIQUE KEY `uk_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色';

-- ----------------------------
-- Records of auth_role
-- ----------------------------
BEGIN;
INSERT INTO `auth_role` (`id`, `name`, `code`, `description`) VALUES (1, '上帝角色', 'god', '拥有系统所有权限');
INSERT INTO `auth_role` (`id`, `name`, `code`, `description`) VALUES (2, '管理员', 'admin', '管理员权限');
INSERT INTO `auth_role` (`id`, `name`, `code`, `description`) VALUES (3, '注册用户', 'user', '已注册用户');
INSERT INTO `auth_role` (`id`, `name`, `code`, `description`) VALUES (4, '来宾', 'guest', '注册');
INSERT INTO `auth_role` (`id`, `name`, `code`, `description`) VALUES (5, '组管理员', 'group-admin', '管理所在用户组');
INSERT INTO `auth_role` (`id`, `name`, `code`, `description`) VALUES (6, '普通组员', 'group-member', '用户组成员');
COMMIT;

-- ----------------------------
-- Table structure for auth_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_permission`;
CREATE TABLE `auth_role_permission` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `role_id` bigint unsigned NOT NULL COMMENT '角色id',
  `permission_id` bigint unsigned NOT NULL COMMENT '权限id',
  `remark` varchar(255) DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_role_id_permission_id` (`role_id`,`permission_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色权限';

-- ----------------------------
-- Records of auth_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `auth_role_permission` (`id`, `role_id`, `permission_id`, `remark`) VALUES (1, 2, 1, '管理员拥有所有权限');
INSERT INTO `auth_role_permission` (`id`, `role_id`, `permission_id`, `remark`) VALUES (2, 3, 12, '注册用户拥有用户登录');
INSERT INTO `auth_role_permission` (`id`, `role_id`, `permission_id`, `remark`) VALUES (3, 3, 13, '注册用户拥有退出登录');
INSERT INTO `auth_role_permission` (`id`, `role_id`, `permission_id`, `remark`) VALUES (4, 3, 17, '注册用户拥有修改密码');
INSERT INTO `auth_role_permission` (`id`, `role_id`, `permission_id`, `remark`) VALUES (5, 3, 23, '注册用户拥有获取当前用户信息');
INSERT INTO `auth_role_permission` (`id`, `role_id`, `permission_id`, `remark`) VALUES (6, 3, 24, '注册用户拥有更新当前用户信息');
INSERT INTO `auth_role_permission` (`id`, `role_id`, `permission_id`, `remark`) VALUES (7, 3, 48, '注册用户拥有刷新/续约token');
INSERT INTO `auth_role_permission` (`id`, `role_id`, `permission_id`, `remark`) VALUES (8, 5, 3, '组管理员拥有用户管理');
INSERT INTO `auth_role_permission` (`id`, `role_id`, `permission_id`, `remark`) VALUES (9, 5, 32, '组管理员拥有用户组管理');
INSERT INTO `auth_role_permission` (`id`, `role_id`, `permission_id`, `remark`) VALUES (10, 5, 19, '组管理员拥有重置密码');
COMMIT;

-- ----------------------------
-- Table structure for auth_user
-- ----------------------------
DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '登录用户名',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号码',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮箱地址',
  `pwd` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `nickname` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '昵称/名字/真实姓名（只用于展示）',
  `locked` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '账号是否锁定 （1-上锁 0-未锁）',
  `is_super` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否为超级管理员',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `pwd_change_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '密码上次修改时间',
  `enabled` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '账号可用 1-启用 0-禁用',
  `must_change_pwd` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否需要修改密码',
  `registered_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_email` (`email`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户';

-- ----------------------------
-- Records of auth_user
-- ----------------------------
BEGIN;
INSERT INTO `auth_user` (`id`, `login_name`, `pwd`, `real_name`, `mobile`, `email`, `user_lock`, `is_super`, `expire_time`, `pwd_change_time`, `enabled`) VALUES (1, 'admin', '$2a$10$2R/BL6V3lGNRAE2KeyYK8eZsFjKVr2RS8P8yduz3JywSX22pgv7ge', 'admin', '13111111111', 'admin@example.com', 0, 1, '2099-12-31 23:59:59', '2023-01-30 10:30:56', 1);
INSERT INTO `auth_user` (`id`, `login_name`, `pwd`, `real_name`, `mobile`, `email`, `user_lock`, `is_super`, `expire_time`, `pwd_change_time`, `enabled`) VALUES (2, 'zheng', '$2a$10$veHMsJJZtiFt0jyLOK9hAuKS1yejN1dsD7mGuAJ8rQfK7KUkXslYC', 'zheng', '15068931037', 'zheng@example.com', 0, 0, NULL, '2023-02-14 20:34:45', 1);
COMMIT;

-- ----------------------------
-- Table structure for auth_user_group
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_group`;
CREATE TABLE `auth_user_group` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
  `group_id` bigint unsigned NOT NULL COMMENT '用户组ID',
  `remark` varchar(255) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '注释',
  `admin` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否是该组管理员',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_user_id_group_id` (`user_id`,`group_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户-用户组关联';

-- ----------------------------
-- Records of auth_user_group
-- ----------------------------
BEGIN;
INSERT INTO `auth_user_group` (`id`, `user_id`, `group_id`, `remark`) VALUES (1, 1, 2, NULL);
COMMIT;

-- ----------------------------
-- Table structure for auth_user_permission
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_permission`;
CREATE TABLE `auth_user_permission` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
  `permission_id` bigint unsigned NOT NULL COMMENT '权限ID',
  `remark` varchar(255) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_user_id_permission_id` (`user_id`,`permission_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户-权限关联';

-- ----------------------------
-- Records of auth_user_permission
-- ----------------------------
BEGIN;
INSERT INTO `auth_user_permission` (`id`, `user_id`, `permission_id`, `remark`) VALUES (1, 1, 1, NULL);
COMMIT;

-- ----------------------------
-- Table structure for auth_user_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_role`;
CREATE TABLE `auth_user_role` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `remark` varchar(255) DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_user_id_role_id` (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户和角色关联表';

-- ----------------------------
-- Records of auth_user_role
-- ----------------------------
BEGIN;
INSERT INTO `auth_user_role` (`id`, `user_id`, `role_id`, `remark`) VALUES (1, 1, 5, NULL);
INSERT INTO `auth_user_role` (`id`, `user_id`, `role_id`, `remark`) VALUES (2, 2, 3, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
