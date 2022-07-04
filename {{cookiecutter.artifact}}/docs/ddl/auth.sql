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
  `sort_no` int unsigned DEFAULT '1' COMMENT '显示顺序',
  `description` varchar(255) DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name_parent_id` (`name`,`parent_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户组';

-- ----------------------------
-- Records of auth_group
-- ----------------------------
BEGIN;
INSERT INTO `auth_group` (`id`, `create_time`, `update_time`, `name`, `parent_id`, `sort_no`, `description`) VALUES (1, '2022-07-04 14:04:28', '2022-07-04 14:10:21', 'root', 0, 1, '根用户组');
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_group_id_permission_id` (`group_id`,`permission_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户组权限';

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
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_group_id_role_id` (`group_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户组角色关联';

-- ----------------------------
-- Records of auth_group_role
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
  `description` varchar(255) DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限';

-- ----------------------------
-- Records of auth_permission
-- ----------------------------
BEGIN;
INSERT INTO `auth_permission` (`id`, `create_time`, `update_time`, `name`, `code`, `description`) VALUES (1, '2022-06-09 09:41:02', '2022-07-04 14:06:38', '上帝权限', '*', NULL);
INSERT INTO `auth_permission` (`id`, `create_time`, `update_time`, `name`, `code`, `description`) VALUES (2, '2022-06-14 16:37:46', '2022-07-04 14:06:31', '用户组查询', 'auth:groups:list', NULL);
INSERT INTO `auth_permission` (`id`, `create_time`, `update_time`, `name`, `code`, `description`) VALUES (3, '2022-06-14 16:38:25', '2022-07-04 14:06:31', '获取用户组树状结构', 'auth:groups:tree', NULL);
INSERT INTO `auth_permission` (`id`, `create_time`, `update_time`, `name`, `code`, `description`) VALUES (4, '2022-06-14 16:39:23', '2022-07-04 14:06:31', '新增用户组', 'auth:groups:add', NULL);
INSERT INTO `auth_permission` (`id`, `create_time`, `update_time`, `name`, `code`, `description`) VALUES (5, '2022-06-14 16:39:37', '2022-07-04 14:06:32', '按ID查询用户组', 'auth:groups:get', NULL);
INSERT INTO `auth_permission` (`id`, `create_time`, `update_time`, `name`, `code`, `description`) VALUES (6, '2022-06-14 16:39:54', '2022-07-04 14:06:32', '按ID更新用户组', 'auth:groups:update', NULL);
INSERT INTO `auth_permission` (`id`, `create_time`, `update_time`, `name`, `code`, `description`) VALUES (7, '2022-06-14 16:40:20', '2022-07-04 14:06:32', '按ID删除用户组', 'auth:groups:delete', NULL);
INSERT INTO `auth_permission` (`id`, `create_time`, `update_time`, `name`, `code`, `description`) VALUES (8, '2022-06-14 16:40:53', '2022-07-04 14:06:32', '用户组迁移', 'auth:groups:migrate', NULL);
INSERT INTO `auth_permission` (`id`, `create_time`, `update_time`, `name`, `code`, `description`) VALUES (9, '2022-06-14 16:41:08', '2022-07-04 14:06:32', '角色查询', 'auth:roles:list', NULL);
INSERT INTO `auth_permission` (`id`, `create_time`, `update_time`, `name`, `code`, `description`) VALUES (10, '2022-06-14 16:41:24', '2022-07-04 14:06:32', '新增角色', 'auth:roles:add', NULL);
INSERT INTO `auth_permission` (`id`, `create_time`, `update_time`, `name`, `code`, `description`) VALUES (11, '2022-06-14 16:42:06', '2022-07-04 14:06:32', '按ID查询角色', 'auth:roles:get', NULL);
INSERT INTO `auth_permission` (`id`, `create_time`, `update_time`, `name`, `code`, `description`) VALUES (12, '2022-06-14 16:42:28', '2022-07-04 14:06:33', '按ID更新角色', 'auth:roles:update', NULL);
INSERT INTO `auth_permission` (`id`, `create_time`, `update_time`, `name`, `code`, `description`) VALUES (13, '2022-06-22 13:36:38', '2022-07-04 14:06:33', '按ID删除角色', 'auth:roles:delete', NULL);
INSERT INTO `auth_permission` (`id`, `create_time`, `update_time`, `name`, `code`, `description`) VALUES (14, '2022-07-04 14:07:20', '2022-07-04 14:08:48', '查询用户拥有所有权限信息', 'auth:permissions:list', NULL);
INSERT INTO `auth_permission` (`id`, `create_time`, `update_time`, `name`, `code`, `description`) VALUES (15, '2022-07-04 14:07:47', '2022-07-04 14:07:47', '新增权限', 'auth:permissions:add', NULL);
INSERT INTO `auth_permission` (`id`, `create_time`, `update_time`, `name`, `code`, `description`) VALUES (16, '2022-07-04 14:08:04', '2022-07-04 14:08:04', '按ID查询权限', 'auth:permissions:get', NULL);
INSERT INTO `auth_permission` (`id`, `create_time`, `update_time`, `name`, `code`, `description`) VALUES (17, '2022-07-04 14:08:19', '2022-07-04 14:08:19', '按ID更新权限', 'auth:permissions:update', NULL);
INSERT INTO `auth_permission` (`id`, `create_time`, `update_time`, `name`, `code`, `description`) VALUES (18, '2022-07-04 14:08:38', '2022-07-04 14:08:38', '按ID删除权限', 'auth:permissions:delete', NULL);
COMMIT;

