/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50730 (5.7.30-log)
 Source Host           : localhost:3306
 Source Schema         : miaosha

 Target Server Type    : MySQL
 Target Server Version : 50730 (5.7.30-log)
 File Encoding         : 65001

 Date: 02/04/2024 10:40:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_seckill_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill_goods`;
CREATE TABLE `t_seckill_goods`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀商品ID',
  `seckill_price` decimal(10, 2) NOT NULL COMMENT '秒杀家',
  `stock_count` int(10) NOT NULL COMMENT '库存数量',
  `start_date` datetime NOT NULL COMMENT '秒杀开始时间',
  `end_date` datetime NOT NULL COMMENT '秒杀结束时间',
  `goods_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_seckill_goods
-- ----------------------------
INSERT INTO `t_seckill_goods` VALUES (1, 3999.00, 6, '2024-04-01 00:46:25', '2024-05-01 00:46:29', '');

-- ----------------------------
-- Table structure for t_seckill_order
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill_order`;
CREATE TABLE `t_seckill_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀订单ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `goods_id` bigint(20) NOT NULL COMMENT '商品ID',
  `is_pay` int(10) NOT NULL DEFAULT 0 COMMENT '是否支付 0未支付 1已支付',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 357 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_seckill_order
-- ----------------------------
INSERT INTO `t_seckill_order` VALUES (346, 10, 1, 1);
INSERT INTO `t_seckill_order` VALUES (349, 2, 1, 1);
INSERT INTO `t_seckill_order` VALUES (350, 4, 1, 1);
INSERT INTO `t_seckill_order` VALUES (352, 6, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
