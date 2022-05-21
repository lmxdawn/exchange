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

 Date: 22/05/2022 01:47:18
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
) ENGINE = InnoDB AUTO_INCREMENT = 94 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '币币委托订单表' ROW_FORMAT = DYNAMIC;

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
INSERT INTO `entrust_order` VALUES (11, 1, 3, 1, 1, 1, 6.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-11 17:25:19', '2022-05-11 17:25:19');
INSERT INTO `entrust_order` VALUES (12, 1, 3, 1, 1, 1, 7.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-11 17:28:43', '2022-05-11 17:28:43');
INSERT INTO `entrust_order` VALUES (13, 1, 3, 1, 1, 1, 7.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-11 17:31:49', '2022-05-11 17:31:49');
INSERT INTO `entrust_order` VALUES (14, 1, 3, 1, 1, 1, 8.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-11 17:38:52', '2022-05-11 17:38:52');
INSERT INTO `entrust_order` VALUES (15, 1, 3, 1, 1, 1, 9.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-11 17:39:05', '2022-05-11 17:39:05');
INSERT INTO `entrust_order` VALUES (16, 1, 3, 1, 1, 1, 10.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-11 17:39:08', '2022-05-11 17:39:08');
INSERT INTO `entrust_order` VALUES (17, 1, 3, 1, 1, 1, 11.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-11 17:39:10', '2022-05-11 17:39:10');
INSERT INTO `entrust_order` VALUES (18, 1, 3, 1, 2, 1, 0.000000000000000000, 0.000000000000000000, 0.040000000000000000, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 2, '2022-05-11 17:39:42', '2022-05-11 17:39:42');
INSERT INTO `entrust_order` VALUES (19, 1, 3, 1, 2, 1, 0.000000000000000000, 0.000000000000000000, 0.040000000000000000, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 2, '2022-05-11 17:40:21', '2022-05-11 17:40:21');
INSERT INTO `entrust_order` VALUES (20, 1, 3, 1, 1, 2, 22.000000000000000000, 1.000000000000000000, 0.040000000000000000, 0.000000000000000000, 0.880000000000000000, 0.080000000000000000, 1, '2022-05-11 17:41:42', '2022-05-11 17:41:42');
INSERT INTO `entrust_order` VALUES (21, 1, 3, 1, 1, 2, 22.000000000000000000, 1.000000000000000000, 0.040000000000000000, 0.000000000000000000, 0.880000000000000000, 0.080000000000000000, 1, '2022-05-11 17:41:59', '2022-05-11 17:41:59');
INSERT INTO `entrust_order` VALUES (22, 1, 3, 1, 1, 2, 23.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-11 17:42:08', '2022-05-11 17:42:08');
INSERT INTO `entrust_order` VALUES (23, 1, 3, 1, 1, 2, 24.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-11 17:42:11', '2022-05-11 17:42:11');
INSERT INTO `entrust_order` VALUES (24, 1, 3, 1, 1, 2, 25.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-11 17:42:15', '2022-05-11 17:42:15');
INSERT INTO `entrust_order` VALUES (25, 1, 3, 1, 1, 2, 26.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-11 17:42:18', '2022-05-11 17:42:18');
INSERT INTO `entrust_order` VALUES (26, 1, 3, 1, 1, 2, 27.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-11 17:42:27', '2022-05-11 17:42:27');
INSERT INTO `entrust_order` VALUES (27, 1, 3, 1, 1, 2, 28.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-11 17:42:29', '2022-05-11 17:42:29');
INSERT INTO `entrust_order` VALUES (28, 1, 3, 1, 1, 2, 29.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-11 17:42:33', '2022-05-11 17:42:33');
INSERT INTO `entrust_order` VALUES (29, 1, 3, 1, 1, 2, 40.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-11 17:42:54', '2022-05-11 17:42:54');
INSERT INTO `entrust_order` VALUES (30, 1, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-11 17:43:04', '2022-05-11 17:43:04');
INSERT INTO `entrust_order` VALUES (31, 1, 3, 1, 1, 1, 2.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-11 17:43:16', '2022-05-11 17:43:16');
INSERT INTO `entrust_order` VALUES (32, 4, 3, 1, 1, 1, 1.000000000000000000, 2.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-15 11:23:23', '2022-05-15 11:23:23');
INSERT INTO `entrust_order` VALUES (33, 4, 3, 1, 1, 1, 1.000000000000000000, 2.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-15 11:23:32', '2022-05-15 11:23:32');
INSERT INTO `entrust_order` VALUES (34, 4, 3, 1, 1, 1, 1.000000000000000000, 2.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-15 13:11:17', '2022-05-15 13:11:17');
INSERT INTO `entrust_order` VALUES (35, 4, 3, 1, 1, 1, 2.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-15 13:15:06', '2022-05-15 13:15:06');
INSERT INTO `entrust_order` VALUES (36, 4, 3, 1, 1, 1, 2.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-15 13:15:06', '2022-05-15 13:15:06');
INSERT INTO `entrust_order` VALUES (37, 4, 3, 1, 1, 1, 1.000000000000000000, 3.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-15 13:16:06', '2022-05-15 13:16:06');
INSERT INTO `entrust_order` VALUES (38, 4, 3, 1, 1, 1, 1.000000000000000000, 3.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-15 13:16:17', '2022-05-15 13:16:17');
INSERT INTO `entrust_order` VALUES (39, 4, 3, 1, 1, 1, 1.000000000000000000, 3.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-15 13:20:27', '2022-05-15 13:20:27');
INSERT INTO `entrust_order` VALUES (40, 4, 3, 1, 1, 1, 1.000000000000000000, 3.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-15 13:20:38', '2022-05-15 13:20:38');
INSERT INTO `entrust_order` VALUES (41, 4, 3, 1, 1, 1, 1.000000000000000000, 3.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-15 13:20:40', '2022-05-15 13:20:40');
INSERT INTO `entrust_order` VALUES (42, 4, 3, 1, 1, 1, 1.000000000000000000, 3.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-15 13:20:41', '2022-05-15 13:20:41');
INSERT INTO `entrust_order` VALUES (43, 4, 3, 1, 1, 1, 1.000000000000000000, 2.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-15 13:21:40', '2022-05-15 13:21:40');
INSERT INTO `entrust_order` VALUES (44, 4, 3, 1, 1, 1, 2.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-15 13:23:26', '2022-05-15 13:23:26');
INSERT INTO `entrust_order` VALUES (45, 4, 3, 1, 1, 1, 1.000000000000000000, 2.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-15 13:35:30', '2022-05-15 13:35:30');
INSERT INTO `entrust_order` VALUES (46, 4, 3, 1, 1, 1, 5.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-15 13:35:53', '2022-05-15 13:35:53');
INSERT INTO `entrust_order` VALUES (47, 4, 3, 1, 1, 1, 6.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-15 13:36:13', '2022-05-15 13:36:13');
INSERT INTO `entrust_order` VALUES (48, 4, 3, 1, 1, 1, 6.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-15 13:37:01', '2022-05-15 13:37:01');
INSERT INTO `entrust_order` VALUES (49, 4, 3, 1, 1, 1, 1.000000000000000000, 2.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-17 10:47:35', '2022-05-17 10:47:35');
INSERT INTO `entrust_order` VALUES (50, 4, 3, 1, 1, 1, 1.000000000000000000, 2.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-17 10:50:47', '2022-05-17 10:50:47');
INSERT INTO `entrust_order` VALUES (51, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-17 10:52:06', '2022-05-17 10:52:06');
INSERT INTO `entrust_order` VALUES (52, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-17 10:54:10', '2022-05-17 10:54:10');
INSERT INTO `entrust_order` VALUES (53, 4, 3, 1, 1, 1, 1.000000000000000000, 1.010000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-17 11:00:24', '2022-05-17 11:00:24');
INSERT INTO `entrust_order` VALUES (54, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-17 11:43:32', '2022-05-17 11:43:32');
INSERT INTO `entrust_order` VALUES (55, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-17 11:43:45', '2022-05-17 11:43:45');
INSERT INTO `entrust_order` VALUES (56, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-17 11:44:10', '2022-05-17 11:44:10');
INSERT INTO `entrust_order` VALUES (57, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-17 11:44:21', '2022-05-17 11:44:21');
INSERT INTO `entrust_order` VALUES (58, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-17 11:44:36', '2022-05-17 11:44:36');
INSERT INTO `entrust_order` VALUES (59, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-17 11:44:56', '2022-05-17 11:44:56');
INSERT INTO `entrust_order` VALUES (60, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-17 11:45:30', '2022-05-17 11:45:30');
INSERT INTO `entrust_order` VALUES (61, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-17 11:46:56', '2022-05-17 11:46:56');
INSERT INTO `entrust_order` VALUES (62, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-17 11:47:24', '2022-05-17 11:47:24');
INSERT INTO `entrust_order` VALUES (63, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-17 11:47:54', '2022-05-17 11:47:54');
INSERT INTO `entrust_order` VALUES (64, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-17 11:48:29', '2022-05-17 11:48:29');
INSERT INTO `entrust_order` VALUES (65, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 2, '2022-05-17 11:54:49', '2022-05-17 11:54:49');
INSERT INTO `entrust_order` VALUES (66, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-20 13:30:21', '2022-05-20 13:30:21');
INSERT INTO `entrust_order` VALUES (67, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-20 13:30:45', '2022-05-20 13:30:45');
INSERT INTO `entrust_order` VALUES (68, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-20 13:33:38', '2022-05-20 13:33:38');
INSERT INTO `entrust_order` VALUES (69, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-20 13:36:16', '2022-05-20 13:36:16');
INSERT INTO `entrust_order` VALUES (70, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-20 13:37:20', '2022-05-20 13:37:20');
INSERT INTO `entrust_order` VALUES (71, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-20 13:46:11', '2022-05-20 13:46:11');
INSERT INTO `entrust_order` VALUES (72, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-20 13:47:42', '2022-05-20 13:47:42');
INSERT INTO `entrust_order` VALUES (73, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-20 13:58:29', '2022-05-20 13:58:29');
INSERT INTO `entrust_order` VALUES (74, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-20 14:00:24', '2022-05-20 14:00:24');
INSERT INTO `entrust_order` VALUES (75, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-20 14:01:01', '2022-05-20 14:01:01');
INSERT INTO `entrust_order` VALUES (76, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-20 14:04:38', '2022-05-20 14:04:38');
INSERT INTO `entrust_order` VALUES (77, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-20 14:10:11', '2022-05-20 14:10:11');
INSERT INTO `entrust_order` VALUES (78, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-20 14:11:11', '2022-05-20 14:11:11');
INSERT INTO `entrust_order` VALUES (79, 4, 3, 1, 1, 1, 2.000000000000000000, 1.000000000000000000, 0.900000000000000000, 0.000000000000000000, 1.800000000000000000, 0.000000000000000000, 1, '2022-05-20 14:11:29', '2022-05-20 14:11:29');
INSERT INTO `entrust_order` VALUES (80, 4, 3, 1, 1, 1, 3.000000000000000000, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 3.000000000000000000, 0.000000000000000000, 2, '2022-05-20 18:09:48', '2022-05-20 18:09:48');
INSERT INTO `entrust_order` VALUES (81, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-20 18:10:16', '2022-05-20 18:10:16');
INSERT INTO `entrust_order` VALUES (82, 4, 3, 1, 1, 1, 4.000000000000000000, 0.100000000000000000, 0.100000000000000000, 0.000000000000000000, 0.400000000000000000, 0.000000000000000000, 2, '2022-05-20 18:13:58', '2022-05-20 18:13:58');
INSERT INTO `entrust_order` VALUES (83, 4, 3, 1, 1, 2, 2.000000000000000000, 2.000000000000000000, 2.000000000000000000, 0.000000000000000000, 5.200000000000000000, 0.520000000000000000, 2, '2022-05-20 18:15:35', '2022-05-20 18:15:35');
INSERT INTO `entrust_order` VALUES (84, 4, 3, 1, 1, 2, 3.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-20 18:15:53', '2022-05-20 18:15:53');
INSERT INTO `entrust_order` VALUES (85, 4, 3, 1, 1, 2, 4.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-20 18:16:46', '2022-05-20 18:16:46');
INSERT INTO `entrust_order` VALUES (86, 4, 3, 1, 1, 2, 5.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-20 18:16:58', '2022-05-20 18:16:58');
INSERT INTO `entrust_order` VALUES (87, 4, 3, 1, 1, 2, 6.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-20 18:19:22', '2022-05-20 18:19:22');
INSERT INTO `entrust_order` VALUES (88, 4, 3, 1, 1, 2, 7.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-20 18:19:33', '2022-05-20 18:19:33');
INSERT INTO `entrust_order` VALUES (89, 6, 3, 1, 1, 1, 3.000000000000000000, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 3.000000000000000000, 0.000000000000000000, 2, '2022-05-20 21:39:59', '2022-05-20 21:39:59');
INSERT INTO `entrust_order` VALUES (90, 4, 3, 1, 1, 1, 3.000000000000000000, 1.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 0.000000000000000000, 1, '2022-05-20 21:41:17', '2022-05-20 21:41:17');
INSERT INTO `entrust_order` VALUES (91, 4, 3, 1, 1, 2, 3.000000000000000000, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 3.000000000000000000, 0.300000000000000000, 2, '2022-05-20 21:41:35', '2022-05-20 21:41:35');
INSERT INTO `entrust_order` VALUES (92, 4, 3, 1, 1, 1, 1.000000000000000000, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 1.000000000000000000, 0.000000000000000000, 2, '2022-05-21 15:38:19', '2022-05-21 15:38:19');
INSERT INTO `entrust_order` VALUES (93, 6, 3, 1, 1, 2, 1.000000000000000000, 1.000000000000000000, 1.000000000000000000, 0.000000000000000000, 1.000000000000000000, 0.100000000000000000, 2, '2022-05-21 15:38:56', '2022-05-21 15:38:56');

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
) ENGINE = MyISAM AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '委托订单明细表' ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of entrust_order_detail
-- ----------------------------
INSERT INTO `entrust_order_detail` VALUES (1, 3, 1, 4, 92, 0.000000000000000000, 1.000000000000000000, 1.000000000000000000, '2022-05-21 15:38:56', '2022-05-21 15:38:56');

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
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '所有币种符号（交易对）表' ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

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
