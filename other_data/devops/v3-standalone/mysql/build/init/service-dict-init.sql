/* 数据库全名 = edu_dict
 Date: 19/08/2020 10:57:24
*/
CREATE SCHEMA IF NOT EXISTS `edu_dict` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

grant all privileges on edu_dict.* to 'edu'@'%';

USE `edu_dict`;



-- ----------------------------
-- Table structure for dict_item
-- ----------------------------
CREATE TABLE `edu_dict`.`dict_item`
(
    `id`           bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `dict_code`    varchar(12)  NOT NULL DEFAULT '' COMMENT '代码编码',
    `dict_name`    varchar(50)  NOT NULL DEFAULT '' COMMENT '代码名称',
    `dict_value`   varchar(11)  NULL     DEFAULT '' COMMENT '代码值',
    `type_code`    varchar(8)   NOT NULL DEFAULT '' COMMENT '代码分类',
    `sort_num`     int(11)      NULL     DEFAULT NULL COMMENT '排序',
    `is_valid`     tinyint(1)   NOT NULL DEFAULT 1 COMMENT '是否有效(1有效0无效)',
    `is_lock`      tinyint(1)   NOT NULL DEFAULT 1 COMMENT '是否锁定(1锁定不可修改0未锁定可修改)',
    `out_sign`     varchar(8)   NULL     DEFAULT '' COMMENT '输出符号',
    `effect_date`  timestamp(0) NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '代码生效日期',
    `field1`       varchar(50)  NULL     DEFAULT NULL COMMENT '预留1',
    `field2`       varchar(50)  NULL     DEFAULT NULL COMMENT '预留2',
    `field3`       varchar(50)  NULL     DEFAULT NULL COMMENT '预留3',
    `created_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`   varchar(36)  NULL     DEFAULT NULL COMMENT '创建者',
    `updated_by`   varchar(36)  NULL     DEFAULT NULL COMMENT '更新者',
    `deleted`      bit(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY uk_dict_code (`dict_code`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT = '系统_字典表';


-- ----------------------------
-- Table structure for dict_type
-- ----------------------------
CREATE TABLE `edu_dict`.`dict_type`
(
    `id`           int(11)      NOT NULL AUTO_INCREMENT COMMENT 'id',
    `type_code`    varchar(8)   NOT NULL COMMENT '代码分类编码',
    `type_name`    varchar(48)  NOT NULL COMMENT '代码分类名称',
    `parent_code`  varchar(8)   NOT NULL COMMENT '上级代码分类的分类标识',
    `sort_num`     int(11)      NULL     DEFAULT 99 COMMENT '排序',
    `level_in`     tinyint(1)   NULL     DEFAULT NULL COMMENT '层级',
    `is_lock`      tinyint(1)   NULL     DEFAULT 1 COMMENT '是否锁定(1可修改0不可修改)',
    `is_valid`     tinyint(1)   NULL     DEFAULT 1 COMMENT '是否有效(1有效0无效)',
    `vn`           varchar(8)   NULL     DEFAULT '1_0' COMMENT '代码版本：不同程序版本中体现',
    `system_id`    varchar(8)   NULL     DEFAULT 'RD' COMMENT '系统id',
    `created_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`   varchar(36)  NULL     DEFAULT NULL COMMENT '创建者',
    `updated_by`   varchar(36)  NULL     DEFAULT NULL COMMENT '更新者',
    `deleted`      bit(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT = '系统_字典_类型表';





-- ----------------------------
-- Records of dict_item
-- ----------------------------
INSERT INTO `dict_item` VALUES (1, '0011_001', '公立', '', '0011', 1, 1, 1, '', '2020-02-13 16:05:45', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (2, '0011_002', '民办', '', '0011', 2, 1, 1, '', '2020-02-13 16:05:45', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (4, '0012_002', '初中', '', '0012', 2, 1, 1, '', '2020-02-13 16:05:46', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (5, '0012_003', '高中', '', '0012', 3, 1, 1, '', '2020-02-13 16:05:46', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (6, '0007_001', '物理实验室', '', '0007', 1, 1, 1, '', '2020-02-13 16:05:46', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (7, '0007_002', '化学实验室', '', '0007', 2, 1, 1, '', '2020-02-13 16:05:46', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (8, '0014_001', '一年级', '', '0014', 1, 1, 1, '', '2020-02-13 16:05:46', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (9, '0014_002', '二年级', '', '0014', 2, 1, 1, '', '2020-02-13 16:05:46', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (10, '0014_003', '三年级', '', '0014', 3, 1, 1, '', '2020-02-13 16:05:46', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (18, '0016_001', '男', '', '0016', 1, 1, 1, '', '2020-02-13 16:05:46', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (19, '0016_002', '女', '', '0016', 2, 1, 1, '', '2020-02-13 16:05:46', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (20, '0017_001', '三级教师', '', '0017', 1, 1, 1, '', '2020-02-13 16:05:46', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (21, '0017_002', '二级教师', '', '0017', 2, 1, 1, '', '2020-02-13 16:05:46', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (22, '0017_003', '一级教师', '', '0017', 3, 1, 1, '', '2020-02-13 16:05:46', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (23, '0017_004', '高级教师', '', '0017', 4, 1, 1, '', '2020-02-13 16:05:46', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (24, '0017_005', '教授', '', '0017', 5, 1, 1, '', '2020-02-13 16:05:46', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (25, '0018_001', '物理', '', '0018', 1, 1, 1, '', '2020-02-13 16:05:46', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (26, '0018_002', '化学', '', '0018', 2, 1, 1, '', '2020-02-13 16:05:46', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (28, '0019_001', '单项选择题', '单选', '0019', 1, 1, 1, '', '2020-03-17 07:38:20', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (29, '0019_003', '判断题', '', '0019', 3, 1, 1, '', '2020-02-13 16:05:25', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (30, '0019_004', '填空题', '', '0019', 4, 1, 1, '', '2020-02-13 16:05:25', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (31, '0019_005', '其他题型', '', '0019', 5, 1, 1, '', '2020-03-17 07:39:41', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (32, '0020_001', '个人', '', '0020', 1, 1, 1, '', '2020-02-13 16:05:25', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (33, '0020_002', '公开', '', '0020', 2, 1, 1, '', '2020-02-13 16:05:25', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (34, '0021_001', '人教版', '', '0021', 1, 1, 1, '', '2020-02-13 16:05:25', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (35, '0021_002', '沪教版', '', '0021', 2, 1, 1, '', '2020-02-13 16:05:25', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (36, '0021_003', '苏教版', '', '0021', 3, 1, 1, '', '2020-02-13 16:05:25', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (37, '0022_001', '音频', '', '0022', 1, 1, 1, '', '2020-02-13 16:05:25', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (38, '0022_002', '视频', '', '0022', 2, 1, 1, '', '2020-02-13 16:05:25', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (39, '0022_003', '图片', '', '0022', 3, 1, 1, '', '2020-02-13 16:05:25', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (40, '0022_004', 'Excel', '', '0022', 4, 1, 1, '', '2020-02-13 16:05:25', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (41, '0022_005', 'Word', '', '0022', 5, 1, 1, '', '2020-02-13 16:05:25', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (42, '0022_006', 'PPT', '', '0022', 6, 1, 1, '', '2020-02-13 16:05:25', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (43, '0022_007', 'TXT', '', '0022', 7, 1, 1, '', '2020-02-13 16:05:25', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (44, '0022_008', 'PDF', '', '0022', 8, 1, 1, '', '2020-02-13 16:05:25', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (45, '0022_009', '其他', '', '0022', 9, 1, 1, '', '2020-04-26 02:51:31', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (46, '0023_001', '物理', '', '0023', 1, 1, 1, '', '2020-02-13 16:05:25', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (47, '0023_002', '化学', '', '0023', 2, 1, 1, '', '2020-02-13 16:05:26', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (48, '0024_001', '一人一组', '1', '0024', 1, 1, 1, '', '2020-02-18 11:12:48', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (49, '0024_002', '两人一组', '2', '0024', 2, 1, 1, '', '2020-02-18 11:12:46', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (50, '0024_003', '三人一组', '3', '0024', 3, 1, 1, '', '2020-02-18 11:12:45', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (51, '0024_004', '四人一组', '4', '0024', 4, 1, 1, '', '2020-02-18 11:12:45', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (52, '0025_001', '正常', '', '0025', 1, 1, 1, '', '2020-02-13 16:05:27', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (53, '0025_002', '异常', '', '0025', 2, 1, 1, '', '2020-02-13 16:05:27', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (54, '0025_003', '待分配', '', '0025', 3, 1, 1, '', '2020-02-13 16:05:34', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (55, '0025_004', '已分配', '', '0025', 4, 1, 1, '', '2020-02-13 16:05:34', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (56, '0025_005', '其他', '', '0025', 5, 1, 1, '', '2020-02-13 16:05:34', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (57, '0019_002', '多项选择题', '多选', '0019', 2, 1, 1, '', '2020-03-17 07:38:33', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (58, '0026_001', '竖向排列', '1', '0026', 1, 1, 1, '', '2020-02-12 20:27:18', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (59, '0026_002', '横向每行2列', '2', '0026', 2, 1, 1, '', '2020-02-12 20:28:44', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (60, '0026_003', '横向每行3列', '3', '0026', 3, 1, 1, '', '2020-02-12 20:28:45', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (61, '0026_004', '横向每行4列', '4', '0026', 4, 1, 1, '', '2020-02-12 20:42:55', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (62, '0031_001', '随堂考', '', '0031', 1, 1, 1, '', '2020-02-13 13:45:56', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (63, '0031_002', '周考', '', '0031', 2, 1, 1, '', '2020-02-13 13:45:56', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (64, '0031_003', '月考', '', '0031', 3, 1, 1, '', '2020-02-13 13:45:56', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (65, '0031_004', '期中考', '', '0031', 4, 1, 1, '', '2020-02-13 13:45:56', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (66, '0031_005', '期末考', '', '0031', 5, 1, 1, '', '2020-02-13 13:45:56', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (67, '0031_006', '会考', '', '0031', 6, 1, 1, '', '2020-02-13 13:45:56', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (68, '0031_007', '中考', '', '0031', 7, 1, 1, '', '2020-02-13 13:45:56', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (69, '0031_008', '高考', '', '0031', 8, 1, 1, '', '2020-02-13 13:45:56', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (70, '0032_001', 'A组', '', '0032', 1, 1, 1, '', '2020-02-13 13:57:11', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (71, '0032_002', 'B组', '', '0032', 2, 1, 1, '', '2020-02-13 13:57:13', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (72, '0033_001', '待审核', '', '0033', 1, 1, 1, '', '2020-02-13 16:04:29', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (73, '0033_002', '审核通过', '', '0033', 2, 1, 1, '', '2020-03-13 07:03:27', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (74, '0033_003', '已驳回', '', '0033', 3, 1, 1, '', '2020-03-13 07:03:37', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (76, '0034_001', '指定分配', '', '0034', 1, 1, 1, '', '2020-02-13 19:39:42', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (77, '0034_002', '随机分配', '', '0034', 2, 1, 1, '', '2020-02-13 20:07:34', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (78, '0034_003', '按考生', '', '0034', 3, 1, 1, '', '2020-02-13 20:46:02', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (79, '0034_004', '按班级', '', '0034', 4, 1, 1, '', '2020-02-13 20:46:06', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (80, '0035_001', '未开始', '', '0035', 1, 1, 1, '', '2020-03-13 03:11:28', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (81, '0035_002', '进行中', '', '0035', 2, 1, 1, '', '2020-03-23 07:49:50', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (82, '0035_003', '未下发', '', '0035', 3, 1, 1, '', '2020-03-13 03:11:47', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (83, '0036_001', '草稿', '', '0036', 1, 1, 1, '', '2020-04-21 02:32:41', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (84, '0036_002', '编辑完成', '', '0036', 2, 1, 1, '', '2020-04-21 02:32:48', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (85, '0036_003', '其他', '', '0036', 3, 1, 1, '', '2020-04-21 02:33:06', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (86, '0035_005', '已结束', '', '0035', 5, 1, 1, '', '2020-03-13 03:18:05', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (90, '0038_001', '第一节课', '', '0038', 1, 1, 1, '', '2020-04-27 08:47:35', '08:00:00', '08:45:00', NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (91, '0038_002', '第二节课', '', '0038', 2, 1, 1, '', '2020-04-27 08:47:35', '09:00:00', '09:45:00', NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (92, '0038_003', '第三节课', '', '0038', 3, 1, 1, '', '2020-04-27 08:47:35', '10:00:00', '10:45:00', NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (93, '0038_004', '第四节课', '', '0038', 4, 1, 1, '', '2020-04-27 08:47:35', '11:00:00', '11:45:00', NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (94, '0038_005', '第五节课', '', '0038', 5, 1, 1, '', '2020-04-27 08:47:35', '13:00:00', '13:45:00', NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (95, '0038_006', '第六节课', '', '0038', 6, 1, 1, '', '2020-04-27 08:47:35', '14:00:00', '14:45:00', NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (96, '0038_007', '第七节课', '', '0038', 7, 1, 1, '', '2020-04-27 08:47:20', '15:00:00', '15:45:00', NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (97, '0038_008', '第八节课', '', '0038', 8, 1, 1, '', '2020-04-27 08:47:25', '16:00:00', '16:45:00', NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (98, '0038_009', '第九节课', '', '0038', 9, 1, 1, '', '2020-04-27 08:47:35', '17:00:00', '17:45:00', NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (103, '0015_001', '物理实验室', '', '0015', 1, 1, 1, '', '2020-03-09 08:10:13', NULL, NULL, NULL, '2020-03-09 08:09:52', '2020-03-09 08:09:52', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (104, '0015_002', '化学实验室', '', '0015', 2, 1, 1, '', '2020-03-09 08:10:35', NULL, NULL, NULL, '2020-03-09 08:10:32', '2020-03-09 08:10:32', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (105, '0013_001', '2019', '', '0013', 1, 1, 1, '', '2020-03-09 08:11:07', NULL, NULL, NULL, '2020-03-09 08:11:07', '2020-03-09 08:11:07', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (106, '0013_002', '2020', '', '0013', 2, 1, 1, '', '2020-03-09 08:11:26', NULL, NULL, NULL, '2020-03-09 08:11:26', '2020-03-09 08:11:26', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (107, '0013_003', '2021', '', '0013', 3, 1, 1, '', '2020-03-09 08:11:46', NULL, NULL, NULL, '2020-03-09 08:11:46', '2020-03-09 08:11:46', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (108, '0035_004', '已撤回', '', '0035', 4, 1, 1, '', '2020-08-26 06:12:51', NULL, NULL, NULL, '2020-03-13 03:16:51', '2020-03-13 03:16:51', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (128, '0046_003', '已阅卷', '', '0046', 3, 1, 1, '', '2020-03-28 02:18:09', NULL, NULL, NULL, '2020-03-18 05:59:02', '2020-03-18 05:59:02', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (132, '0046_002', '待阅卷', '', '0046', 2, 1, 1, '', '2020-03-28 02:18:10', NULL, NULL, NULL, '2020-03-23 08:00:28', '2020-03-23 08:00:28', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (134, '0046_001', '已认证未考试', '', '0046', 1, 1, 1, '', '2020-05-07 00:57:47', NULL, NULL, NULL, '2020-03-28 02:21:19', '2020-03-28 02:21:19', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (136, '0047_001', '初试', '', '0047', 1, 1, 1, '', '2020-04-30 15:03:49', NULL, NULL, NULL, '2020-04-30 15:03:49', '2020-04-30 15:03:49', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (137, '0047_002', '补考', '', '0047', 2, 1, 1, '', '2020-04-30 15:04:07', NULL, NULL, NULL, '2020-04-30 15:04:07', '2020-04-30 15:04:07', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (138, '0046_004', '未认证', '', '0046', 4, 1, 1, '', '2020-05-06 07:44:58', NULL, NULL, NULL, '2020-05-06 07:44:39', '2020-05-06 07:44:39', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (139, '0046_005', '考试中', '', '0046', 5, 1, 1, '', '2020-05-07 00:57:55', NULL, NULL, NULL, '2020-05-06 07:44:53', '2020-05-06 07:44:53', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (141, '0049_001', '摄像头', '', '0049', 1, 1, 1, '', '2020-07-20 01:42:45', NULL, NULL, NULL, '2020-07-20 01:42:18', '2020-07-20 01:42:18', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (142, '0050_001', '前摄像头1', 'front-1', '0050', 1, 1, 1, '', '2020-09-27 06:40:23', NULL, NULL, NULL, '2020-07-20 01:42:56', '2020-07-20 01:42:56', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (143, '0050_003', '顶摄像头', 'top-1', '0050', 3, 1, 1, '', '2020-09-27 06:41:03', NULL, NULL, NULL, '2020-07-20 01:43:04', '2020-07-20 01:43:04', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (144, '0050_004', '边摄像头', 'side-1', '0050', 4, 1, 1, '', '2020-09-27 06:41:11', NULL, NULL, NULL, '2020-07-20 01:43:09', '2020-07-20 01:43:09', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (145, '0051_001', '正常', '', '0051', 1, 1, 1, '', '2020-07-20 03:06:56', NULL, NULL, NULL, '2020-07-20 03:06:10', '2020-07-20 03:06:10', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (146, '0051_002', '异常', '', '0051', 2, 1, 1, '', '2020-07-20 03:06:57', NULL, NULL, NULL, '2020-07-20 03:06:13', '2020-07-20 03:06:13', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (147, '0052_001', '考试', '', '0052', 1, 1, 1, '', '2020-08-19 01:20:49', NULL, NULL, NULL, '2020-08-19 01:19:20', '2020-08-19 01:19:20', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (148, '0052_002', '课程', '', '0052', 2, 1, 1, '', '2020-08-19 01:20:50', NULL, NULL, NULL, '2020-08-19 01:19:24', '2020-08-19 01:19:24', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (149, '0052_003', '其他', '', '0052', 3, 1, 1, '', '2020-08-19 01:20:06', NULL, NULL, NULL, '2020-08-19 01:20:06', '2020-08-19 01:20:06', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (153, '0053_001', '未开始', '', '0053', 1, 1, 1, '', '2020-08-24 07:13:32', NULL, NULL, NULL, '2020-08-24 07:12:46', '2020-08-24 07:12:46', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (154, '0053_002', '进行中', '', '0053', 2, 1, 1, '', '2020-08-24 07:14:00', NULL, NULL, NULL, '2020-08-24 07:12:50', '2020-08-24 07:12:50', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (155, '0053_003', '已结束', '', '0053', 3, 1, 1, '', '2020-08-24 07:14:01', NULL, NULL, NULL, '2020-08-24 07:13:18', '2020-08-24 07:13:18', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (156, '0014_004', '预初', '', '0014', 0, 1, 1, '', '2020-02-13 16:05:46', NULL, NULL, NULL, '2020-02-17 10:22:43', '2020-02-17 10:22:43', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (157, '0013_004', '2022', '', '0013', 3, 1, 1, '', '2020-09-02 02:58:58', NULL, NULL, NULL, '2020-03-09 08:11:46', '2020-03-09 08:11:46', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (158, '0013_005', '2023', '', '0013', 3, 1, 1, '', '2020-03-09 08:11:46', NULL, NULL, NULL, '2020-03-09 08:11:46', '2020-03-09 08:11:46', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (159, '0013_006', '2024', '', '0013', 3, 1, 1, '', '2020-03-09 08:11:46', NULL, NULL, NULL, '2020-03-09 08:11:46', '2020-03-09 08:11:46', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (160, '0013_007', '2025', '', '0013', 3, 1, 1, '', '2020-03-09 08:11:46', NULL, NULL, NULL, '2020-03-09 08:11:46', '2020-03-09 08:11:46', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (161, '0013_008', '2026', '', '0013', 3, 1, 1, '', '2020-03-09 08:11:46', NULL, NULL, NULL, '2020-03-09 08:11:46', '2020-03-09 08:11:46', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (162, '0013_009', '2027', '', '0013', 3, 1, 1, '', '2020-09-02 03:01:29', NULL, NULL, NULL, '2020-03-09 08:11:46', '2020-03-09 08:11:46', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (163, '0013_010', '2028', '', '0013', 3, 1, 1, '', '2020-09-02 03:01:29', NULL, NULL, NULL, '2020-03-09 08:11:46', '2020-03-09 08:11:46', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (164, '0013_011', '2029', '', '0013', 3, 1, 1, '', '2020-09-02 03:01:29', NULL, NULL, NULL, '2020-03-09 08:11:46', '2020-03-09 08:11:46', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (165, '0013_012', '2030', '', '0013', 3, 1, 1, '', '2020-09-02 03:01:29', NULL, NULL, NULL, '2020-03-09 08:11:46', '2020-03-09 08:11:46', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (166, '0055_001', '教师', '', '0055', 1, 1, 1, '', '2020-09-02 03:01:29', NULL, NULL, NULL, '2020-03-09 08:11:46', '2020-03-09 08:11:46', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (167, '0055_002', '物管员', '', '0055', 2, 1, 1, '', '2020-09-02 03:01:29', NULL, NULL, NULL, '2020-03-09 08:11:46', '2020-03-09 08:11:46', NULL, NULL, b'0');
INSERT INTO `dict_item` VALUES (168, '0050_002', '前摄像头2', 'front-2', '0050', 2, 1, 1, '', '2020-09-27 06:40:53', NULL, NULL, NULL, '2020-07-20 01:43:09', '2020-07-20 01:43:09', NULL, NULL, b'0');



-- ----------------------------
-- Records of dict_type
-- ----------------------------
INSERT INTO `dict_type` VALUES (1, '0001', '系统管理', '0', 1, 0, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (2, '0002', '基础信息管理', '0', 2, 0, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (3, '0003', '教学管理', '0', 3, 0, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (4, '0004', '考试管理', '0', 4, 0, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (5, '0005', '角色管理', '0001', 2, 1, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (6, '0006', '学校管理', '0002', 1, 1, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (7, '0007', '实验室管理', '0002', 2, 1, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (8, '0008', '班级管理', '0002', 3, 1, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (9, '0009', '教师管理', '0002', 4, 1, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (10, '0010', '学生管理', '0002', 5, 1, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (11, '0011', '办学性质', '0006', 1, 2, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (12, '0012', '学段', '0006', 2, 2, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (13, '0013', '批次', '0006', 3, 2, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (14, '0014', '年级', '0006', 4, 2, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (15, '0015', '实验室类型', '0007', 1, 2, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (16, '0016', '性别', '0001', 1, 1, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (17, '0017', '教师级别', '0006', 5, 2, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (18, '0018', '学科', '0006', 6, 2, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (19, '0019', '题目类型', '0004', 1, 2, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (20, '0020', '公开类型', '0003', 1, 1, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (21, '0021', '教材版本', '0003', 2, 1, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (22, '0022', '课件类型', '0003', 3, 1, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (23, '0023', '实验类型', '0007', 2, 2, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (24, '0024', '分桌方式', '0003', 4, 1, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (25, '0025', '实验台状态', '0007', 3, 2, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (26, '0026', '选项排列方式', '0003', 5, 1, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (27, '0027', '考题管理', '0004', 2, 1, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (28, '0028', '考试安排', '0004', 3, 1, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (29, '0029', '阅卷评分', '0004', 4, 1, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (30, '0030', '成绩查询', '0004', 4, 1, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (31, '0031', '考试类型', '0027', 1, 2, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (33, '0032', '组别', '0027', 2, 2, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (34, '0033', '考题状态', '0027', 3, 2, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (35, '0034', '分配方式', '0027', 4, 2, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (36, '0035', '考试状态', '0004', 1, 2, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (37, '0036', '实验状态', '0007', 99, 2, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (38, '0037', '课程安排', '0003', 6, 1, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (39, '0038', '课程时间', '0037', 1, 2, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (40, '0040', '评分标准', '0', 5, 0, 1, 1, '1_0', 'RD', '2020-02-17 20:28:54', '2020-02-17 20:28:54', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (44, '0043', '试题', '0040', 1, 1, 1, 1, '1_0', 'RD', '2020-03-17 01:20:04', '2020-03-17 01:20:04', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (45, '0044', '评分要求', '0040', 2, 1, 1, 1, '1_0', 'RD', '2020-03-17 01:20:40', '2020-03-17 01:20:40', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (46, '0046', '考生状态', '0004', 2, 2, 1, 1, '1_0', 'RD', '2020-03-23 07:59:12', '2020-03-23 07:59:12', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (47, '0047', '考生类别', '0004', 99, 2, 1, 1, '1_0', 'RD', '2020-04-30 15:02:07', '2020-04-30 15:02:07', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (48, '0048', '设备管理', '0', 6, 0, 1, 1, '1_0', 'RD', '2020-07-20 01:39:00', '2020-07-20 01:39:00', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (49, '0049', '设备类型', '0048', 1, 1, 1, 1, '1_0', 'RD', '2020-07-20 01:39:46', '2020-07-20 01:39:46', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (50, '0050', '设备子类型', '0048', 2, 1, 1, 1, '1_0', 'RD', '2020-07-20 01:39:59', '2020-07-20 01:39:59', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (51, '0051', '设备状态', '0048', 3, 1, 1, 1, '1_0', 'RD', '2020-07-20 03:05:58', '2020-07-20 03:05:58', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (52, '0052', '实验室使用类型', '0007', 4, 2, 1, 1, '1_0', 'RD', '2020-08-19 01:17:58', '2020-08-19 01:17:58', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (53, '0053', '课程状态', '0037', 2, 2, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (54, '0054', '教职工管理', '0002', 6, 1, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
INSERT INTO `dict_type` VALUES (56, '0055', '职务', '0011', 1, 2, 1, 1, '1_0', 'RD', '2020-02-17 10:22:52', '2020-02-17 10:22:52', NULL, NULL, b'0');