-- ----------------------------
-- Table structure for auth_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色代码',
  `description` varchar(255) DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色';

-- ----------------------------
-- Records of auth_role
-- ----------------------------
BEGIN;
INSERT INTO `auth_role` (`id`, `create_time`, `update_time`, `name`, `code`, `description`) VALUES (1, '2022-06-14 16:35:04', '2022-06-21 23:31:59', '上帝角色', 'god', '拥有系统所有权限');
INSERT INTO `auth_role` (`id`, `create_time`, `update_time`, `name`, `code`, `description`) VALUES (2, '2022-06-20 13:45:34', '2022-06-20 13:45:34', '管理员', 'admin', NULL);
INSERT INTO `auth_role` (`id`, `create_time`, `update_time`, `name`, `code`, `description`) VALUES (3, '2022-06-21 23:28:54', '2022-06-21 23:40:15', '来宾', 'guest', '基本登录、登出、密码管理');
INSERT INTO `auth_role` (`id`, `create_time`, `update_time`, `name`, `code`, `description`) VALUES (4, '2022-06-21 23:29:17', '2022-06-21 23:31:18', '只读角色', 'readonly', '所有资源只读，无法修改删除和更新');
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_id_permission_id` (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色权限';

-- ----------------------------
-- Records of auth_role_permission
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for auth_user
-- ----------------------------
DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `login_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录用户名',
  `pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '真实姓名',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号码',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮箱地址',
  `telephone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '固定电话',
  `user_lock` tinyint NOT NULL DEFAULT '0' COMMENT '锁1-上锁 0-解锁',
  `is_super` tinyint NOT NULL DEFAULT '0' COMMENT '超级管理员',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_login_name` (`login_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户';

-- ----------------------------
-- Records of auth_user
-- ----------------------------
BEGIN;
INSERT INTO `auth_user` (`id`, `create_time`, `update_time`, `login_name`, `pwd`, `real_name`, `mobile`, `email`, `telephone`, `user_lock`, `is_super`, `expire_time`) VALUES (1, '2022-06-08 22:19:10', '2022-06-09 21:32:41', 'zhengxiang', '$2a$10$rgOjIXgsgQnox3GcCV7L2.aVNcDaXqgzKC.GWDu3PwGF12H83qfdi', '郑翔', '13111111111', 'me@example.com', '057112345678', 0, 0, '2099-12-31 23:59:59');
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id_group_id` (`user_id`,`group_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户-用户组关联';

-- ----------------------------
-- Records of auth_user_group
-- ----------------------------
BEGIN;
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id_permission_id` (`user_id`,`permission_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户-权限关联';

-- ----------------------------
-- Records of auth_user_permission
-- ----------------------------
BEGIN;
INSERT INTO `auth_user_permission` (`id`, `create_time`, `update_time`, `user_id`, `permission_id`) VALUES (1, '2022-06-16 06:57:17', '2022-07-04 13:49:06', 1, 1);
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id_role_id` (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户和角色关联表';

-- ----------------------------
-- Records of auth_user_role
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
