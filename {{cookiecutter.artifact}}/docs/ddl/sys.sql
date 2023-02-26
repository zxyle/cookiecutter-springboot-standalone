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
  `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'IP',
  `end_time` datetime DEFAULT NULL COMMENT '截止日期',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
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
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('性别', 0, '男', 'male', 'gender');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('性别', 1, '女', 'female', 'gender');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('客户端类型', 0, 'PC', 'pc', 'agent');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('客户端类型', 0, 'H5', 'h5', 'agent');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('客户端类型', 0, 'APP', 'app', 'agent');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('政治面貌', 0, '群众', 'masses', 'political_status');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('政治面貌', 0, '民主党派', 'democratic-party', 'political_status');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('政治面貌', 0, '中共党员', 'cpc', 'political_status');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('政治面貌', 0, '共青团员', 'cyl', 'political_status');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('婚姻状况', 0, '未婚', 'unmarried', 'marital_status');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('婚姻状况', 0, '已婚', 'married', 'marital_status');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('婚姻状况', 0, '丧偶', 'widowed', 'marital_status');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('婚姻状况', 0, '离婚', 'divorce', 'marital_status');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('学生状态', 0, '正常', 'normal', 'student_status');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('学生状态', 0, '毕业', 'graduate', 'student_status');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('学生状态', 0, '肄业', 'quit', 'student_status');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('学生状态', 0, '休学', 'leave', 'student_status');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '汉族', 'Han', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '壮族', 'Zhuang', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '满族', 'Manchu', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '回族', 'Hui', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '苗族', 'Hmong', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '维吾尔族', 'Uighur', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '土家族', 'Tujia', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '彝族', 'Yi', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '蒙古族', 'Mongolian', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '藏族', 'Tibetan', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '布依族', 'Buyi', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '侗族', 'Dong', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '瑶族', 'Yao', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '朝鲜族', 'Korean', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '白族', 'Bai', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '哈尼族', 'Hani', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '哈萨克族', 'Kazakh', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '黎族', 'Li', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '傣族', 'Dai', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '畲族', 'She', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '傈僳族', 'Lisu', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '仡佬族', 'Gelao', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '东乡族', 'Dongxiang', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '高山族', 'Gaoshan', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '拉祜族', 'Lahu', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '水族', 'Water', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '佤族', 'Wa', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '纳西族', 'Naxi', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '羌族', 'Qiang', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '土族', 'Tu', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '仫佬族', 'Mulao', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '锡伯族', 'Xibe', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '柯尔克孜族', 'Kirgiz', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '达斡尔族', 'Daur', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '景颇族', 'Jingpo', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '毛南族', 'Maonan', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '撒拉族', 'Salar', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '布朗族', 'Browns', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '塔吉克族', 'Tajik', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '阿昌族', 'Achang', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '普米族', 'Pumi', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '鄂温克族', 'Evenki', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '怒族', 'Nu', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '京族', 'Jing', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '基诺族', 'Jino', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '德昂族', 'Deang', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '保安族', 'Baoan', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '俄罗斯族', 'Russian', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '裕固族', 'Yugur', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '乌孜别克族', 'Uzbek', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '门巴族', 'Monba', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '鄂伦春族', 'Oroqen', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '独龙族', 'Dulong', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '塔塔尔族', 'Tatar', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '赫哲族', 'Hezhen', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('民族', 0, '珞巴族', 'Lhoba', 'nationality');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('受教育程度', 0, '小学', 'primary-school', 'education');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('受教育程度', 0, '初中', 'junior-high-school', 'education');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('受教育程度', 0, '高中', 'high-school', 'education');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('受教育程度', 0, '中专', 'technical-secondary-school', 'education');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('受教育程度', 0, '大专', 'college', 'education');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('受教育程度', 0, '本科', 'undergraduate', 'education');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('受教育程度', 0, '硕士', 'master', 'education');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('受教育程度', 0, '博士', 'doctor', 'education');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('受教育程度', 0, '博士后', 'postdoc', 'education');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('生肖', 0, '鼠', 'rat', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('生肖', 0, '牛', 'ox', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('生肖', 0, '虎', 'tiger', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('生肖', 0, '兔', 'rabbit', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('生肖', 0, '龙', 'dragon', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('生肖', 0, '蛇', 'snake', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('生肖', 0, '马', 'horse', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('生肖', 0, '羊', 'goat', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('生肖', 0, '猴', 'monkey', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('生肖', 0, '鸡', 'rooster', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('生肖', 0, '狗', 'dog', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('生肖', 0, '猪', 'pig', 'chinese_zodiac_signs');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('星座', 0, '白羊座', 'aries', 'constellations');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('星座', 0, '金牛座', 'taurus', 'constellations');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('星座', 0, '双子座', 'gemini', 'constellations');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('星座', 0, '巨蟹座', 'cancer', 'constellations');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('星座', 0, '狮子座', 'leo', 'constellations');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('星座', 0, '处女座', 'virgo', 'constellations');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('星座', 0, '天秤座', 'libra', 'constellations');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('星座', 0, '天蝎座', 'scorpio', 'constellations');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('星座', 0, '射手座', 'sagittarius', 'constellations');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('星座', 0, '摩羯座', 'capricorn', 'constellations');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('星座', 0, '水瓶座', 'aquarius', 'constellations');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('星座', 0, '双鱼座', 'pisces', 'constellations');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('血型', 0, 'A型', 'A', 'blood_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('血型', 0, 'B型', 'B', 'blood_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('血型', 0, 'AB型', 'AB', 'blood_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('血型', 0, 'O型', 'O', 'blood_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('邮箱', 0, '126.com', '126.com', 'email_domain');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('邮箱', 0, '139.com', '139.com', 'email_domain');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('邮箱', 0, '163.com', '163.com', 'email_domain');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('邮箱', 0, '189.com', '189.com', 'email_domain');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('邮箱', 0, 'aliyun.com', 'aliyun.com', 'email_domain');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('邮箱', 0, 'foxmail.com', 'foxmail.com', 'email_domain');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('邮箱', 0, 'gmail.com', 'gmail.com', 'email_domain');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('邮箱', 0, 'hotmail.com', 'hotmail.com', 'email_domain');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('邮箱', 0, 'icloud.com', 'icloud.com', 'email_domain');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('邮箱', 0, 'outlook.com', 'outlook.com', 'email_domain');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('邮箱', 0, 'qq.com', 'qq.com', 'email_domain');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('邮箱', 0, 'sina.cn', 'sina.cn', 'email_domain');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('邮箱', 0, 'sina.com', 'sina.com', 'email_domain');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('邮箱', 0, 'sohu.com', 'sohu.com', 'email_domain');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('邮箱', 0, 'yahoo.cn', 'yahoo.cn', 'email_domain');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('邮箱', 0, 'yeah.net', 'yeah.net', 'email_domain');
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
-- Records of sys_friendly_url
-- ----------------------------
BEGIN;
INSERT INTO `sys_friendly_url` (`content`, `url`, `sort`, `status`) VALUES ('百度', 'https://www.baidu.com', 2, 1);
INSERT INTO `sys_friendly_url` (`content`, `url`, `sort`, `status`) VALUES ('腾讯网', 'https://www.qq.com', 1, 1);
INSERT INTO `sys_friendly_url` (`content`, `url`, `sort`, `status`) VALUES ('淘宝网', 'https://www.taobao.com', 3, 1);
COMMIT;

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
-- Records of sys_info
-- ----------------------------
BEGIN;
INSERT INTO `sys_info` (`param_key`, `param_value`, `description`) VALUES ('icp', '浙ICP备0000号', '备案号');
INSERT INTO `sys_info` (`param_key`, `param_value`, `description`) VALUES ('name', 'XXX管理系统', '系统名称');
INSERT INTO `sys_info` (`param_key`, `param_value`, `description`) VALUES ('version', 'V1.0.0', '版本');
INSERT INTO `sys_info` (`param_key`, `param_value`, `description`) VALUES ('language', '简体中文', '语言');
INSERT INTO `sys_info` (`param_key`, `param_value`, `description`) VALUES ('favicon', 'B', '网站logo');
INSERT INTO `sys_info` (`param_key`, `param_value`, `description`) VALUES ('copyright', 'XX有限公司版权所有', '版权信息');
INSERT INTO `sys_info` (`param_key`, `param_value`, `description`) VALUES ('email', 'xxx@example.com', '技术支持邮箱');
INSERT INTO `sys_info` (`param_key`, `param_value`, `description`) VALUES ('keywords', 'XXX', 'SEO关键字');
INSERT INTO `sys_info` (`param_key`, `param_value`, `description`) VALUES ('description', 'XXX', 'SEO描述');
INSERT INTO `sys_info` (`param_key`, `param_value`, `description`) VALUES ('agreement', 'G', '协议文本');
INSERT INTO `sys_info` (`param_key`, `param_value`, `description`) VALUES ('homepage', 'example.com', '主页');
INSERT INTO `sys_info` (`param_key`, `param_value`, `description`) VALUES ('title', 'XXX', '网站标题');
INSERT INTO `sys_info` (`param_key`, `param_value`, `description`) VALUES ('tel', '400-111-2222', '联系电话');
INSERT INTO `sys_info` (`param_key`, `param_value`, `description`) VALUES ('address', '浙江省杭州市余杭区文一西路10000号', '联系地址');
INSERT INTO `sys_info` (`param_key`, `param_value`, `description`) VALUES ('about', 'XX公司是一家什么公司', '关于我们');
COMMIT;

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
  `operate_time` datetime NOT NULL COMMENT '操作时间',
  `user_id` bigint unsigned NOT NULL COMMENT '操作用户ID',
  `operation_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作类型',
  `object_id` bigint unsigned DEFAULT NULL COMMENT '操作对象ID',
  `result` enum('true','false') NOT NULL DEFAULT 'false' COMMENT '操作结果',
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
-- Records of sys_release
-- ----------------------------
BEGIN;
INSERT INTO `sys_release` (`version`, `description`) VALUES ('V1.0.0', '第一个版本');
INSERT INTO `sys_release` (`version`, `description`) VALUES ('v1.1.1', '第二版本');
COMMIT;

