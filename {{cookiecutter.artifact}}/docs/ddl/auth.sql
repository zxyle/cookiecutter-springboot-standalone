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
  `sort` smallint unsigned DEFAULT '1' COMMENT '显示顺序',
  `description` varchar(255) DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name_parent_id` (`name`,`parent_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户组';

-- ----------------------------
-- Records of auth_group
-- ----------------------------
BEGIN;
INSERT INTO `auth_group` (`id`, `name`, `parent_id`, `sort`, `description`) VALUES (1, 'root', 0, 1, '根用户组');
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
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`) VALUES (1, '上帝权限', '*', NULL);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`) VALUES (2, '用户组查询', 'auth:groups:list', NULL);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`) VALUES (3, '获取用户组树状结构', 'auth:groups:tree', NULL);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`) VALUES (4, '新增用户组', 'auth:groups:add', NULL);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`) VALUES (5, '按ID查询用户组', 'auth:groups:get', NULL);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`) VALUES (6, '按ID更新用户组', 'auth:groups:update', NULL);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`) VALUES (7, '按ID删除用户组', 'auth:groups:delete', NULL);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`) VALUES (8, '用户组迁移', 'auth:groups:migrate', NULL);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`) VALUES (9, '角色查询', 'auth:roles:list', NULL);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`) VALUES (10, '新增角色', 'auth:roles:add', NULL);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`) VALUES (11, '按ID查询角色', 'auth:roles:get', NULL);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`) VALUES (12, '按ID更新角色', 'auth:roles:update', NULL);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`) VALUES (13, '按ID删除角色', 'auth:roles:delete', NULL);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`) VALUES (14, '查询用户拥有所有权限信息', 'auth:permissions:list', NULL);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`) VALUES (15, '新增权限', 'auth:permissions:add', NULL);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`) VALUES (16, '按ID查询权限', 'auth:permissions:get', NULL);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`) VALUES (17, '按ID更新权限', 'auth:permissions:update', NULL);
INSERT INTO `auth_permission` (`id`, `name`, `code`, `description`) VALUES (18, '按ID删除权限', 'auth:permissions:delete', NULL);
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
INSERT INTO `auth_role` (`id`, `name`, `code`, `description`) VALUES (1, '上帝角色', 'god', '拥有系统所有权限');
INSERT INTO `auth_role` (`id`, `name`, `code`, `description`) VALUES (2, '管理员', 'admin', '管理员权限');
INSERT INTO `auth_role` (`id`, `name`, `code`, `description`) VALUES (3, '来宾', 'guest', '基本登录、登出、密码管理');
INSERT INTO `auth_role` (`id`, `name`, `code`, `description`) VALUES (4, '只读角色', 'readonly', '所有资源只读，无法修改删除和更新');
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
  `pwd_change_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '密码上次修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_login_name` (`login_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户';

-- ----------------------------
-- Records of auth_user
-- ----------------------------
BEGIN;
INSERT INTO `auth_user` (`id`, `login_name`, `pwd`, `real_name`, `mobile`, `email`, `telephone`, `user_lock`, `is_super`, `expire_time`) VALUES (1, 'admin', '$2a$10$2R/BL6V3lGNRAE2KeyYK8eZsFjKVr2RS8P8yduz3JywSX22pgv7ge', 'admin', '13111111111', 'admin@example.com', '057112345678', 0, 0, '2099-12-31 23:59:59');
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
INSERT INTO `auth_user_permission` (`id`, `user_id`, `permission_id`) VALUES (1, 1, 1);
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
  `gender` enum('male','female') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '性别',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `qq` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'QQ',
  `wechat` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '微信',
  `weibo` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '微博',
  `intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '简介',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `region` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '地区',
  `school` varchar(255) DEFAULT NULL COMMENT '学校',
  `education` varchar(255) DEFAULT NULL COMMENT '学历',
  `major` varchar(255) DEFAULT NULL COMMENT '专业',
  `company` varchar(255) DEFAULT NULL COMMENT '公司',
  `position` varchar(255) DEFAULT NULL COMMENT '职位',
  `industry` varchar(255) DEFAULT NULL COMMENT '行业',
  `profession` varchar(255) DEFAULT NULL COMMENT '职业',
  `telephone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '固定电话',
PRIMARY KEY (`id`),
UNIQUE KEY `uk_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息';

-- ----------------------------
-- Records of auth_profile
-- ----------------------------
BEGIN;
INSERT INTO `auth_profile` (`id`, `user_id`, `birthday`, `age`, `gender`, `avatar`, `nickname`, `qq`, `wechat`, `weibo`, `intro`, `address`, `region`, `school`, `education`, `major`, `company`, `position`, `industry`, `profession`, `telephone`) VALUES (1, 1, '1969-10-01', 53, 'female', NULL, NULL, NULL, NULL, NULL, NULL, '杭州市', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
