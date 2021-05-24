CREATE SCHEMA IF NOT EXISTS `edu_edge` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `edu_edge`;
-- ----------------------------
-- Table structure for video_process_task
-- ----------------------------
CREATE TABLE IF NOT EXISTS `video_process_task`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `task_id` varchar(32)  NOT NULL COMMENT '提交的taskId',
    `status` varchar(32)  NULL DEFAULT NULL COMMENT '任务处理状态 0:等待处理,1:正在合并视频,2:合并视频成功,3:m3u8视频转换中,4:m3u8转换成功,5:上传中,6:任务完成,7:任务失败',
    `error_message` varchar(1024)  NULL DEFAULT NULL COMMENT '错误消息',
    `merged_file` varchar(1024)  NULL DEFAULT NULL COMMENT '合并之后的Mp4文件',
    `m3u8url` varchar(1024)  NULL DEFAULT NULL COMMENT 'm3u8访问路径',
    `local_m3u8_path` varchar(1024)  NULL DEFAULT NULL COMMENT '本地包含 m3u8视频片段的路径',
    `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `merge_begin` datetime NULL DEFAULT NULL COMMENT '视频合并开始时间',
    `merge_end` datetime NULL DEFAULT NULL COMMENT '视频合并结束时间',
    `m3u8_ts_begin` datetime NULL DEFAULT NULL COMMENT '分割视频开始时间',
    `m3u8_ts_end` datetime NULL DEFAULT NULL COMMENT '分割视频结束时间',
    `upload_begin` datetime NULL DEFAULT NULL COMMENT '上传开始时间',
    `upload_end` datetime NULL DEFAULT NULL COMMENT '上传结束时间',
    `heartbeat` datetime NULL DEFAULT NULL COMMENT '最近一次心跳时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Table structure for video_task_detail
-- ----------------------------
CREATE TABLE  IF NOT EXISTS `video_task_detail`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `task_id` varchar(32)  NOT NULL COMMENT '所属taskId',
    `file` varchar(255)  NULL DEFAULT NULL COMMENT '磁盘文件路径',
    `file_order` tinyint(255) NULL DEFAULT NULL COMMENT '文件拼接位置 1：左上角 2：右上角 3：左下角 4：右下角',
    `start_time` datetime NULL DEFAULT NULL COMMENT '摄像头录制时的起始时间戳',
    `end_time` datetime NULL DEFAULT NULL COMMENT '摄像头录制时的结束时间戳',
    `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8 COLLATE = utf8_general_ci;

create index video_task_index on video_process_task (task_id);