-- ----------------------------
-- Table structure for sys_setting
-- ----------------------------
DROP TABLE IF EXISTS `sys_setting`;
CREATE TABLE `sys_setting` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `option_label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '选项',
  `option_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '选项值',
  `data_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'string' COMMENT 'Java数据类型',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '选项描述',
  `default_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '默认值',
PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统设置';

-- ----------------------------
-- Records of sys_setting
-- ----------------------------
BEGIN;
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('captcha.on', 'false', 'java.lang.Boolean', '登录是否开启图形验证码', 'false');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('captcha.alive-time', '30', 'java.lang.Integer', '验证码存活时间（单位：分钟）', '30');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('captcha.digits', '6', 'java.lang.Integer', '验证码位数/长度', '6');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('captcha.between', '60', 'java.lang.Integer', '两次验证码请求间隔时间（单位：秒）', '60');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('captcha.retry-times', '3', 'java.lang.Integer', '重试登录次数', '3');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('captcha.characters', '23456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKMNPQRSTUVWXYZ', 'java.lang.String', '验证码字符集(一般去掉1 l L 0 o O 易混淆字符)', '23456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKMNPQRSTUVWXYZ');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('captcha.height', '70', 'java.lang.Integer', '高度像素', '70');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('captcha.width', '160', 'java.lang.Integer', '宽度像素', '160');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('captcha.font-size', '45', 'java.lang.Integer', '字体大小', '45');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('captcha.font-family', '宋体,楷体,微软雅黑', 'java.lang.String', '字体', '宋体,楷体,微软雅黑');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('captcha.kind', 'kaptcha', 'java.lang.String', '验证码种类 kaptcha、patchca', 'kaptcha');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('captcha.format', 'png', 'java.lang.String', '图片格式（jpg、png）', 'png');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('captcha.key-prefix', 'captcha:', 'java.lang.String', '验证码存储前缀', 'captcha:');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('pwd.expire-days', '90', 'java.lang.Integer', '密码过期天数（-1代表不限制）', '90');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('pwd.max-length', '32', 'java.lang.Integer', '最大长度', '32');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('pwd.min-length', '8', 'java.lang.Integer', '最小长度', '8');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('pwd.complexity', '32', 'java.lang.Integer', '密码复杂度', '32');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('pwd.chars', '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_+`-={}|[]:;<>?,.', 'java.lang.String', '密码字符集', '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_+`-={}|[]:;<>?,.');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('pwd.enable-history', 'false', 'java.lang.Boolean', '是否记录历史密码', 'false');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('pwd.history-count', '5', 'java.lang.Integer', '密码历史记录数量 0代表不限制', '5');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('auth.user.max-failed-times', '5', 'java.lang.Integer', '最大登录失败次数', '5');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('auth.user.lock-time', '30', 'java.lang.Integer', '锁定时间', '30');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('auth.user.lock-time-unit', 'MINUTES', 'java.lang.String', '锁定时间单位', 'MINUTES');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('auth.user.default-password', 'QAr$S2xreDb##tdm', 'java.lang.String', '默认密码', 'QAr$S2xreDb##tdm');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('auth.user.verification', 'password', 'java.lang.String', '认证方式 （password-密码登录、qrcode-扫码登录、code-验证码登录）', 'password');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('auth.user.open-registration', 'false', 'java.lang.Boolean', '是否开放用户注册', 'false');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('auth.user.default-role', '3', 'java.lang.Long', '注册后默认角色ID', '3');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('auth.user.reset', 'false', 'java.lang.Boolean', '初次登录后是否需修改密码', 'false');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('spring.mail.host', 'smtp.163.com', 'java.lang.String', NULL, 'smtp.163.com');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('spring.mail.port', '465', 'java.lang.Integer', NULL, '465');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('spring.mail.username', 'xxx@163.com', 'java.lang.String', NULL, 'xxx@163.com');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('spring.mail.password', 'xxx', 'java.lang.String', NULL, 'xxx');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('spring.mail.properties.mail.smtp.ssl.enable', 'true', 'java.lang.Boolean', NULL, 'true');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('dingtalk.access-token', 'PLEASE-REPLACE-ACCESS-TOKEN', 'java.lang.String', '钉钉告警token', 'PLEASE-REPLACE-ACCESS-TOKEN');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('app.name', 'XXX管理系统', 'java.lang.String', '系统名称', 'XXX管理系统');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('app.icp', '浙ICP备0000号', 'java.lang.String', '备案号', '浙ICP备0000号');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('app.version', 'V1.0.0', 'java.lang.String', '版本', 'V1.0.0');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('app.language', '简体中文', 'java.lang.String', '语言', '简体中文');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('app.favicon', 'B', 'java.lang.String', '网站logo', 'B');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('app.copyright', 'XX有限公司版权所有', 'java.lang.String', '版权信息', 'XX有限公司版权所有');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('app.email', 'xxx@example.com', 'java.lang.String', '技术支持邮箱', 'xxx@example.com');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('app.keywords', 'XXX', 'java.lang.String', 'SEO关键字', 'XXX');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('app.description', 'XXX', 'java.lang.String', 'SEO描述', 'XXX');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('app.agreement', 'G', 'java.lang.String', '协议文本', 'G');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('app.homepage', 'example.com', 'java.lang.String', '主页', 'example.com');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('app.title', 'XXX', 'java.lang.String', '网站标题', 'XXX');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('app.tel', '400-111-2222', 'java.lang.String', '联系电话', '400-111-2222');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('app.address', '浙江省杭州市余杭区文一西路10000号', 'java.lang.String', '联系地址', '浙江省杭州市余杭区文一西路10000号');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('app.about', 'XX公司是一家什么公司', 'java.lang.String', '关于我们', 'XX公司是一家什么公司');
COMMIT;

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
-- Table structure for sys_verification
-- ----------------------------
DROP TABLE IF EXISTS `sys_verification`;
CREATE TABLE `sys_verification` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `kind` enum('email','mobile') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '验证码类型',
  `receiver` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '接收者',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='验证码发送记录';


-- ----------------------------
-- Table structure for sys_whitelist
-- ----------------------------
DROP TABLE IF EXISTS `sys_whitelist`;
CREATE TABLE `sys_whitelist` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'IP',
  `end_time` datetime DEFAULT NULL COMMENT '截止日期',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
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
