SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '行政区编码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '行政区名称',
  `parent` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '上级编码',
  `postcode` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮政编码',
  `level` tinyint NOT NULL COMMENT '级别',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_level` (`level`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='中国行政区划';

-- ----------------------------
-- Table structure for sys_blacklist
-- ----------------------------
DROP TABLE IF EXISTS `sys_blacklist`;
CREATE TABLE `sys_blacklist` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'IP地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ip` (`ip`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='IP黑名单';

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典名称',
  `dict_sort` int NOT NULL DEFAULT '0' COMMENT '字典排序',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '字典标签',
  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '字典类型',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_dict_type` (`dict_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='字典数据表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (1, '2022-03-15 16:43:59', '2022-03-15 16:43:59', '性别', 0, '男', 'male', 'gender');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (2, '2022-03-15 16:44:02', '2022-03-15 16:44:17', '性别', 0, '女', 'female', 'gender');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (3, '2022-05-22 22:27:52', '2022-06-14 16:10:13', '政治面貌', 0, '群众', 'masses', 'political_status');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (4, '2022-05-22 22:28:11', '2022-06-14 16:29:19', '政治面貌', 0, '民主党派', 'democratic-party', 'political_status');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (5, '2022-05-22 22:28:37', '2022-06-14 16:29:26', '政治面貌', 0, '中共党员', 'cpc', 'political_status');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (6, '2022-05-22 22:29:01', '2022-06-14 16:29:37', '政治面貌', 0, '共青团员', 'cyl', 'political_status');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (7, '2022-05-22 22:29:27', '2022-06-14 16:08:18', '婚姻状况', 0, '未婚', 'unmarried', 'marital_status');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (8, '2022-05-22 22:29:37', '2022-06-14 16:08:29', '婚姻状况', 0, '已婚', 'married', 'marital_status');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (9, '2022-05-22 22:29:40', '2022-06-14 16:08:36', '婚姻状况', 0, '丧偶', 'widowed', 'marital_status');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (10, '2022-05-22 22:29:44', '2022-06-14 16:08:44', '婚姻状况', 0, '离婚', 'divorce', 'marital_status');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (11, '2022-05-22 22:30:19', '2022-06-14 16:09:00', '学生状态', 0, '正常', 'normal', 'student_status');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (12, '2022-05-22 22:30:23', '2022-06-14 16:09:08', '学生状态', 0, '毕业', 'graduate', 'student_status');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (13, '2022-05-22 22:30:26', '2022-06-14 16:09:19', '学生状态', 0, '肄业', 'quit', 'student_status');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (14, '2022-05-22 22:30:29', '2022-06-14 16:09:31', '学生状态', 0, '休学', 'leave', 'student_status');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (15, '2022-05-22 22:31:15', '2022-06-14 16:18:55', '民族', 0, '汉族', 'Han', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (16, '2022-05-22 22:31:22', '2022-06-14 16:18:55', '民族', 0, '壮族', 'Zhuang', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (17, '2022-05-22 22:31:25', '2022-06-14 16:18:56', '民族', 0, '满族', 'Manchu', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (18, '2022-05-22 22:31:28', '2022-06-14 16:18:56', '民族', 0, '回族', 'Hui', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (19, '2022-05-22 22:31:31', '2022-06-14 16:18:56', '民族', 0, '苗族', 'Hmong', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (20, '2022-05-22 22:31:34', '2022-06-14 16:18:56', '民族', 0, '维吾尔族', 'Uighur', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (21, '2022-05-22 22:31:37', '2022-06-14 16:18:56', '民族', 0, '土家族', 'Tujia', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (22, '2022-05-22 22:31:40', '2022-06-14 16:18:57', '民族', 0, '彝族', 'Yi', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (23, '2022-05-22 22:31:43', '2022-06-14 16:18:57', '民族', 0, '蒙古族', 'Mongolian', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (24, '2022-05-22 22:31:56', '2022-06-14 16:18:57', '民族', 0, '藏族', 'Tibetan', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (25, '2022-05-22 22:31:59', '2022-06-14 16:18:57', '民族', 0, '布依族', 'Buyi', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (26, '2022-05-22 22:32:03', '2022-06-14 16:18:57', '民族', 0, '侗族', 'Dong', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (27, '2022-05-22 22:32:07', '2022-06-14 16:18:57', '民族', 0, '瑶族', 'Yao', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (28, '2022-05-22 22:32:10', '2022-06-14 16:18:58', '民族', 0, '朝鲜族', 'Korean', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (29, '2022-05-22 22:32:15', '2022-06-14 16:18:58', '民族', 0, '白族', 'Bai', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (30, '2022-05-22 22:32:18', '2022-06-14 16:18:58', '民族', 0, '哈尼族', 'Hani', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (31, '2022-05-22 22:32:20', '2022-06-14 16:18:58', '民族', 0, '哈萨克族', 'Kazakh', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (32, '2022-05-22 22:32:23', '2022-06-14 16:18:58', '民族', 0, '黎族', 'Li', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (33, '2022-05-22 22:32:26', '2022-06-14 16:18:58', '民族', 0, '傣族', 'Dai', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (34, '2022-05-22 22:32:28', '2022-06-14 16:18:59', '民族', 0, '畲族', 'She', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (35, '2022-05-22 22:32:59', '2022-06-14 16:18:59', '民族', 0, '傈僳族', 'Lisu', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (36, '2022-05-22 22:33:31', '2022-06-14 16:18:59', '民族', 0, '仡佬族', 'Gelao', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (37, '2022-05-22 22:33:34', '2022-06-14 16:18:59', '民族', 0, '东乡族', 'Dongxiang', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (38, '2022-05-22 22:33:37', '2022-06-14 16:18:59', '民族', 0, '高山族', 'Gaoshan', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (39, '2022-05-22 22:33:40', '2022-06-14 16:19:00', '民族', 0, '拉祜族', 'Lahu', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (40, '2022-05-22 22:33:43', '2022-06-14 16:19:00', '民族', 0, '水族', 'Water', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (41, '2022-05-22 22:33:46', '2022-06-14 16:19:00', '民族', 0, '佤族', 'Wa', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (42, '2022-05-22 22:33:49', '2022-06-14 16:19:00', '民族', 0, '纳西族', 'Naxi', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (43, '2022-05-22 22:33:52', '2022-06-14 16:19:00', '民族', 0, '羌族', 'Qiang', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (44, '2022-05-22 22:33:55', '2022-06-14 16:19:00', '民族', 0, '土族', 'Tu', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (45, '2022-05-22 22:33:58', '2022-06-14 16:19:01', '民族', 0, '仫佬族', 'Mulao', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (46, '2022-05-22 22:34:03', '2022-06-14 16:19:01', '民族', 0, '锡伯族', 'Xibe', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (47, '2022-05-22 22:34:07', '2022-06-14 16:19:01', '民族', 0, '柯尔克孜族', 'Kirgiz', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (48, '2022-05-22 22:35:11', '2022-06-14 16:19:01', '民族', 0, '达斡尔族', 'Daur', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (49, '2022-05-22 22:35:12', '2022-06-14 16:19:01', '民族', 0, '景颇族', 'Jingpo', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (50, '2022-05-22 22:35:13', '2022-06-14 16:19:01', '民族', 0, '毛南族', 'Maonan', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (51, '2022-05-22 22:35:14', '2022-06-14 16:19:02', '民族', 0, '撒拉族', 'Salar', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (52, '2022-05-22 22:35:14', '2022-06-14 16:19:02', '民族', 0, '布朗族', 'Browns', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (53, '2022-05-22 22:35:15', '2022-06-14 16:19:02', '民族', 0, '塔吉克族', 'Tajik', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (54, '2022-05-22 22:35:15', '2022-06-14 16:19:02', '民族', 0, '阿昌族', 'Achang', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (55, '2022-05-22 22:35:16', '2022-06-14 16:19:02', '民族', 0, '普米族', 'Pumi', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (56, '2022-05-22 22:35:16', '2022-06-14 16:19:02', '民族', 0, '鄂温克族', 'Evenki', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (57, '2022-05-22 22:35:17', '2022-06-14 16:19:03', '民族', 0, '怒族', 'Nu', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (58, '2022-05-22 22:35:18', '2022-06-14 16:19:03', '民族', 0, '京族', 'Jing', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (59, '2022-05-22 22:35:18', '2022-06-14 16:19:03', '民族', 0, '基诺族', 'Jino', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (60, '2022-05-22 22:35:28', '2022-06-14 16:19:03', '民族', 0, '德昂族', 'De\'ang', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (61, '2022-05-22 22:35:29', '2022-06-14 16:19:03', '民族', 0, '保安族', 'Baoan', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (62, '2022-05-22 22:35:30', '2022-06-14 16:19:03', '民族', 0, '俄罗斯族', 'Russian', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (63, '2022-05-22 22:35:31', '2022-06-14 16:19:03', '民族', 0, '裕固族', 'Yugur', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (64, '2022-05-22 22:35:31', '2022-06-14 16:19:04', '民族', 0, '乌孜别克族', 'Uzbek', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (65, '2022-05-22 22:35:32', '2022-06-14 16:19:04', '民族', 0, '门巴族', 'Monba', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (66, '2022-05-22 22:35:33', '2022-06-14 16:19:04', '民族', 0, '鄂伦春族', 'Oroqen', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (67, '2022-05-22 22:35:34', '2022-06-14 16:19:04', '民族', 0, '独龙族', 'Dulong', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (68, '2022-05-22 22:35:35', '2022-06-14 16:19:04', '民族', 0, '塔塔尔族', 'Tatar', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (69, '2022-05-22 22:35:35', '2022-06-14 16:19:04', '民族', 0, '赫哲族', 'Hezhen', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (70, '2022-05-22 22:35:36', '2022-06-14 16:19:05', '民族', 0, '珞巴族', 'Lhoba', 'nationality');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (71, '2022-05-22 22:37:52', '2022-06-14 16:25:56', '受教育程度', 0, '小学', 'primary-school', 'education');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (72, '2022-05-22 22:38:40', '2022-06-14 16:26:11', '受教育程度', 0, '初中', 'junior-high-school', 'education');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (73, '2022-05-22 22:38:46', '2022-06-14 16:26:19', '受教育程度', 0, '高中', 'high-school', 'education');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (74, '2022-05-22 22:38:55', '2022-06-14 16:26:39', '受教育程度', 0, '中专', 'technical-secondary-school', 'education');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (75, '2022-05-22 22:39:02', '2022-06-14 16:26:47', '受教育程度', 0, '大专', 'college', 'education');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (76, '2022-05-22 22:39:10', '2022-06-14 16:27:02', '受教育程度', 0, '本科', 'undergraduate', 'education');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (77, '2022-05-22 22:39:22', '2022-06-14 16:27:17', '受教育程度', 0, '硕士', 'master', 'education');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (78, '2022-05-22 22:39:30', '2022-06-14 16:27:21', '受教育程度', 0, '博士', 'docter', 'education');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (79, '2022-05-22 22:40:05', '2022-06-14 16:27:34', '受教育程度', 0, '博士后', 'postdoc', 'education');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (80, '2022-05-22 22:52:42', '2022-06-14 16:06:21', '生肖', 0, '鼠', 'rat', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (81, '2022-05-22 22:52:42', '2022-06-14 16:06:23', '生肖', 0, '牛', 'ox', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (82, '2022-05-22 22:52:43', '2022-06-14 16:06:24', '生肖', 0, '虎', 'tiger', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (83, '2022-05-22 22:52:44', '2022-06-14 16:06:26', '生肖', 0, '兔', 'rabbit', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (84, '2022-05-22 22:52:45', '2022-06-14 16:06:28', '生肖', 0, '龙', 'dragon', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (85, '2022-05-22 22:52:45', '2022-06-14 16:06:30', '生肖', 0, '蛇', 'snake', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (86, '2022-05-22 22:52:46', '2022-06-14 16:06:32', '生肖', 0, '马', 'horse', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (87, '2022-05-22 22:52:47', '2022-06-14 16:06:34', '生肖', 0, '羊', 'goat', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (88, '2022-05-22 22:52:47', '2022-06-14 16:06:35', '生肖', 0, '猴', 'monkey', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (89, '2022-05-22 22:52:48', '2022-06-14 16:06:37', '生肖', 0, '鸡', 'rooster', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (90, '2022-05-22 22:52:49', '2022-06-14 16:06:38', '生肖', 0, '狗', 'dog', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (91, '2022-05-22 22:52:50', '2022-06-14 16:06:41', '生肖', 0, '猪', 'pig', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (92, '2022-05-22 22:55:00', '2022-06-14 16:06:43', '星座', 0, '白羊座', 'aries', 'constellations');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (93, '2022-05-22 22:55:00', '2022-06-14 16:06:45', '星座', 0, '金牛座', 'taurus', 'constellations');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (94, '2022-05-22 22:55:01', '2022-06-14 16:06:47', '星座', 0, '双子座', 'gemini', 'constellations');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (95, '2022-05-22 22:55:02', '2022-06-14 16:06:49', '星座', 0, '巨蟹座', 'cancer', 'constellations');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (96, '2022-05-22 22:55:02', '2022-06-14 16:06:52', '星座', 0, '狮子座', 'leo', 'constellations');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (97, '2022-05-22 22:55:03', '2022-06-14 16:06:53', '星座', 0, '处女座', 'virgo', 'constellations');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (98, '2022-05-22 22:55:03', '2022-06-14 16:06:55', '星座', 0, '天秤座', 'libra', 'constellations');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (99, '2022-05-22 22:55:04', '2022-06-14 16:06:57', '星座', 0, '天蝎座', 'scorpio', 'constellations');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (100, '2022-05-22 22:55:05', '2022-06-14 16:06:59', '星座', 0, '射手座', 'sagittarius', 'constellations');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (101, '2022-05-22 22:55:05', '2022-06-14 16:07:02', '星座', 0, '摩羯座', 'capricorn', 'constellations');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (102, '2022-05-22 22:55:06', '2022-06-14 16:07:04', '星座', 0, '水瓶座', 'aquarius', 'constellations');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (103, '2022-05-22 22:55:07', '2022-06-14 16:07:06', '星座', 0, '双鱼座', 'pisces', 'constellations');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (104, '2022-05-22 23:00:50', '2022-06-14 16:07:09', '血型', 0, 'A型', 'A', 'blood_type');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (105, '2022-05-22 23:01:00', '2022-06-14 16:07:11', '血型', 0, 'B型', 'B', 'blood_type');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (106, '2022-05-22 23:01:01', '2022-06-14 16:07:13', '血型', 0, 'AB型', 'AB', 'blood_type');
INSERT INTO `sys_dict` (`id`, `create_time`, `update_time`, `name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES (107, '2022-05-22 23:01:03', '2022-06-14 16:07:15', '血型', 0, 'O型', 'O', 'blood_type');
COMMIT;

-- ----------------------------
-- Table structure for sys_feedback
-- ----------------------------
DROP TABLE IF EXISTS `sys_feedback`;
CREATE TABLE `sys_feedback` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '联系邮箱',
  `tel` varchar(255) DEFAULT NULL COMMENT '联系电话',
  `description` varchar(2048) DEFAULT NULL COMMENT '详细描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='意见反馈';

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `filename` varchar(255) DEFAULT NULL COMMENT '文件名',
  `content` longblob NOT NULL COMMENT '文件内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文件';

-- ----------------------------
-- Table structure for sys_friendly_url
-- ----------------------------
DROP TABLE IF EXISTS `sys_friendly_url`;
CREATE TABLE `sys_friendly_url` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `content` varchar(255) NOT NULL COMMENT '链接文本',
  `url` varchar(255) NOT NULL COMMENT '链接',
  `sort` tinyint NOT NULL COMMENT '排序',
  `status` tinyint NOT NULL COMMENT '1-启用 0-禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='友情链接';

-- ----------------------------
-- Table structure for sys_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_info`;
CREATE TABLE `sys_info` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `param_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '参数名称',
  `param_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '参数值',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统信息';

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `login_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录用户名',
  `ip` varchar(255) DEFAULT NULL COMMENT 'IP地址',
  `ua` varchar(255) DEFAULT NULL COMMENT '浏览器请求头',
  `msg` varchar(255) DEFAULT NULL COMMENT '消息',
  `is_success` tinyint unsigned DEFAULT '0' COMMENT '1-成功 0-失败',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='登录日志';

-- ----------------------------
-- Table structure for sys_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operate_log`;
CREATE TABLE `sys_operate_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `project` int DEFAULT NULL COMMENT '项目信息',
  `action_type` int DEFAULT NULL,
  `category` int DEFAULT NULL,
  `client_ip` varchar(255) DEFAULT NULL,
  `key_id` varchar(255) DEFAULT NULL,
  `login_channel` varchar(255) DEFAULT NULL,
  `login_edition` varchar(255) DEFAULT NULL,
  `operate_result` varchar(255) DEFAULT NULL,
  `operate_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `staff_id` int DEFAULT NULL,
  `staffName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='操作日志';

-- ----------------------------
-- Table structure for sys_release
-- ----------------------------
DROP TABLE IF EXISTS `sys_release`;
CREATE TABLE `sys_release` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '版本号',
  `description` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '版本描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='发布版本';

-- ----------------------------
-- Table structure for sys_sql
-- ----------------------------
DROP TABLE IF EXISTS `sys_sql`;
CREATE TABLE `sys_sql` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `sql` varchar(255) DEFAULT NULL COMMENT 'SQL语句',
  `success` tinyint DEFAULT '0' COMMENT '是否成功',
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '作者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='SQL执行';

-- ----------------------------
-- Table structure for sys_task
-- ----------------------------
DROP TABLE IF EXISTS `sys_task`;
CREATE TABLE `sys_task` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '定时任务名称',
  `cron` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '定时任务表达式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='定时任务';

-- ----------------------------
-- Table structure for sys_whitelist
-- ----------------------------
DROP TABLE IF EXISTS `sys_whitelist`;
CREATE TABLE `sys_whitelist` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'IP白名单',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ip` (`ip`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='IP白名单';

-- ----------------------------
-- Table structure for template
-- ----------------------------
DROP TABLE IF EXISTS `template`;
CREATE TABLE `template` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='模板表';

SET FOREIGN_KEY_CHECKS = 1;
