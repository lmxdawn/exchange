/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : exchange-trade

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 26/05/2022 16:40:40
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
) ENGINE = InnoDB AUTO_INCREMENT = 132 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '币币委托订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of entrust_order
-- ----------------------------
INSERT INTO `entrust_order` VALUES (126, 4, 3, 1, 1, 1, 2.000000000000000000, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 2.000000000000000000, 0.000000000000000000, 2, '2022-05-25 17:39:09', '2022-05-25 17:39:09');
INSERT INTO `entrust_order` VALUES (127, 6, 3, 1, 1, 2, 1.800000000000000000, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 2.000000000000000000, 0.200000000000000000, 2, '2022-05-25 17:39:55', '2022-05-25 17:39:55');
INSERT INTO `entrust_order` VALUES (128, 4, 3, 1, 1, 1, 2.100000000000000000, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 2.100000000000000000, 0.000000000000000000, 2, '2022-05-25 17:40:38', '2022-05-25 17:40:38');
INSERT INTO `entrust_order` VALUES (129, 6, 3, 1, 1, 2, 1.800000000000000000, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 2.100000000000000000, 0.210000000000000000, 2, '2022-05-25 17:44:44', '2022-05-25 17:44:44');
INSERT INTO `entrust_order` VALUES (130, 4, 3, 1, 1, 1, 2.000000000000000000, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 2.000000000000000000, 0.000000000000000000, 2, '2022-05-25 17:45:17', '2022-05-25 17:45:17');
INSERT INTO `entrust_order` VALUES (131, 6, 3, 1, 1, 2, 1.500000000000000000, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 2.000000000000000000, 0.200000000000000000, 2, '2022-05-25 17:49:28', '2022-05-25 17:49:28');

