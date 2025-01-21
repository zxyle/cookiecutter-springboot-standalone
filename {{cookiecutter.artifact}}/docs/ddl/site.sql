SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for site_feedback
-- ----------------------------
DROP TABLE IF EXISTS `site_feedback`;
CREATE TABLE `site_feedback` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `contacts` varchar(32) DEFAULT NULL COMMENT '联系方式',
  `description` varchar(2048) DEFAULT NULL COMMENT '详细描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='意见反馈';

-- ----------------------------
-- Table structure for site_friendly
-- ----------------------------
DROP TABLE IF EXISTS `site_friendly`;
CREATE TABLE `site_friendly` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `content` varchar(32) NOT NULL COMMENT '链接文本',
  `url` varchar(255) NOT NULL COMMENT '链接',
  `sort` tinyint NOT NULL COMMENT '排序',
  `enabled` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '友链是否启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='友情链接';
-- ----------------------------
-- Records of site_friendly
-- ----------------------------
BEGIN;
INSERT INTO `site_friendly` (`content`, `url`, `sort`, `enabled`) VALUES ('百度', 'https://www.baidu.com', 2, 1);
INSERT INTO `site_friendly` (`content`, `url`, `sort`, `enabled`) VALUES ('腾讯网', 'https://www.qq.com', 1, 1);
INSERT INTO `site_friendly` (`content`, `url`, `sort`, `enabled`) VALUES ('淘宝网', 'https://www.taobao.com', 3, 1);
COMMIT;

-- ----------------------------
-- Table structure for site_info
-- ----------------------------
DROP TABLE IF EXISTS `site_info`;
CREATE TABLE `site_info` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `param_key` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '参数名称',
  `param_value` text CHARACTER SET utf8mb4 NOT NULL COMMENT '参数值',
  `description` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_param_key` (`param_key`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统信息';
-- ----------------------------
-- Records of site_info
-- ----------------------------
BEGIN;
INSERT INTO `site_info` (`param_key`, `param_value`, `description`) VALUES ('icp', '浙ICP备0000号', '备案号');
INSERT INTO `site_info` (`param_key`, `param_value`, `description`) VALUES ('name', 'XXX管理系统', '系统名称');
INSERT INTO `site_info` (`param_key`, `param_value`, `description`) VALUES ('version', 'V1.0.0', '版本');
INSERT INTO `site_info` (`param_key`, `param_value`, `description`) VALUES ('language', '简体中文', '语言');
INSERT INTO `site_info` (`param_key`, `param_value`, `description`) VALUES ('favicon', 'B', '网站logo');
INSERT INTO `site_info` (`param_key`, `param_value`, `description`) VALUES ('copyright', 'XX有限公司版权所有', '版权信息');
INSERT INTO `site_info` (`param_key`, `param_value`, `description`) VALUES ('email', 'xxx@example.com', '技术支持邮箱');
INSERT INTO `site_info` (`param_key`, `param_value`, `description`) VALUES ('keywords', 'XXX', 'SEO关键字');
INSERT INTO `site_info` (`param_key`, `param_value`, `description`) VALUES ('description', 'XXX', 'SEO描述');
INSERT INTO `site_info` (`param_key`, `param_value`, `description`) VALUES ('agreement', 'G', '协议文本');
INSERT INTO `site_info` (`param_key`, `param_value`, `description`) VALUES ('homepage', 'example.com', '主页');
INSERT INTO `site_info` (`param_key`, `param_value`, `description`) VALUES ('title', 'XXX', '网站标题');
INSERT INTO `site_info` (`param_key`, `param_value`, `description`) VALUES ('tel', '400-111-2222', '联系电话');
INSERT INTO `site_info` (`param_key`, `param_value`, `description`) VALUES ('address', '浙江省杭州市余杭区文一西路10000号', '联系地址');
INSERT INTO `site_info` (`param_key`, `param_value`, `description`) VALUES ('about', 'XX公司是一家什么公司', '关于我们');
INSERT INTO `site_info` (`param_key`, `param_value`, `description`) VALUES ('user-agreement', '这是用户协议', '用户协议');
INSERT INTO `site_info` (`param_key`, `param_value`, `description`) VALUES ('privacy-policy', '这是隐私政策', '隐私政策');
COMMIT;

-- ----------------------------
-- Table structure for site_release
-- ----------------------------
DROP TABLE IF EXISTS `site_release`;
CREATE TABLE `site_release` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` varchar(16) CHARACTER SET utf8mb4 NOT NULL COMMENT '版本号',
  `description` varchar(1024) CHARACTER SET utf8mb4 NOT NULL COMMENT '版本描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='发布版本';
-- ----------------------------
-- Records of site_release
-- ----------------------------
BEGIN;
INSERT INTO `site_release` (`version`, `description`) VALUES ('V1.0.0', '第一个版本');
COMMIT;

-- ----------------------------
-- Table structure for site_faq
-- ----------------------------
DROP TABLE IF EXISTS `site_faq`;
CREATE TABLE `site_faq` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `question` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '问题',
  `answer` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '解答',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='常见问题';

-- ----------------------------
-- Table structure for site_realname
-- ----------------------------
DROP TABLE IF EXISTS `site_realname`;
CREATE TABLE `site_realname` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '姓名',
  `id_type` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '证件类型',
  `id_num` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '证件号码',
  `user_id` int unsigned NOT NULL COMMENT '用户id',
  `status` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '认证状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='实名认证';

-- ----------------------------
-- Table structure for site_checkin
-- ----------------------------
DROP TABLE IF EXISTS `site_checkin`;
CREATE TABLE `site_checkin` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `user_id` int unsigned NOT NULL COMMENT '用户ID',
  `checkin_date` date NOT NULL COMMENT '签到日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_date` (`user_id`,`checkin_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='签到';

-- ----------------------------
-- Table structure for site_banner
-- ----------------------------
DROP TABLE IF EXISTS `site_banner`;
CREATE TABLE `site_banner` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT '名称',
  `title` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT '标题',
  `description` text CHARACTER SET utf8mb4 NOT NULL COMMENT '描述',
  `image_url` varchar(512) CHARACTER SET utf8mb4 NOT NULL COMMENT '图片路径或URL',
  `link_url` varchar(512) CHARACTER SET utf8mb4 NOT NULL COMMENT '链接',
  `sort_order` int unsigned NOT NULL DEFAULT '0' COMMENT '展示顺序，越小越靠前',
  `status` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '状态 (1:启用, 0:禁用)',
  `position` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT '展示位置',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_pos_status` (`status`,`position`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='轮播图';

-- ----------------------------
-- Records of site_banner
-- ----------------------------
BEGIN;
INSERT INTO `site_banner` (`name`, `title`, `description`, `image_url`, `link_url`, `sort_order`, `status`, `position`) VALUES ('首页banner1', '欢迎来到我们的网站', '', '/images/banner1.jpg', '/', 1, 0, 'header');
COMMIT;

DROP TABLE IF EXISTS `site_bank_card`;
CREATE TABLE `site_bank_card` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `user_id` int unsigned NOT NULL COMMENT '用户ID',
  `card_no` varchar(20) NOT NULL COMMENT '银行卡号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_id` (`user_id`,`card_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='银行卡号';

SET FOREIGN_KEY_CHECKS = 1;