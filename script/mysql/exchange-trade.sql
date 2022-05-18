/*
 Navicat Premium Data Transfer

 Source Server         : 本地服务
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:3306
 Source Schema         : exchange-trade

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 11/05/2022 14:02:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for entrust_order
-- ----------------------------
DROP TABLE IF EXISTS `entrust_order`;
CREATE TABLE `entrust_order`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `member_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户ID',
  `trade_coin_id` bigint(20) UNSIGNED NOT NULL COMMENT '交易币种ID',
  `coin_id` bigint(20) UNSIGNED NOT NULL COMMENT '计价币种ID',
  `type` tinyint(1) UNSIGNED NOT NULL COMMENT '类型（1：限价，2：市价）',
  `direction` tinyint(1) UNSIGNED NOT NULL COMMENT '方向（1：买入，2：卖出）',
  `price` decimal(26, 18) NOT NULL COMMENT '价格',
  `amount` decimal(26, 18) UNSIGNED NOT NULL COMMENT '数量',
  `amount_complete` decimal(26, 18) NOT NULL COMMENT '已完成数量',
  `total` decimal(26, 18) UNSIGNED NOT NULL COMMENT '交易额',
  `total_complete` decimal(26, 18) UNSIGNED NOT NULL DEFAULT 0.000000000000000000 COMMENT '已完成的交易额',
  `total_fee` decimal(26, 18) UNSIGNED NOT NULL DEFAULT 0.000000000000000000 COMMENT '总的手续费',
  `status` tinyint(3) UNSIGNED NOT NULL COMMENT '状态（1：未完成，2：已完成，3：已撤单）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '币币委托订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of entrust_order
-- ----------------------------
INSERT INTO `entrust_order` VALUES (1, 1, 3, 1, 2, 1, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-09 17:13:02', '2022-05-09 17:13:02');
INSERT INTO `entrust_order` VALUES (2, 1, 3, 1, 2, 1, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-09 19:09:19', '2022-05-09 19:09:19');
INSERT INTO `entrust_order` VALUES (3, 1, 3, 1, 2, 1, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-09 23:38:35', '2022-05-09 23:38:35');
INSERT INTO `entrust_order` VALUES (4, 1, 3, 1, 2, 1, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-09 23:42:58', '2022-05-09 23:42:58');
INSERT INTO `entrust_order` VALUES (5, 1, 3, 1, 2, 1, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-09 23:44:42', '2022-05-09 23:44:42');
INSERT INTO `entrust_order` VALUES (6, 1, 3, 1, 2, 1, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-09 23:49:02', '2022-05-09 23:49:02');
INSERT INTO `entrust_order` VALUES (7, 1, 3, 1, 2, 1, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-09 23:51:06', '2022-05-09 23:51:06');
INSERT INTO `entrust_order` VALUES (8, 1, 3, 1, 1, 1, 5.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-10 08:06:40', '2022-05-10 08:06:40');
INSERT INTO `entrust_order` VALUES (9, 1, 3, 1, 1, 1, 5.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-10 08:07:08', '2022-05-10 08:07:08');
INSERT INTO `entrust_order` VALUES (10, 1, 3, 1, 1, 1, 6.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-10 08:10:20', '2022-05-10 08:10:20');

-- ----------------------------
-- Table structure for pair
-- ----------------------------
DROP TABLE IF EXISTS `pair`;
CREATE TABLE `pair`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `trade_coin_id` bigint(20) UNSIGNED NOT NULL COMMENT '交易币种ID',
  `coin_id` bigint(20) UNSIGNED NOT NULL COMMENT '计价币种ID',
  `buy_fee` decimal(10, 8) UNSIGNED NOT NULL COMMENT '买入手续费率',
  `buy_fee_precision` int(10) UNSIGNED NOT NULL COMMENT '买入手续费精度',
  `sell_fee` decimal(10, 8) UNSIGNED NOT NULL COMMENT '卖出手续费率',
  `sell_fee_precision` int(10) UNSIGNED NOT NULL COMMENT '卖出手续费精度',
  `min_amount` decimal(26, 18) UNSIGNED NOT NULL DEFAULT 0.000000000000000000 COMMENT '最低交易数量',
  `min_total` decimal(26, 18) UNSIGNED NOT NULL DEFAULT 0.000000000000000000 COMMENT '最低交易额',
  `trade_total_precision` int(10) UNSIGNED NOT NULL COMMENT '交易额精度',
  `trade_price_precision` int(10) UNSIGNED NOT NULL COMMENT '价格精度',
  `trade_amount_precision` int(10) UNSIGNED NOT NULL COMMENT '交易量精度',
  `price` decimal(26, 18) UNSIGNED NOT NULL DEFAULT 0.000000000000000000 COMMENT '最新价格',
  `price_24` decimal(10, 2) NOT NULL COMMENT '(24h)之前的价格',
  `trade_total_24` decimal(26, 18) NOT NULL COMMENT '(24h)交易额',
  `trade_amount_24` decimal(26, 18) UNSIGNED NOT NULL DEFAULT 0.000000000000000000 COMMENT '(24h)交易数量',
  `highest_24` decimal(26, 18) UNSIGNED NOT NULL DEFAULT 0.000000000000000000 COMMENT '(24h)最高',
  `lowest_24` decimal(26, 18) UNSIGNED NOT NULL DEFAULT 0.000000000000000000 COMMENT '(24h)最低',
  `sort` int(10) NOT NULL DEFAULT 0 COMMENT '排序（升序）',
  `status` tinyint(3) UNSIGNED NOT NULL COMMENT '状态（1：关闭，2：开启）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tcid_cid`(`trade_coin_id`, `coin_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '所有币种符号（交易对）表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pair
-- ----------------------------
INSERT INTO `pair` VALUES (1, 3, 1, 0.10000000, 2, 0.00000000, 0, 0.000000000000000000, 0.000000000000000000, 4, 3, 2, 96.000000000000000000, 23.00, 56221.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0, 2, '2021-12-03 00:06:35', '2021-12-03 00:06:39');
INSERT INTO `pair` VALUES (2, 2, 1, 0.12000000, 4, 0.00000000, 0, 0.000000000000000000, 0.000000000000000000, 4, 2, 2, 52002.669000000000000000, 56932.00, 26545612.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0, 2, '2021-12-25 11:56:12', '2021-12-25 11:56:17');
INSERT INTO `pair` VALUES (3, 3, 2, 0.00000000, 4, 0.00000000, 0, 0.000000000000000000, 0.000000000000000000, 4, 2, 2, 45612.000000000000000000, 45612.00, 46512.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0, 2, '2021-12-25 14:36:47', '2021-12-25 14:36:51');

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
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

-- ----------------------------
-- Table structure for usdt_rate
-- ----------------------------
DROP TABLE IF EXISTS `usdt_rate`;
CREATE TABLE `usdt_rate`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `symbol` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '符号',
  `price` decimal(18, 2) NULL DEFAULT NULL COMMENT 'USDT汇率',
  `precision` int(10) UNSIGNED NOT NULL COMMENT '精度',
  `sort` int(10) NOT NULL DEFAULT 0 COMMENT '排序（升序）',
  `status` tinyint(3) UNSIGNED NOT NULL COMMENT '状态（1：关闭，2：开启）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'USDT汇率表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of usdt_rate
-- ----------------------------
INSERT INTO `usdt_rate` VALUES (1, 'USD', '$', 1.00, 2, 0, 2, '2021-12-24 19:30:37', '2021-12-24 19:30:40');

SET FOREIGN_KEY_CHECKS = 1;
