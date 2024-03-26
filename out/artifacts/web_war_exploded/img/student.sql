/*
Navicat MySQL Data Transfer

Source Server         : yy
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2020-11-02 17:48:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `sequence` char(12) NOT NULL COMMENT '学号',
  `name` varchar(10) DEFAULT NULL COMMENT '姓名',
  `sex` char(1) DEFAULT '男' COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `card` char(18) DEFAULT NULL COMMENT '证件号',
  `nation` varchar(10) DEFAULT '汉族' COMMENT '民族',
  `nativePlace` varchar(20) DEFAULT NULL COMMENT '籍贯',
  `political` char(2) DEFAULT '群众' COMMENT '政治面貌:群众;团员;党员',
  `className` varchar(7) DEFAULT '' COMMENT '班级名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='学生基本信息表';

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', '200700010101', '丁艳艳', '女', '1990-05-21', '130203199005212012', '汉族', '山西省', '团员', '会计A0711');
INSERT INTO `student` VALUES ('2', '200700010102', '余志飞', '男', '1989-08-03', '140423198908031226', '回族', '宁夏回族自治区', '群众', '会计A0711');
INSERT INTO `student` VALUES ('3', '200700010103', '王凯丽', '女', '1987-11-05', '150021198711052024', '汉族', '河北省', '党员', '会计A0711');
INSERT INTO `student` VALUES ('4', '200700010201', '何富平', '男', '1991-11-01', '150104199111010913', '汉族', '辽宁省', '群众', '会计A0721');
INSERT INTO `student` VALUES ('5', '200700010202', '张华权', '男', '1986-04-19', '220021198604190728', '汉族', '吉林省', '团员', '会计A0721');
INSERT INTO `student` VALUES ('6', '200700010203', '曹元庆', '男', '1988-02-27', '220321198802273513', '维吾尔族', '新疆维吾尔自治区', '党员', '会计A0721');
INSERT INTO `student` VALUES ('7', '200700010801', '谢丽秋', '女', '1987-04-10', '250106198704101237', '汉族', '吉林省', '党员', '会计A0731');
INSERT INTO `student` VALUES ('8', '200700010802', '李平飞', '男', '1991-11-01', '350202199111011554', '汉族', '山西省', '群众', '会计A0731');
INSERT INTO `student` VALUES ('9', '200700010803', '王子建', '男', '1991-03-24', '350262199103241425', '汉族', '黑龙江省', '团员', '会计A0731');
INSERT INTO `student` VALUES ('10', '200700010901', '徐文彬', '男', '1992-04-23', '360001199204230012', '藏族', '西藏自治区', '群众', '机材A0711');
INSERT INTO `student` VALUES ('11', '200700010902', '陈圆圆', '女', '1990-06-12', '360002199006121234', '汉族', '黑龙江省', '党员', '机材A0711');
INSERT INTO `student` VALUES ('12', '200700010903', '李明祖', '男', '1990-09-24', '360002199009241020', '汉族', '天津市', '群众', '机材A0711');
INSERT INTO `student` VALUES ('13', '200700020601', '张大林', '男', '1989-12-24', '360003198912241012', '汉族', '天津市', '群众', '机材A0712');
INSERT INTO `student` VALUES ('14', '200700020602', '黄振宇', '男', '1989-12-31', '360023198912316026', '回族', '宁夏回族自治区', '群众', '机材A0712');
INSERT INTO `student` VALUES ('15', '200700020603', '张艳', '女', '1990-01-03', '360208199001030123', '汉族', '江苏省', '党员', '机材A0712');
INSERT INTO `student` VALUES ('16', '200700030401', '章蕾', '女', '1990-04-25', '360401199004254576', '汉族', '上海市', '团员', '土木A0711');
INSERT INTO `student` VALUES ('17', '200700030402', '刘勇', '男', '1992-04-21', '360412199204217216', '汉族', '福建省', '群众', '土木A0711');
INSERT INTO `student` VALUES ('18', '200700030403', '白诗诗', '女', '1991-06-27', '400003199106278006', '回族', '宁夏回族自治区', '党员', '土木A0711');
INSERT INTO `student` VALUES ('19', '200700030501', '杨明辉', '男', '1992-05-15', '420421199205150010', '汉族', '福建省', '团员', '土木A0712');
INSERT INTO `student` VALUES ('20', '200700030502', '彭杰', '男', '1990-11-24', '430001199011245301', '汉族', '北京市', '团员', '土木A0712');

-- ----------------------------
-- Table structure for `teacher`
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `sequence` char(5) NOT NULL COMMENT '工号',
  `name` varchar(10) DEFAULT NULL COMMENT '姓名',
  `sex` char(1) DEFAULT '男' COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `card` varchar(18) DEFAULT NULL COMMENT '证件号',
  `political` varchar(10) DEFAULT '汉族' COMMENT '民族',
  `nation` varchar(20) DEFAULT NULL COMMENT '籍贯',
  `nativePlace` char(2) DEFAULT '群众' COMMENT '政治面貌:群众;团员;党员',
  `department` varchar(10) DEFAULT NULL COMMENT '所在部门',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='教师基本信息表';

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('1', '14011', '杨启华', '女', '1990-05-21', '130203199005212012', '团员', '汉族', '山西', '会计学院');
INSERT INTO `teacher` VALUES ('2', '14012', '林佳颖', '男', '1989-08-03', '140423198908031226', '群众', '回族', '宁夏', '会计学院');
INSERT INTO `teacher` VALUES ('3', '14013', '胡月天', '女', '1987-11-05', '150021198711052024', '党员', '汉族', '河北', '会计学院');
INSERT INTO `teacher` VALUES ('4', '14021', '吴镇森', '男', '1991-11-01', '150104199111010913', '群众', '汉族', '辽宁', '电子商务学院');
INSERT INTO `teacher` VALUES ('5', '14022', '文嘉豪', '男', '1986-04-19', '220021198604190728', '团员', '汉族', '吉林', '电子商务学院');
INSERT INTO `teacher` VALUES ('6', '14031', '高晓丽', '男', '1988-02-27', '220321198802273513', '党员', '维吾尔族', '新疆', '电子商务学院');
INSERT INTO `teacher` VALUES ('7', '14032', '彭华伦', '女', '1987-04-10', '250106198704101237', '党员', '汉族', '吉林', '电子商务学院');
INSERT INTO `teacher` VALUES ('8', '14041', '范天宇', '男', '1991-11-01', '350202199111011554', '群众', '汉族', '山西', '信息科学与技术学院');
INSERT INTO `teacher` VALUES ('9', '14042', '刘云龙', '男', '1991-03-24', '350262199103241425', '团员', '汉族', '黑龙', '信息科学与技术学院');
INSERT INTO `teacher` VALUES ('10', '14043', '冯菲菲', '男', '1992-04-23', '360001199204230012', '群众', '藏族', '西藏', '信息科学与技术学院');
INSERT INTO `teacher` VALUES ('11', '14051', '张晓艳', '女', '1990-06-12', '360002199006121234', '党员', '汉族', '黑龙', '信息科学与技术学院');
INSERT INTO `teacher` VALUES ('12', '14052', '李牧之', '男', '1990-09-24', '360002199009241020', '群众', '汉族', '天津', '理学院');
INSERT INTO `teacher` VALUES ('13', '14053', '金香玉', '男', '1989-12-24', '360003198912241012', '群众', '汉族', '天津', '理学院');
INSERT INTO `teacher` VALUES ('14', '14061', '申屠刚', '男', '1989-12-31', '360023198912316026', '群众', '回族', '宁夏', '理学院');
