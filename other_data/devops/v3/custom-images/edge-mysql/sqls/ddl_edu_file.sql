CREATE SCHEMA IF NOT EXISTS `edu_edge` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `edu_edge`;
-- ----------------------------
-- Table structure for upload_task
-- ----------------------------
CREATE TABLE IF NOT EXISTS `upload_task`  (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `file_path` varchar(200)  NOT NULL DEFAULT '' COMMENT '文件路径',
  `checksum` varchar(500)  NOT NULL DEFAULT '' COMMENT 'md5校验码',
  `public_url` varchar(1000)  NULL DEFAULT '' COMMENT '访问路径',
  `status` smallint(2) NOT NULL DEFAULT 0 COMMENT '任务状态',
  `error_code` varchar(30) NOT NULL DEFAULT '' COMMENT '错误码',
  `heartbeat` datetime NULL DEFAULT NULL COMMENT '心跳时间',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci
  COMMENT = '上传任务';

-- ----------------------------
-- Table structure for upload_task_log
-- ----------------------------
CREATE TABLE IF NOT EXISTS `upload_task_log`  (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `task_id` bigint(10) NOT NULL COMMENT '任务ID',
  `is_success` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否执行成功',
  `start_time` datetime NOT NULL COMMENT '执行开始时间',
  `end_time` datetime NOT NULL COMMENT '执行结束时间',
  `error_code` varchar(100) NOT NULL DEFAULT '' COMMENT '错误码',
  `error_msg` varchar(500) NOT NULL DEFAULT '' COMMENT '错误信息',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci
  COMMENT = '上传任务执行日志';


