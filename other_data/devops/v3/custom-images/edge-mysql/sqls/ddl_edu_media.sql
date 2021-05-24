CREATE SCHEMA IF NOT EXISTS `edu_edge` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `edu_edge`;

-- Table: -----------------------------
CREATE TABLE IF NOT EXISTS `video_record_task`
(
    `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `recording_id` VARCHAR(50)     NOT NULL COMMENT '录制id, 调用方提供',
    `type`         VARCHAR(10)     NOT NULL COMMENT '录制任务类型: exam, teach, material',
    `status`       VARCHAR(20)     NOT NULL COMMENT '录制任务状态: connecting, recording, finished, auto_finished, connect_failed, aborting',
    `start_time`   DATETIME(3)              DEFAULT NULL COMMENT '启动录制时间',
    `end_time`     DATETIME(3)              DEFAULT NULL COMMENT '结束录制时间',
    `dir_path`     VARCHAR(50)     NOT NULL COMMENT '视频存放目录',
    `max_seconds`  INT UNSIGNED    NOT NULL COMMENT '任务录制的最大秒数（超时未收到结束指令，则自动结束）',
    `created_time` DATETIME(3)     NOT NULL DEFAULT NOW(3) COMMENT '创建时间',
    `updated_time` DATETIME(3)     NOT NULL DEFAULT NOW(3) COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '视频录制任务';


CREATE TABLE IF NOT EXISTS `video_record_item`
(
    `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `task_id`      BIGINT UNSIGNED NOT NULL COMMENT '录制任务id',
    `camera_id`    VARCHAR(40)     NOT NULL COMMENT '摄像头id',
    `stream_url`   VARCHAR(100)    NOT NULL COMMENT '视频流地址',
    `file_path`    VARCHAR(200)    NOT NULL COMMENT '文件存储地址',
    `access_url`   VARCHAR(200)    NOT NULL COMMENT '内网访问URL',
    `status`       VARCHAR(20)     NOT NULL COMMENT '录制状态：recording, finished, auto_finished, aborting',
    `start_time`   DATETIME(3)              DEFAULT NULL COMMENT '录制开始时间',
    `end_time`     DATETIME(3)              DEFAULT NULL COMMENT '录制结束时间',
    `err_msg`      VARCHAR(100)    NOT NULL DEFAULT '' COMMENT '异常信息',
    `created_time` DATETIME(3)     NOT NULL DEFAULT NOW(3) COMMENT '创建时间',
    `updated_time` DATETIME(3)     NOT NULL DEFAULT NOW(3) COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '视频录制明细项';