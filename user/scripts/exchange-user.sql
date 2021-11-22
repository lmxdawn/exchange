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

 Date: 22/11/2021 16:10:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `uid` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `tel` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `pwd` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个性签名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `uk_tel`(`tel`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES (1, '15213230873', 'c3284d0f94606de1fd2af172aba15bf3', '555', '11', '22', '2021-09-10 23:10:08', '2021-09-10 23:10:08');
INSERT INTO `member` VALUES (2, '15213230874', 'c3284d0f94606de1fd2af172aba15bf3', '999', '22', '33', '2021-09-10 23:10:08', '2021-09-10 23:10:08');
INSERT INTO `member` VALUES (3, '15213230875', 'c3284d0f94606de1fd2af172aba15bf3', '666', '44', '55', '2021-09-10 23:10:08', '2021-09-10 23:10:08');

SET FOREIGN_KEY_CHECKS = 1;
