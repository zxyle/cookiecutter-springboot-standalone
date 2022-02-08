CREATE DATABASE IF NOT EXISTS app CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE app;
DROP TABLE IF EXISTS `template`;
CREATE TABLE IF NOT EXISTS `template`
(
    `id`          int unsigned     NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `create_time` datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `version`     int unsigned     NOT NULL DEFAULT 1 COMMENT '乐观锁版本号',
    `deleted`     tinyint unsigned not null default 0 COMMENT '逻辑删除1-删除 0-未删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='模板表';

CREATE TABLE IF NOT EXISTS `user`
(
    `id`          BIGINT(20)       NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `create_time` datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
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