-- ----------------------------
-- Table structure for entrust_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `entrust_order_detail`;
CREATE TABLE `entrust_order_detail`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `trade_coin_id` bigint(20) UNSIGNED NOT NULL COMMENT '交易币种ID',
  `coin_id` bigint(20) UNSIGNED NOT NULL COMMENT '计价币种ID',
  `member_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户ID',
  `order_id` bigint(20) UNSIGNED NOT NULL COMMENT '订单ID',
  `fee` decimal(26, 18) UNSIGNED NOT NULL COMMENT '手续费',
  `price` decimal(26, 18) NOT NULL COMMENT '价格',
  `amount` decimal(26, 18) UNSIGNED NOT NULL COMMENT '数量',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_mid_oid`(`member_id`, `order_id`, `create_time`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '委托订单明细表' ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of entrust_order_detail
-- ----------------------------
INSERT INTO `entrust_order_detail` VALUES (26, 3, 1, 6, 127, 0.200000000000000000, 2.000000000000000000, 1.000000000000000000, '2022-05-25 17:39:55', '2022-05-25 17:39:55');
INSERT INTO `entrust_order_detail` VALUES (27, 3, 1, 4, 126, 0.000000000000000000, 2.000000000000000000, 1.000000000000000000, '2022-05-25 17:39:55', '2022-05-25 17:39:55');
INSERT INTO `entrust_order_detail` VALUES (28, 3, 1, 6, 129, 0.210000000000000000, 2.100000000000000000, 1.000000000000000000, '2022-05-25 17:44:44', '2022-05-25 17:44:44');
INSERT INTO `entrust_order_detail` VALUES (29, 3, 1, 4, 128, 0.000000000000000000, 2.100000000000000000, 1.000000000000000000, '2022-05-25 17:44:44', '2022-05-25 17:44:44');
INSERT INTO `entrust_order_detail` VALUES (30, 3, 1, 6, 131, 0.200000000000000000, 2.000000000000000000, 1.000000000000000000, '2022-05-25 17:49:28', '2022-05-25 17:49:28');
INSERT INTO `entrust_order_detail` VALUES (31, 3, 1, 4, 130, 0.000000000000000000, 2.000000000000000000, 1.000000000000000000, '2022-05-25 17:49:28', '2022-05-25 17:49:28');

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
  `status` tinyint(3) UNSIGNED NOT NULL COMMENT '状态（0：禁用，1：正常）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tcid_cid`(`trade_coin_id`, `coin_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '所有币种符号（交易对）表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pair
-- ----------------------------
INSERT INTO `pair` VALUES (1, 3, 1, 0.10000000, 2, 0.00000000, 0, 0.000000000000000000, 0.000000000000000000, 4, 3, 2, 2.000000000000000000, 23.00, 56221.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0, 2, '2021-12-03 00:06:35', '2021-12-03 00:06:39');
INSERT INTO `pair` VALUES (2, 2, 1, 0.12000000, 4, 0.00000000, 0, 0.000000000000000000, 0.000000000000000000, 4, 2, 2, 52002.669000000000000000, 56932.00, 26545612.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0, 2, '2021-12-25 11:56:12', '2021-12-25 11:56:17');
INSERT INTO `pair` VALUES (3, 3, 2, 0.00000000, 4, 0.00000000, 0, 0.000000000000000000, 0.000000000000000000, 4, 2, 2, 45612.000000000000000000, 45612.00, 46512.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0, 2, '2021-12-25 14:36:47', '2021-12-25 14:36:51');

-- ----------------------------
-- Table structure for pair_robot
-- ----------------------------
DROP TABLE IF EXISTS `pair_robot`;
CREATE TABLE `pair_robot`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `pair_id` bigint(20) UNSIGNED NOT NULL COMMENT '交易对ID',
  `trade_coin_id` bigint(20) UNSIGNED NOT NULL COMMENT '交易币种ID',
  `coin_id` bigint(20) UNSIGNED NOT NULL COMMENT '计价币种ID',
  `lower_coin_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '小写的交易对名称',
  `upper_coin_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '大写的交易对名称',
  `min_amount` decimal(26, 18) NULL DEFAULT NULL COMMENT '最低交易量',
  `rand_range_0` decimal(26, 18) NULL DEFAULT NULL COMMENT '交易量随机数范围 1%概率',
  `randRange_1` decimal(26, 18) NULL DEFAULT NULL COMMENT '交易量随机数范围 9%概率',
  `rand_range_2` decimal(26, 18) NULL DEFAULT NULL COMMENT '交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率',
  `rand_range_3` decimal(26, 18) NULL DEFAULT NULL COMMENT '交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率',
  `rand_range_4` decimal(26, 18) NULL DEFAULT NULL COMMENT '交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率',
  `rand_range_5` decimal(26, 18) NULL DEFAULT NULL COMMENT '交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率',
  `rand_range_6` decimal(26, 18) NULL DEFAULT NULL COMMENT '交易量随机数范围0.1(0.0001 ~ 0.09) 10%概率',
  `price_precision` int(10) UNSIGNED NOT NULL COMMENT '价格精度',
  `amount_precision` int(10) UNSIGNED NOT NULL COMMENT '交易量精度',
  `max_sub_price` decimal(26, 18) NULL DEFAULT NULL COMMENT '买盘最高价与卖盘最低价相差超过该值',
  `init_order_count` int(10) UNSIGNED NULL DEFAULT 0 COMMENT '初始订单量',
  `price_step_rate` decimal(26, 18) NULL DEFAULT NULL COMMENT '价格变化步长(0.01 = 1%)',
  `run_time` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '行情请求间隔时间（单位毫秒 1000 = 1秒）',
  `status` tinyint(3) UNSIGNED NOT NULL COMMENT '状态（0：禁用，1：正常）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tcid_cid`(`trade_coin_id`, `coin_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '交易对机器人' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pair_robot
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
) ENGINE = InnoDB AUTO_INCREMENT = 144 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'USDT汇率表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of usdt_rate
-- ----------------------------
INSERT INTO `usdt_rate` VALUES (1, 'USD', '$', 1.00, 2, 0, 2, '2021-12-24 19:30:37', '2021-12-24 19:30:40');

SET FOREIGN_KEY_CHECKS = 1;
