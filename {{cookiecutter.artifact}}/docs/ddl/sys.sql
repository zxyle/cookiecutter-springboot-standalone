SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(16) CHARACTER SET utf8mb4 NOT NULL COMMENT '行政区编码',
  `name` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '行政区名称',
  `parent_id` varchar(16) CHARACTER SET utf8mb4 NOT NULL COMMENT '上级编码',
  `level` tinyint NOT NULL COMMENT '级别',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_level` (`level`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='中国行政区划';

-- ----------------------------
-- Table structure for sys_blacklist
-- ----------------------------
DROP TABLE IF EXISTS `sys_blacklist`;
CREATE TABLE `sys_blacklist` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `ip` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT 'IP',
  `end_time` datetime DEFAULT NULL COMMENT '截止日期',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ip` (`ip`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='IP黑名单';

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT '字典名称',
  `dict_sort` int NOT NULL DEFAULT '0' COMMENT '字典排序',
  `label` varchar(100) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '字典标签',
  `value` varchar(100) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(25) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '字典类型',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_dict_type` (`dict_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='字典数据表';

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
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('行业分类', 0, '农、林、牧、渔业', 'A', 'industry');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('行业分类', 0, '采矿业', 'B', 'industry');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('行业分类', 0, '制造业', 'C', 'industry');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('行业分类', 0, '电力、热力、燃气及水生产和供应业', 'D', 'industry');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('行业分类', 0, '建筑业', 'E', 'industry');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('行业分类', 0, '批发和零售业', 'F', 'industry');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('行业分类', 0, '交通运输、仓储和邮政业', 'G', 'industry');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('行业分类', 0, '住宿和餐饮业', 'H', 'industry');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('行业分类', 0, '信息传输、软件和信息技术服务业', 'I', 'industry');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('行业分类', 0, '金融业', 'J', 'industry');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('行业分类', 0, '房地产业', 'K', 'industry');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('行业分类', 0, '租赁和商务服务业', 'L', 'industry');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('行业分类', 0, '科学研究和技术服务业', 'M', 'industry');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('行业分类', 0, '水利、环境和公共设施管理业', 'N', 'industry');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('行业分类', 0, '居民服务、修理和其他服务业', 'O', 'industry');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('行业分类', 0, '教育', 'P', 'industry');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('行业分类', 0, '卫生和社会工作', 'Q', 'industry');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('行业分类', 0, '文化、体育和娱乐业', 'R', 'industry');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('行业分类', 0, '公共管理、社会保障和社会组织', 'S', 'industry');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('行业分类', 0, '国际组织', 'T', 'industry');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('企业类型', 0, '有限责任公司', '有限责任公司', 'company_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('企业类型', 0, '股份有限公司', '股份有限公司', 'company_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('企业类型', 0, '股份合作公司', '股份合作公司', 'company_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('企业类型', 0, '国有企业', '国有企业', 'company_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('企业类型', 0, '集体所有制', '集体所有制', 'company_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('企业类型', 0, '个体工商户', '个体工商户', 'company_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('企业类型', 0, '独资企业', '独资企业', 'company_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('企业类型', 0, '有限合伙', '有限合伙', 'company_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('企业类型', 0, '普通合伙', '普通合伙', 'company_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('企业类型', 0, '外商投资企业', '外商投资企业', 'company_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('企业类型', 0, '港、澳、台商投资企业', '港、澳、台商投资企业', 'company_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('企业类型', 0, '联营企业', '联营企业', 'company_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('企业类型', 0, '私有企业', '私有企业', 'company_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('证件类型', 0, '居民身份证', '0', 'id_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('证件类型', 0, '护照', '1', 'id_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('证件类型', 0, '军人证（军官证）', '2', 'id_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('证件类型', 0, '驾照', '3', 'id_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('证件类型', 0, '户口本', '4', 'id_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('证件类型', 0, '学生证', '5', 'id_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('证件类型', 0, '工作证', '6', 'id_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('证件类型', 0, '出生证', '7', 'id_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('证件类型', 0, '其它', '8', 'id_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('证件类型', 0, '无证件', '9', 'id_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('证件类型', 0, '士兵证', 'A', 'id_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('证件类型', 0, '回乡证', 'B', 'id_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('证件类型', 0, '临时身份证', 'C', 'id_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('证件类型', 0, '警官证', 'D', 'id_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('证件类型', 0, '台胞证', 'E', 'id_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('证件类型', 0, '外国人永久居留身份证', 'I', 'id_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('证件类型', 0, '港澳台居民居住证', 'J', 'id_type');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '中国工商银行', '0101', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '中国农业银行', '0201', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '中国建设银行', '0301', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '中国银行', '0401', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '中国邮政储蓄银行', '0501', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '招商银行', '0601', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '兴业银行', '0701', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '中国光大银行', '0801', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '广东发展银行', '0901', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '民生银行', '1001', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '平安银行', '1201', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '深圳发展银行', '1301', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '交通银行', '1401', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '中信银行', '1501', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '江苏银行', '1601', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '恒丰银行', '1701', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '上海浦东发展银行', '1801', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '南京银行', '1901', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '宁波银行', '2101', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '华夏银行', '2201', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '农村商业银行', '2301', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '上海银行', '2401', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '江南农村商业银行', '2501', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '莱商银行', '2701', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '张家港农村商业银行', '3101', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '齐鲁银行', '3201', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '浙江泰隆商业银行', '3401', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '东营银行股份有限公司', '3501', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '江苏长江商业银行', '4101', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '昆山农村商业银行', '4301', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '浙商银行', '4401', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '常熟农村商业银行', '4501', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '太仓农村商业银行', '4601', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '成都银行', '4701', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '苏州银行', '5002', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('银行', 0, '苏州农商行', '3301', 'bank');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '女儿', '06', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '祖父', '07', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '祖母', '08', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '孙子', '09', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '孙女', '10', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '外祖父', '11', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '外祖母', '12', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '外孙', '13', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '外孙女', '14', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '哥哥', '15', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '姐姐', '16', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '弟弟', '17', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '妹妹', '18', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '公公', '19', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '婆婆', '20', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '岳父', '21', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '岳母', '22', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '儿媳', '23', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '女婿', '24', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '其他亲属', '25', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '同事', '26', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '朋友', '27', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '雇主', '28', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '雇员', '29', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '其他', '30', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '本人', '00', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '丈夫', '01', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '妻子', '02', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '父亲', '03', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '母亲', '04', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '儿子', '05', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '父母', '31', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '子女', '32', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('亲属关系', 0, '配偶', '33', 'relation');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '中国', 'China', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '安哥拉', 'Angola', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '阿富汗', 'Afghanistan', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '阿尔巴尼亚', 'Albania', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '阿尔及利亚', 'Algeria', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '安道尔共和国', 'Andorra', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '安圭拉岛', 'Anguilla', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '安提瓜和巴布达', 'Antigua and Barbuda', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '阿根廷', 'Argentina', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '亚美尼亚', 'Armenia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '阿森松', 'Ascension', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '澳大利亚', 'Australia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '奥地利', 'Austria', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '阿塞拜疆', 'Azerbaijan', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '巴哈马', 'Bahamas', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '巴林', 'Bahrain', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '孟加拉国', 'Bangladesh', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '巴巴多斯', 'Barbados', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '白俄罗斯', 'Belarus', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '比利时', 'Belgium', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '伯利兹', 'Belize', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '贝宁', 'Benin', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '百慕大群岛', 'Bermuda Is', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '玻利维亚', 'Bolivia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '博茨瓦纳', 'Botswana', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '巴西', 'Brazil', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '文莱', 'Brunei', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '保加利亚', 'Bulgaria', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '布基纳法索', 'Burkina Faso', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '缅甸', 'Burma', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '布隆迪', 'Burundi', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '喀麦隆', 'Cameroon', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '加拿大', 'Canada', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '开曼群岛', 'Cayman Is', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '中非共和国', 'Central African Republic', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '乍得', 'Chad', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '智利', 'Chile', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '哥伦比亚', 'Colombia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '刚果', 'Congo', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '库克群岛', 'Cook Is', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '哥斯达黎加', 'Costa Rica', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '古巴', 'Cuba', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '塞浦路斯', 'Cyprus', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '捷克', 'Czech Republic', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '丹麦', 'Denmark', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '吉布提', 'Djibouti', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '多米尼加共和国', 'Dominica Rep', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '厄瓜多尔', 'Ecuador', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '埃及', 'Egypt', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '萨尔瓦多', 'EI Salvador', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '爱沙尼亚', 'Estonia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '埃塞俄比亚', 'Ethiopia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '斐济', 'Fiji', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '芬兰', 'Finland', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '法国', 'France', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '法属圭亚那', 'French Guiana', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '法属玻利尼西亚', 'French Polynesia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '加蓬', 'Gabon', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '冈比亚', 'Gambia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '格鲁吉亚', 'Georgia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '德国', 'Germany', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '加纳', 'Ghana', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '直布罗陀', 'Gibraltar', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '希腊', 'Greece', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '格林纳达', 'Grenada', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '关岛', 'Guam', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '危地马拉', 'Guatemala', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '几内亚', 'Guinea', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '圭亚那', 'Guyana', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '海地', 'Haiti', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '洪都拉斯', 'Honduras', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '匈牙利', 'Hungary', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '冰岛', 'Iceland', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '印度', 'India', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '印度尼西亚', 'Indonesia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '伊朗', 'Iran', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '伊拉克', 'Iraq', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '爱尔兰', 'Ireland', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '以色列', 'Israel', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '意大利', 'Italy', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '科特迪瓦', 'Ivory Coast', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '牙买加', 'Jamaica', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '日本', 'Japan', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '约旦', 'Jordan', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '柬埔寨', 'Cambodia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '哈萨克斯坦', 'Kazakhstan', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '肯尼亚', 'Kenya', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '韩国', 'Korea', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '科威特', 'Kuwait', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '吉尔吉斯坦', 'Kyrgyzstan', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '老挝', 'Laos', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '拉脱维亚', 'Latvia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '黎巴嫩', 'Lebanon', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '莱索托', 'Lesotho', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '利比里亚', 'Liberia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '利比亚', 'Libya', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '列支敦士登', 'Liechtenstein', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '立陶宛', 'Lithuania', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '卢森堡', 'Luxembourg', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '马达加斯加', 'Madagascar', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '马拉维', 'Malawi', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '马来西亚', 'Malaysia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '马尔代夫', 'Maldives', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '马里', 'Mali', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '马耳他', 'Malta', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '马里亚那群岛', 'Mariana Is', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '马提尼克', 'Martinique', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '毛里求斯', 'Mauritius', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '墨西哥', 'Mexico', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '摩尔多瓦', 'Moldova', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '摩纳哥', 'Monaco', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '蒙古', 'Mongolia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '蒙特塞拉特岛', 'Montserrat Is', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '摩洛哥', 'Morocco', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '莫桑比克', 'Mozambique', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '纳米比亚', 'Namibia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '瑙鲁', 'Nauru', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '尼泊尔', 'Nepal', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '荷属安的列斯', 'Netherlands Antilles', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '荷兰', 'Netherlands', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '新西兰', 'New Zealand', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '尼加拉瓜', 'Nicaragua', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '尼日尔', 'Niger', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '尼日利亚', 'Nigeria', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '朝鲜', 'North Korea', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '挪威', 'Norway', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '阿曼', 'Oman', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '巴基斯坦', 'Pakistan', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '巴拿马', 'Panama', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '巴布亚新几内亚', 'Papua New Guinea', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '巴拉圭', 'Paraguay', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '秘鲁', 'Peru', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '菲律宾', 'Philippines', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '波兰', 'Poland', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '葡萄牙', 'Portugal', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '波多黎各', 'Puerto Rico', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '卡塔尔', 'Qatar', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '留尼旺', 'Reunion', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '罗马尼亚', 'Romania', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '俄罗斯', 'Russia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '圣卢西亚', 'Saint Lucia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '圣文森特岛', 'Saint Vincent', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '东萨摩亚(美)', 'Samoa Eastern', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '西萨摩亚', 'Samoa Western', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '圣马力诺', 'San Marino', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '圣多美和普林西比', 'Sao Tome and Principe', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '沙特阿拉伯', 'Saudi Arabia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '塞内加尔', 'Senegal', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '塞舌尔', 'Seychelles', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '塞拉利昂', 'Sierra Leone', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '新加坡', 'Singapore', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '斯洛伐克', 'Slovakia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '斯洛文尼亚', 'Slovenia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '所罗门群岛', 'Solomon Is', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '索马里', 'Somali', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '南非', 'South Africa', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '西班牙', 'Spain', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '斯里兰卡', 'SriLanka', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '苏丹', 'Sudan', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '苏里南', 'Suriname', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '斯威士兰', 'Swaziland', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '瑞典', 'Sweden', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '瑞士', 'Switzerland', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '叙利亚', 'Syria', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '塔吉克斯坦', 'Tajikistan', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '坦桑尼亚', 'Tanzania', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '泰国', 'Thailand', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '多哥', 'Togo', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '汤加', 'Tonga', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '特立尼达和多巴哥', 'Trinidad and Tobago', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '突尼斯', 'Tunisia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '土耳其', 'Turkey', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '土库曼斯坦', 'Turkmenistan', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '乌干达', 'Uganda', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '乌克兰', 'Ukraine', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '阿拉伯联合酋长国', 'United Arab Emirates', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '英国', 'United Kingdom', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '美国', 'United States of America', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '乌拉圭', 'Uruguay', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '乌兹别克斯坦', 'Uzbekistan', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '委内瑞拉', 'Venezuela', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '越南', 'Vietnam', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '也门', 'Yemen', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '南斯拉夫', 'Yugoslavia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '津巴布韦', 'Zimbabwe', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '扎伊尔', 'Zaire', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('国家', 0, '赞比亚', 'Zambia', 'country');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('货币', 0, '美元', 'USD', 'currency');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('货币', 0, '欧元', 'EUR', 'currency');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('货币', 0, '日元', 'JPY', 'currency');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('货币', 0, '英镑', 'GBP', 'currency');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('货币', 0, '加拿大元', 'CAD', 'currency');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('货币', 0, '澳大利亚元', 'AUD', 'currency');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('货币', 0, '瑞士法郎', 'CHF', 'currency');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('货币', 0, '人民币', 'CNY', 'currency');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('货币', 0, '瑞典克朗', 'SEK', 'currency');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('货币', 0, '新加坡元', 'SGD', 'currency');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('货币', 0, '韩元', 'KRW', 'currency');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('货币', 0, '印度卢比', 'INR', 'currency');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('货币', 0, '巴西雷亚尔', 'BRL', 'currency');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('货币', 0, '新西兰元', 'NZD', 'currency');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('货币', 0, '香港元', 'HKD', 'currency');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('货币', 0, '新台币', 'TWD', 'currency');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('货币', 0, '澳门元', 'MOP', 'currency');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('货币', 0, '俄罗斯卢布', 'RUB', 'currency');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('星期', 0, '星期一', 'Mon', 'week');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('星期', 0, '星期二', 'Tue', 'week');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('星期', 0, '星期三', 'Wed', 'week');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('星期', 0, '星期四', 'Thu', 'week');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('星期', 0, '星期五', 'Fri', 'week');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('星期', 0, '星期六', 'Sat', 'week');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('星期', 0, '星期日', 'Sun', 'week');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('月份', 0, '一月', 'Jan', 'month');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('月份', 0, '二月', 'Feb', 'month');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('月份', 0, '三月', 'Mar', 'month');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('月份', 0, '四月', 'Apr', 'month');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('月份', 0, '五月', 'May', 'month');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('月份', 0, '六月', 'Jun', 'month');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('月份', 0, '七月', 'Jul', 'month');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('月份', 0, '八月', 'Aug', 'month');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('月份', 0, '九月', 'Sep', 'month');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('月份', 0, '十月', 'Oct', 'month');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('月份', 0, '十一月', 'Nov', 'month');
INSERT INTO `sys_dict` (`name`, `dict_sort`, `label`, `value`, `dict_type`) VALUES ('月份', 0, '十二月', 'Dec', 'month');
COMMIT;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `filename` varchar(255) DEFAULT NULL COMMENT '文件名',
  `content` longblob NOT NULL COMMENT '文件内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件';

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `account` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '登录用户名',
  `ip` varchar(15) DEFAULT NULL COMMENT 'IP地址',
  `ua` varchar(255) DEFAULT NULL COMMENT '浏览器请求头',
  `msg` varchar(255) DEFAULT NULL COMMENT '消息',
  `success` bit(1) NOT NULL DEFAULT b'0' COMMENT '登录是否成功',
  `user_id` int unsigned DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录日志';

-- ----------------------------
-- Table structure for sys_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operate_log`;
CREATE TABLE `sys_operate_log` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `operate_time` datetime NOT NULL COMMENT '操作时间',
  `biz` varchar(16) CHARACTER SET utf8mb4 NOT NULL COMMENT '业务模块',
  `user_id` int unsigned NOT NULL COMMENT '操作用户ID',
  `operation_name` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '操作名称',
  `request` varchar(2048) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '请求体',
  `ip` varchar(20) NOT NULL COMMENT '请求来源IP',
  `method` varchar(6) CHARACTER SET utf8mb4 NOT NULL COMMENT 'http方法',
  `path` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '请求路径',
  `response` varchar(1024) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '响应体',
  `success` bit(1) NOT NULL DEFAULT b'0' COMMENT '操作是否成功',
  `trace_id` char(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '链路追踪ID',
  `measured` smallint unsigned NOT NULL DEFAULT '0' COMMENT '操作耗时(毫秒)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志';

-- ----------------------------
-- Table structure for sys_setting
-- ----------------------------
DROP TABLE IF EXISTS `sys_setting`;
CREATE TABLE `sys_setting` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `option_label` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '选项',
  `option_value` varchar(128) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '选项值',
  `data_type` varchar(32) CHARACTER SET utf8mb4 DEFAULT 'string' COMMENT 'Java数据类型',
  `description` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '选项描述',
  `default_value` varchar(128) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '默认值',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_label` (`option_label`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统设置';

-- ----------------------------
-- Records of sys_setting
-- ----------------------------
BEGIN;
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('captcha.on', 'false', 'java.lang.Boolean', '登录是否开启图形验证码', 'true');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('captcha.alive-time', '30', 'java.lang.Integer', '验证码存活时间（单位：分钟）', '30');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('captcha.digits', '6', 'java.lang.Integer', '验证码位数/长度', '6');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('captcha.between', '60', 'java.lang.Integer', '两次验证码请求间隔时间（单位：秒）', '60');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('captcha.retry-times', '3', 'java.lang.Integer', '重试登录次数', '3');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('captcha.chars', '23456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKMNPQRSTUVWXYZ', 'java.lang.String', '验证码字符集(一般去掉1 l L 0 o O 易混淆字符)', '23456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKMNPQRSTUVWXYZ');
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
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('pwd.enable-same', 'false', 'java.lang.Boolean', '能否和旧密码相同', 'false');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('pwd.change-max-retry-times', '3', 'java.lang.Integer', '使用旧密码修改密码重试次数上限', '3');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('auth.user.max-failed-times', '5', 'java.lang.Integer', '最大登录失败次数', '5');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('auth.user.lock-time', '30', 'java.lang.Integer', '锁定时间', '30');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('auth.user.lock-time-unit', 'MINUTES', 'java.lang.String', '锁定时间单位', 'MINUTES');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('auth.user.default-password', 'QAr$S2xreDb##tdm', 'java.lang.String', '默认密码', 'QAr$S2xreDb##tdm');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('auth.user.verification', 'password', 'java.lang.String', '认证方式 （password-密码登录、qrcode-扫码登录、code-验证码登录）', 'password');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('auth.user.open-registration', 'false', 'java.lang.Boolean', '是否开放用户注册', 'false');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('auth.user.auto-login', 'true', 'java.lang.Boolean', '注册后是否自动登录', 'true');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('auth.user.default-role', '3', 'java.lang.Long', '注册后默认角色ID', '3');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('auth.user.reset', 'false', 'java.lang.Boolean', '初次登录后是否需修改密码', 'false');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('spring.mail.protocol', 'smtp', 'java.lang.String', '发送邮件所使用的协议smtp或smtps', 'smtp');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('spring.mail.host', 'smtp.163.com', 'java.lang.String', '邮件服务器地址', 'smtp.163.com');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('spring.mail.port', '465', 'java.lang.Integer', '邮件服务器端口', '465');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('spring.mail.username', 'xxx@163.com', 'java.lang.String', '邮件发送账户', 'xxx@163.com');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('spring.mail.password', 'xxx', 'java.lang.String', '邮件发送账户密码或授权码', 'xxx');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('spring.mail.properties.mail.smtp.ssl.enable', 'true', 'java.lang.Boolean', '是否启用 SMTP SSL', 'true');
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
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('sms.verification-template', '【%s】验证码：%s，5分钟内有效，为了保障您的账户安全，请勿向他人泄漏验证码信息', 'java.lang.String', '短信验证码模板', '【%s】验证码：%s，5分钟内有效，为了保障您的账户安全，请勿向他人泄漏验证码信息');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('email.verification-template', '邮箱验证码为<b>%s</b>，验证码有效期为%s分钟!', 'java.lang.String', '邮件验证码模板', '邮箱验证码为<b>%s</b>，验证码有效期为%s分钟!');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('blacklist.enable', 'true', 'java.lang.Boolean', 'IP黑名单是否开启', 'true');
INSERT INTO `sys_setting` (`option_label`, `option_value`, `data_type`, `description`, `default_value`) VALUES ('whitelist.enable', 'true', 'java.lang.Boolean', 'IP白名单是否开启', 'true');
COMMIT;


-- ----------------------------
-- Table structure for sys_task
-- ----------------------------
DROP TABLE IF EXISTS `sys_task`;
CREATE TABLE `sys_task` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '定时任务名称',
  `cron` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '定时任务表达式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务';

-- ----------------------------
-- Table structure for sys_verification
-- ----------------------------
DROP TABLE IF EXISTS `sys_verification`;
CREATE TABLE `sys_verification` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `kind` enum('email','mobile') CHARACTER SET utf8mb4 NOT NULL COMMENT '验证码类型',
  `receiver` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '接收者',
  `content` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '内容',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='验证码发送记录';


-- ----------------------------
-- Table structure for sys_whitelist
-- ----------------------------
DROP TABLE IF EXISTS `sys_whitelist`;
CREATE TABLE `sys_whitelist` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `ip` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT 'IP',
  `end_time` datetime DEFAULT NULL COMMENT '截止日期',
  `remark` varchar(64) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ip` (`ip`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='IP白名单';

-- ----------------------------
-- Records of sys_whitelist
-- ----------------------------
BEGIN;
INSERT INTO `sys_whitelist` (`id`, `ip`, `end_time`, `remark`) VALUES (1, 'localhost', NULL, NULL);
INSERT INTO `sys_whitelist` (`id`, `ip`, `end_time`, `remark`) VALUES (2, '127.0.0.1', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for template
-- ----------------------------
DROP TABLE IF EXISTS `template`;
CREATE TABLE `template` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='模板表';

-- ----------------------------
-- Table structure for sys_counter
-- ----------------------------
DROP TABLE IF EXISTS `sys_counter`;
CREATE TABLE `sys_counter` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `biz` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '键',
  `number` bigint unsigned NOT NULL DEFAULT '0' COMMENT '计数值',
  PRIMARY KEY (`id`),
  KEY `uk_biz` (`biz`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='计数器';

SET FOREIGN_KEY_CHECKS = 1;
