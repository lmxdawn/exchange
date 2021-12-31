/*
 Navicat Premium Data Transfer

 Source Server         : 本地服务
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:3306
 Source Schema         : exchange-user

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 31/12/2021 16:39:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `member_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `tel` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `pwd` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个性签名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`member_id`) USING BTREE,
  UNIQUE INDEX `uk_tel`(`tel`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES (1, '15213230873', 'c3284d0f94606de1fd2af172aba15bf3', '555', '11', '22', '2021-09-10 23:10:08', '2021-09-10 23:10:08');
INSERT INTO `member` VALUES (2, '15213230874', 'c3284d0f94606de1fd2af172aba15bf3', '999', '22', '33', '2021-09-10 23:10:08', '2021-09-10 23:10:08');
INSERT INTO `member` VALUES (3, '15213230875', 'c3284d0f94606de1fd2af172aba15bf3', '666', '44', '55', '2021-09-10 23:10:08', '2021-09-10 23:10:08');

-- ----------------------------
-- Table structure for member_bill
-- ----------------------------
DROP TABLE IF EXISTS `member_bill`;
CREATE TABLE `member_bill`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `member_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户ID',
  `coin_id` bigint(20) UNSIGNED NOT NULL COMMENT '币种ID',
  `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类别（member_bill_category表）',
  `type` tinyint(3) UNSIGNED NOT NULL COMMENT '类型（1：收入，2：支出）',
  `money` decimal(26, 18) NOT NULL DEFAULT 0.000000000000000000 COMMENT '金额',
  `fee` decimal(26, 18) UNSIGNED NOT NULL DEFAULT 0.000000000000000000 COMMENT '手续费',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_date` date NOT NULL COMMENT '创建日期',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of member_bill
-- ----------------------------
INSERT INTO `member_bill` VALUES (1, 1, 1, 'trade', 1, 1.800000000000000000, 0.000000000000000000, '币币交易买入', '2021-12-17', '2021-12-17 17:32:42', '2021-12-17 17:32:42');
INSERT INTO `member_bill` VALUES (2, 1, 3, 'trade', 2, 9.000000000000000000, 0.000000000000000000, '币币交易解冻买入', '2021-12-17', '2021-12-17 17:32:42', '2021-12-17 17:32:42');
INSERT INTO `member_bill` VALUES (3, 1, 3, 'trade', 1, 8.100000000000000000, 0.900000000000000000, '币币交易卖出', '2021-12-17', '2021-12-17 17:32:42', '2021-12-17 17:32:42');
INSERT INTO `member_bill` VALUES (4, 1, 1, 'trade', 2, 1.800000000000000000, 0.000000000000000000, '币币交易解冻卖出', '2021-12-17', '2021-12-17 17:32:42', '2021-12-17 17:32:42');
INSERT INTO `member_bill` VALUES (5, 1, 1, 'trade', 1, 0.200000000000000000, 0.000000000000000000, '币币交易买入', '2021-12-17', '2021-12-17 17:34:13', '2021-12-17 17:34:13');
INSERT INTO `member_bill` VALUES (6, 1, 3, 'trade', 2, 1.000000000000000000, 0.000000000000000000, '币币交易解冻买入', '2021-12-17', '2021-12-17 17:34:13', '2021-12-17 17:34:13');
INSERT INTO `member_bill` VALUES (7, 1, 3, 'trade', 1, 0.900000000000000000, 0.100000000000000000, '币币交易卖出', '2021-12-17', '2021-12-17 17:34:13', '2021-12-17 17:34:13');
INSERT INTO `member_bill` VALUES (8, 1, 1, 'trade', 2, 0.200000000000000000, 0.000000000000000000, '币币交易解冻卖出', '2021-12-17', '2021-12-17 17:34:13', '2021-12-17 17:34:13');

-- ----------------------------
-- Table structure for member_bill_category
-- ----------------------------
DROP TABLE IF EXISTS `member_bill_category`;
CREATE TABLE `member_bill_category`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '账单类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of member_bill_category
-- ----------------------------
INSERT INTO `member_bill_category` VALUES (1, 'recharge', '充值');
INSERT INTO `member_bill_category` VALUES (2, 'withdraw', '提现');
INSERT INTO `member_bill_category` VALUES (3, 'transfer', '转账');
INSERT INTO `member_bill_category` VALUES (4, 'trade', '币币交易');

-- ----------------------------
-- Table structure for member_coin
-- ----------------------------
DROP TABLE IF EXISTS `member_coin`;
CREATE TABLE `member_coin`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `member_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户ID',
  `coin_id` bigint(20) UNSIGNED NOT NULL COMMENT '币种ID',
  `balance` decimal(26, 18) UNSIGNED NOT NULL COMMENT '可用余额',
  `frozen_balance` decimal(26, 18) UNSIGNED NOT NULL COMMENT '冻结余额',
  `status` tinyint(3) UNSIGNED NOT NULL COMMENT '状态（1：关闭，2：开启）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_mid_cid`(`member_id`, `coin_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户钱包表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of member_coin
-- ----------------------------
INSERT INTO `member_coin` VALUES (1, 1, 1, 100.000000000000000000, 0.000000000000000000, 1, '2021-12-02 23:37:03', '2021-12-02 23:37:08');
INSERT INTO `member_coin` VALUES (2, 1, 3, 99.000000000000000000, 0.000000000000000000, 1, '2021-12-06 09:16:03', '2021-12-06 09:16:03');

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
