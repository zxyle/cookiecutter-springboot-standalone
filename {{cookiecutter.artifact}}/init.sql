CREATE DATABASE IF NOT EXISTS app CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE app;
-- ----------------------------
DROP TABLE IF EXISTS `template`;
CREATE TABLE IF NOT EXISTS `template`
(
    `id`          int unsigned     NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `create_time` datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `version`     int unsigned     NOT NULL DEFAULT 1 COMMENT '乐观锁版本号',
    `deleted`     tinyint unsigned not null default 0 COMMENT '逻辑删除1-删除 0-未删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='模板表';

-- ----------------------------
CREATE TABLE IF NOT EXISTS `user`
(
    `id`          BIGINT(20)       NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `create_time` datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `version`     int unsigned     NOT NULL DEFAULT 1 COMMENT '乐观锁版本号',
    `deleted`     tinyint unsigned NOT NULL DEFAULT 0 COMMENT '逻辑删除1-删除 0-未删除',
    `username`    VARCHAR(30)      NULL     DEFAULT NULL COMMENT '姓名',
    `age`         INT(11)          NULL     DEFAULT NULL COMMENT '年龄',
    `email`       VARCHAR(50)      NULL     DEFAULT NULL COMMENT '邮箱',
    `sex`         INT(11)          NULL     DEFAULT 1 COMMENT '性别',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

INSERT INTO user(id, username, age, email)
VALUES (1, 'Jone', 18, 'test1@baomidou.com'),
       (2, 'Jack', 20, 'test2@baomidou.com'),
       (3, 'Tom', 28, 'test3@baomidou.com'),
       (4, 'Sandy', 21, 'test4@baomidou.com'),
       (5, 'Billie', 24, 'test5@baomidou.com');

-- ----------------------------
DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `param_key` varchar(255) DEFAULT NULL COMMENT '参数键',
  `param_value` varchar(255) DEFAULT NULL COMMENT '参数值',
  `description` varchar(255) DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='系统信息';

INSERT INTO `sys_param` VALUES (1, '2021-11-22 13:51:33', '2021-11-22 13:51:33', 'icp', '浙ICP备XXX号', '备案号');
INSERT INTO `sys_param` VALUES (2, '2021-11-22 13:51:56', '2021-11-22 13:51:56', 'name', 'XX系统', '系统名称');
INSERT INTO `sys_param` VALUES (3, '2021-11-22 13:52:17', '2021-11-22 13:52:17', 'version', 'V1.0', '版本');
INSERT INTO `sys_param` VALUES (4, '2021-11-22 13:54:01', '2021-11-22 13:54:01', 'language', 'A', '语言');
INSERT INTO `sys_param` VALUES (5, '2021-11-22 13:54:13', '2021-11-22 13:54:13', 'favicon', 'B', '网站logo');
INSERT INTO `sys_param` VALUES (6, '2021-11-22 14:23:04', '2021-11-22 14:23:04', 'copyright', 'C', '版权信息');
INSERT INTO `sys_param` VALUES (7, '2021-11-22 14:23:32', '2021-11-22 14:23:32', 'email', 'D', '技术支持邮箱');
INSERT INTO `sys_param` VALUES (8, '2021-11-22 14:30:27', '2021-11-22 14:30:27', 'keywords', 'E', 'SEO关键字');
INSERT INTO `sys_param` VALUES (9, '2021-11-22 14:30:38', '2021-11-22 14:30:38', 'description', 'F', 'SEO描述');
INSERT INTO `sys_param` VALUES (10, '2021-11-22 14:35:32', '2021-11-22 14:35:32', 'agreement', 'G', '协议文本');
