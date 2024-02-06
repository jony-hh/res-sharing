/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80030 (8.0.30)
 Source Host           : localhost:3306
 Source Schema         : t1

 Target Server Type    : MySQL
 Target Server Version : 80030 (8.0.30)
 File Encoding         : 65001

 Date: 01/12/2023 23:47:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


-- PART 1 账号系统
-- ----------------------------
-- Table structure for user_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_auth`;
CREATE TABLE `sys_auth`
(
    `id`            bigint           NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `uid`           bigint UNSIGNED  NOT NULL DEFAULT 0 COMMENT '用户id',
    `identity_type` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博',
    `identifier`    varchar(50)      NOT NULL DEFAULT '' COMMENT '手机号 邮箱 用户名或第三方应用的唯一标识',
    `certificate`   varchar(20)      NOT NULL DEFAULT '' COMMENT '密码凭证(站内的保存密码，站外的不保存或保存token)',
    `version`       int              NULL     DEFAULT NULL COMMENT '版本号',
    `deleted`       tinyint          NULL     DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
    `creator`       bigint           NULL     DEFAULT NULL COMMENT '创建者',
    `created_time`  timestamp        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '绑定时间',
    `updater`       bigint           NULL     DEFAULT NULL COMMENT '更新者',
    `updated_time`  timestamp        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新绑定时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `only` (`uid` ASC, `identity_type` ASC) USING BTREE,
    INDEX `idx_uid` (`uid` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4 COMMENT = '用户授权表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user_base
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `uid`              bigint           NOT NULL COMMENT '用户ID',
    `user_role`        tinyint UNSIGNED NOT NULL DEFAULT 2 COMMENT '2正常用户 3禁言用户 4虚拟用户 5运营',
    `register_source`  tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '注册来源：1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博',
    `username`         varchar(32)      NOT NULL DEFAULT '' COMMENT '用户账号，必须唯一',
    `nickname`         varchar(32)      NOT NULL DEFAULT '' COMMENT '用户昵称',
    `gender`           tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户性别 0-female 1-male',
    `birthday`         bigint UNSIGNED  NOT NULL DEFAULT 0 COMMENT '用户生日',
    `signature`        varchar(255)     NOT NULL DEFAULT '' COMMENT '用户个人签名',
    `mobile`           varchar(16)      NOT NULL DEFAULT '' COMMENT '手机号码(唯一)',
    `mobile_bind_time` int UNSIGNED     NOT NULL DEFAULT 0 COMMENT '手机号码绑定时间',
    `email`            varchar(100)     NOT NULL DEFAULT '' COMMENT '邮箱(唯一)',
    `email_bind_time`  int UNSIGNED     NOT NULL DEFAULT 0 COMMENT '邮箱绑定时间',
    `avatar`           varchar(255)     NOT NULL DEFAULT '' COMMENT '头像',
    `avatar200`        varchar(255)     NOT NULL DEFAULT '' COMMENT '头像 200x200x80',
    `avatar_src`       varchar(255)     NOT NULL DEFAULT '' COMMENT '原图头像',
    `push_token`       varchar(50)      NOT NULL COMMENT '用户设备push_token',
    `version`          int              NULL     DEFAULT NULL COMMENT '版本号',
    `deleted`          tinyint          NULL     DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
    `creator`          bigint           NULL     DEFAULT NULL COMMENT '创建者',
    `created_time`     timestamp        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`          bigint           NULL     DEFAULT NULL COMMENT '更新者',
    `updated_time`     timestamp        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4 COMMENT = '用户基础信息表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user_login_log
