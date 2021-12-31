/*
 Navicat Premium Data Transfer

 Source Server         : 本地服务
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:3306
 Source Schema         : exchange-wallet

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 31/12/2021 16:39:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for coin
-- ----------------------------
DROP TABLE IF EXISTS `coin`;
CREATE TABLE `coin`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '币种ID自增',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '币种别名',
  `coin_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '币种全称',
  `symbol` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '币种单位',
  `precision` int(11) NOT NULL COMMENT '币种精度',
  `usdt_price` decimal(26, 10) UNSIGNED NOT NULL COMMENT 'USDT价格',
  `sort` int(10) NOT NULL DEFAULT 0 COMMENT '排序（升序）',
  `status` tinyint(3) UNSIGNED NOT NULL COMMENT '状态（1：关闭，2：开启）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coin
-- ----------------------------
INSERT INTO `coin` VALUES (1, 'USDT', 'Usdt', 'USDT', 8, 1.0000000000, 2, 2, '2021-11-28 22:10:42', '2021-11-28 22:18:27');
INSERT INTO `coin` VALUES (2, 'BTC', 'Bitcoin', 'BTC', 8, 51006.7000000000, 0, 2, '2021-12-06 08:58:20', '2021-12-06 08:58:22');
INSERT INTO `coin` VALUES (3, 'ETH', 'Ether', 'ETH', 8, 4052.3700000000, 0, 2, '2021-12-06 09:00:11', '2021-12-06 09:00:14');

-- ----------------------------
-- Table structure for coin_conf
-- ----------------------------
DROP TABLE IF EXISTS `coin_conf`;
CREATE TABLE `coin_conf`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `coin_id` bigint(20) NOT NULL COMMENT '币种ID',
  `protocol_id` bigint(20) NOT NULL COMMENT '协议ID',
  `private_key` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '当前币种协议的转账私钥',
  `contract_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '合约地址（代币需要）',
  `is_withdraw` tinyint(1) NOT NULL COMMENT '是否可提现（1：否，2：是）',
  `withdraw_rate` decimal(3, 2) NULL DEFAULT NULL COMMENT '提现费率',
  `min_withdraw_fee` decimal(26, 18) NULL DEFAULT NULL COMMENT '最低提现费用',
  `min_withdraw` decimal(26, 18) NULL DEFAULT NULL COMMENT '最低提现',
  `max_withdraw` decimal(26, 18) NULL DEFAULT NULL COMMENT '最大提现',
  `is_recharge` tinyint(1) NOT NULL COMMENT '是否可充值（1：否，2：是）',
  `min_recharge` decimal(26, 18) NULL DEFAULT NULL COMMENT '最低充值',
  `confirmations` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '确认数量',
  `min_gather` decimal(26, 18) NULL DEFAULT NULL COMMENT '最低归集数量（满足当前数量自动归集）',
  `gather_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '归集的地址',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_cid_pid`(`coin_id`, `protocol_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '币种配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coin_conf
-- ----------------------------

-- ----------------------------
-- Table structure for coin_protocol
-- ----------------------------
DROP TABLE IF EXISTS `coin_protocol`;
CREATE TABLE `coin_protocol`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '协议ID自增',
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '协议名称',
  `rpc_host` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'rpc主机',
  `rpc_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'rpc登录用户',
  `rpc_pwd` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'rpc登录密码',
  `sort` int(10) NOT NULL DEFAULT 0 COMMENT '排序（升序）',
  `status` tinyint(3) UNSIGNED NOT NULL COMMENT '状态（1：关闭，2：开启）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uk_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '币种协议表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coin_protocol
-- ----------------------------
INSERT INTO `coin_protocol` VALUES (1, 'USDT', 'string', 'string', '11', 2, 2, '2021-11-28 22:17:24', '2021-11-28 22:20:08');
INSERT INTO `coin_protocol` VALUES (2, 'BTC', 'string', 'string', '11', 2, 2, '2021-11-28 22:17:24', '2021-11-28 22:20:08');
INSERT INTO `coin_protocol` VALUES (3, 'ETH', 'string', 'string', '11', 2, 2, '2021-11-28 22:17:24', '2021-11-28 22:20:08');

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
