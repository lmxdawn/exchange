/*
 Navicat Premium Data Transfer

 Source Server         : 本地服务
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:3306
 Source Schema         : exchange-admin

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 31/12/2021 16:39:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for auth_admin
-- ----------------------------
DROP TABLE IF EXISTS `auth_admin`;
CREATE TABLE `auth_admin`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '登录密码；sp_password加密',
  `tel` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户手机号',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '登录邮箱',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户头像',
  `sex` smallint(1) NOT NULL DEFAULT 0 COMMENT '性别；0：保密，1：男；2：女',
  `last_login_ip` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '最后登录ip',
  `last_login_time` datetime NOT NULL COMMENT '最后登录时间',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '用户状态 0：禁用； 1：正常 ；2：未验证',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_login_key`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_admin
-- ----------------------------
INSERT INTO `auth_admin` VALUES (1, 'admin', 'c3284d0f94606de1fd2af172aba15bf3', 'admin', 'lmxdawn@gmail.com', 'sssss', 0, '0:0:0:0:0:0:0:1', '2021-11-28 22:04:11', '2018-07-06 17:19:00', 1);

-- ----------------------------
-- Table structure for auth_permission
-- ----------------------------
DROP TABLE IF EXISTS `auth_permission`;
CREATE TABLE `auth_permission`  (
  `role_id` int(11) UNSIGNED NOT NULL COMMENT '角色',
  `permission_rule_id` int(11) NOT NULL DEFAULT 0 COMMENT '权限id',
  `type` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限规则分类，请加应用前缀,如admin_'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限授权表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_permission
-- ----------------------------
INSERT INTO `auth_permission` VALUES (1, 4, 'admin');
INSERT INTO `auth_permission` VALUES (1, 3, 'admin');
INSERT INTO `auth_permission` VALUES (1, 2, 'admin');
INSERT INTO `auth_permission` VALUES (1, 1, 'admin');

-- ----------------------------
-- Table structure for auth_permission_rule
-- ----------------------------
DROP TABLE IF EXISTS `auth_permission_rule`;
CREATE TABLE `auth_permission_rule`  (
  `id` int(8) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '规则编号',
  `pid` int(11) NOT NULL DEFAULT 0 COMMENT '父级id',
  `name` char(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '规则唯一标识',
  `title` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '规则中文名称',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态：为1正常，为0禁用',
  `condition` char(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '规则表达式，为空表示存在就验证，不为空表示按照条件验证',
  `listorder` int(10) NOT NULL DEFAULT 0 COMMENT '排序，优先级，越小优先级越高',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '规则表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_permission_rule
-- ----------------------------
INSERT INTO `auth_permission_rule` VALUES (1, 0, 'user_manage', '用户管理', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (2, 1, 'user_manage/admin_manage', '管理组', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (3, 2, 'auth_admin/index', '管理员管理', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (4, 3, 'auth_admin/save', '添加管理员', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (5, 3, 'auth_admin/edit', '编辑管理员', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (6, 3, 'auth_admin/delete', '删除管理员', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (7, 2, 'auth_role/index', '角色管理', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (8, 7, 'auth_role/save', '添加角色', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (9, 7, 'auth_role/edit', '编辑角色', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (10, 7, 'auth_role/delete', '删除角色', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (11, 7, 'auth_role/auth', '角色授权', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (12, 2, 'auth_permission_rule/index', '权限管理', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (13, 12, 'auth_permission_rule/save', '添加权限', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (14, 12, 'auth_permission_rule/edit', '编辑权限', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (15, 12, 'auth_permission_rule/delete', '删除权限', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (16, 0, 'ad_manage', '广告相关', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (17, 16, 'ad_site/index', '广告位管理', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (18, 17, 'ad_site/save', '广告位添加', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (19, 17, 'ad_site/edit', '广告位编辑', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (20, 17, 'ad_site/delete', '广告位删除', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (21, 16, 'ad/index', '广告管理', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (22, 21, 'ad/save', '广告添加', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (23, 21, 'ad/edit', '广告编辑', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (24, 21, 'ad/delete', '广告删除', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (25, 17, 'ad_site/adlist', '广告位选择时的广告列表', 1, '', 999, '2018-07-06 17:19:00', '2018-07-06 17:19:00');
INSERT INTO `auth_permission_rule` VALUES (26, 7, 'auth/role/authList', '角色授权列表', 1, '', 999, '2021-11-17 14:08:14', '2021-11-17 14:08:17');

-- ----------------------------
-- Table structure for auth_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `pid` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '父角色ID',
  `status` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '状态',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `listorder` int(3) NOT NULL DEFAULT 0 COMMENT '排序，优先级，越小优先级越高',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_role
-- ----------------------------
INSERT INTO `auth_role` VALUES (1, '超级管理员', 0, 1, '拥有网站最高管理员权限！', '2018-07-06 17:19:00', '2018-07-06 17:19:00', 0);

-- ----------------------------
-- Table structure for auth_role_admin
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_admin`;
CREATE TABLE `auth_role_admin`  (
  `role_id` int(11) UNSIGNED NULL DEFAULT 0 COMMENT '角色 id',
  `admin_id` int(11) NULL DEFAULT 0 COMMENT '管理员id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色对应表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_role_admin
-- ----------------------------

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