-- ----------------------------
DROP TABLE IF EXISTS `user_login_log`;
CREATE TABLE `user_login_log`
(
    `id`         bigint           NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `uid`        bigint UNSIGNED  NOT NULL DEFAULT 0 COMMENT '用户uid',
    `type`       tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '登录方式 第三方/邮箱/手机等',
    `command`    tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '操作类型 1登陆成功  2登出成功 3登录失败 4登出失败',
    `version`    varchar(32)      NOT NULL DEFAULT '1.0' COMMENT '客户端版本号',
    `client`     varchar(20)      NOT NULL DEFAULT 'dabaozha' COMMENT '客户端',
    `device_id`  varchar(64)      NOT NULL DEFAULT '' COMMENT '登录时设备号',
    `last_ip`    varchar(32)      NOT NULL DEFAULT '' COMMENT '登录ip',
    `os`         varchar(16)      NOT NULL DEFAULT '' COMMENT '手机系统',
    `os_version` varchar(32)      NOT NULL DEFAULT '' COMMENT '系统版本',
    `text`       varchar(200)     NOT NULL DEFAULT '' COMMENT '登录备注',
    `login_time` timestamp        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_uid_type_time` (`uid` ASC, `type` ASC, `login_time` ASC) USING BTREE,
    INDEX `idx_create_time` (`login_time` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4 COMMENT = '用户登陆日志表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user_register_log
-- ----------------------------
DROP TABLE IF EXISTS `user_register_log`;
CREATE TABLE `user_register_log`
(
    `id`              bigint           NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `uid`             bigint UNSIGNED  NOT NULL COMMENT '用户ID',
    `register_method` tinyint UNSIGNED NOT NULL COMMENT '注册方式1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博',
    `register_ip`     varchar(16)      NOT NULL DEFAULT '' COMMENT '注册IP',
    `register_client` varchar(16)      NOT NULL DEFAULT '' COMMENT '注册客户端',
    `register_time`   timestamp        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4 COMMENT = '用户注册日志表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user_update_log
-- ----------------------------
DROP TABLE IF EXISTS `user_update_log`;
CREATE TABLE `log_update`
(
    `id`                bigint          NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `uid`               bigint UNSIGNED NOT NULL COMMENT '用户ID',
    `attribute_name`    varchar(30)     NOT NULL COMMENT '属性名',
    `attribute_old_val` varchar(30)     NOT NULL DEFAULT '' COMMENT '属性对应旧的值',
    `attribute_new_val` varchar(30)     NOT NULL DEFAULT '' COMMENT '属性对应新的值',
    `update_time`       timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4 COMMENT = '用户更新日志表'
  ROW_FORMAT = DYNAMIC;


DROP TABLE IF EXISTS `sms_platform`;
CREATE TABLE `sms_platform`
(
    id          bigint       NOT NULL AUTO_INCREMENT COMMENT 'id',
    platform    tinyint COMMENT '平台类型  0：阿里云   1：腾讯云   2：七牛云   3：华为云',
    sign_name   varchar(100) NOT NULL COMMENT '短信签名',
    template_id varchar(100) NOT NULL COMMENT '短信模板',
    app_id      varchar(100) NOT NULL COMMENT '短信应用ID，如：腾讯云等',
    sender_id   varchar(100) NOT NULL COMMENT '腾讯云国际短信、华为云等需要',
    url         varchar(200) NOT NULL COMMENT '接入地址，如：华为云',
    access_key  varchar(100) COMMENT 'AccessKey',
    secret_key  varchar(100) COMMENT 'SecretKey',
    status      tinyint COMMENT '状态  0：禁用   1：启用',
    version     int COMMENT '版本号',
    deleted     tinyint COMMENT '删除标识  0：正常   1：已删除',
    creator     bigint COMMENT '创建者',
    create_time datetime COMMENT '创建时间',
    updater     bigint COMMENT '更新者',
    update_time datetime COMMENT '更新时间',
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='短信平台';

CREATE TABLE sms_log
(
    id          bigint      NOT NULL AUTO_INCREMENT COMMENT 'id',
    platform_id bigint COMMENT '平台ID',
    platform    tinyint COMMENT '平台类型',
    mobile      varchar(20) NOT NULL COMMENT '手机号',
    params      varchar(200) COMMENT '参数',
    status      tinyint COMMENT '状态  0：失败   1：成功',
    error       varchar(2000) COMMENT '异常信息',
    create_time datetime COMMENT '创建时间',
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='短信日志';

SET FOREIGN_KEY_CHECKS = 1;


-- PART 2 RBAC