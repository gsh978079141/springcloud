CREATE SCHEMA IF NOT EXISTS `edu_edge` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

grant all privileges on edu_edge.* to 'edu'@'%';


USE `edu_edge`;


--  算法调度表定义

-- ----------------------------
-- Table structure for compute_node
-- ----------------------------
create table if not exists `algo_compute_node`
(
    `id`                       bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `name`                     varchar(50)  NOT NULL UNIQUE COMMENT '节点名称',
    `is_online`                tinyint(1)   NOT NULL DEFAULT 0 COMMENT '在线状态 0-不在线 1-在线',
    `is_usable`                tinyint(1)   NOT NULL DEFAULT 0 COMMENT '可用状态 0-不可用 1-可用',
    `gpu_memory`               varchar(50)  NOT NULL DEFAULT '' COMMENT '节点显存',
    `scoring_experiment_codes` varchar(100) NOT NULL DEFAULT '' comment '正在评分的实验code',
    `heartbeat_time`           datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP comment '最近一次心跳时间',
    `created_time`             datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`             datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '计算节点信息';


-- ----------------------------
-- Table structure for score_task
-- ----------------------------

create table if not exists `algo_experiment_score_task`
(
    `id`              bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `src_task_id`     varchar(100)        NOT NULL UNIQUE COMMENT '来源系统任务id',
    `experiment_id`   bigint(20) unsigned NOT NULL COMMENT '实验id',
    `experiment_code` varchar(100)        NOT NULL DEFAULT '' COMMENT '实验code',
    `experiment_name` varchar(100)        NOT NULL DEFAULT '' COMMENT '实验名称',
    `node_id`         bigint(20)          NOT NULL DEFAULT 0 COMMENT '处理节点id',
    `status`          varchar(10)         NOT NULL COMMENT '任务状态：created-新建 scoring-评分中 error-评分失败 success-评分成功',
    `err_code`        varchar(40)         NOT NULL DEFAULT '' COMMENT '执行失败的错误码',
    `algo_task_json`  varchar(4000)       NOT NULL COMMENT '下发给算法节点的json格式消息',
    `execute_count`   int(5)              NOT NULL DEFAULT 0 COMMENT '任务执行次数',
    `created_time`    datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`    datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '实验评分任务';



CREATE TABLE `algo_task_execute_event`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `task_id`      bigint(20) unsigned NOT NULL COMMENT '实验id',
    `type`         varchar(20)         NOT NULL COMMENT '动作：asign: 任务分配, notify_process: 回调处理',
    `msg`          varchar(4000)       NOT NULL DEFAULT '' COMMENT '消息',
    `created_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 54538701
  DEFAULT CHARSET = utf8mb4 COMMENT ='任务执行事件'

-- ----------------------------
-- Table structure for score_point
-- ----------------------------

create table if not exists `algo_score_point_result`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `task_id`      bigint(20)          NOT NULL COMMENT '评分任务id',
    `operation_id` bigint(20)          NOT NULL COMMENT '操作id',
    `point_id`     bigint(20)          NOT NULL COMMENT '得分点id',
    `value`        decimal(10, 1)      NULL COMMENT '分值',
    `created_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '得分点评分结果';


-- ----------------------------
-- Table structure for algo_score_point_evidence
-- ----------------------------
create table if not exists `algo_score_point_evidence`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `task_id`      bigint(20)          NOT NULL COMMENT '评分任务id',
    `point_id`     bigint(20)          NOT NULL COMMENT '得分点id',
    `type`         varchar(20)         NOT NULL COMMENT '凭证类型：pic, video, text',
    `value`        varchar(200)        NULL COMMENT '凭证内容',
    `created_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    primary key (id),
    index idx_task_id (`task_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '得分点评分凭证';


-- ----------------------------
-- Table structure for algo_score_answer
-- ----------------------------
create table if not exists `algo_score_answer`
(
    `id`             bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `task_id`        bigint(20)          NOT NULL COMMENT '评分任务id',
    `question_no`    int(5)              NOT NULL COMMENT '题目编号',
    `option_no`      int(5)              NOT NULL COMMENT '选项编号',
    `score_point_id` bigint(20)          NULL COMMENT '评分点id',
    `answer`         varchar(100)        NULL COMMENT '答案',
    `created_time`   datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`   datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '实验评分答案';



-- ----------------------------
-- Table structure for algo_experiment_video
-- ----------------------------

create table if not exists `algo_score_video`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `task_id`      bigint(20)          NOT NULL COMMENT '评分任务id',
    `camera_id`    varchar(20)         NOT NULL COMMENT '摄像头id:  front-1, top-1, side-1,front-2',
    `url`          varchar(200)        NOT NULL COMMENT '视频url',
    `created_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '实验评分视频信息';


-- 通用业务处理表

-- biz_experiment_process_task 实验结束后，后续处理任务
create table if not exists `biz_experiment_process_task`
(
    `id`                  bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `source_type`         varchar(20)         NOT NULL DEFAULT '' COMMENT '来源类型: exam-考试，course-课程',
    `source_id`           bigint(20)          NOT NULL COMMENT '来源ID: 考试或课程ID',
    `source_ext_tag`      varchar(20)         NOT NULL DEFAULT '' COMMENT '来源扩展标签：考试时组别号，用于区分初试，补考；教学时填空',
    `is_teaching`         tinyint(1)          NOT NULL DEFAULT 0 COMMENT '是否是老师教学，0-不是，1-是',
    `operator_no`         bigint(20)          NOT NULL COMMENT '操作人编号: 准考证号/学籍号/用户名',
    `experiment_id`       bigint(20)          NOT NULL COMMENT '考试或教学实验ID',
    `ai_experiment_id`    bigint(20)          NOT NULL COMMENT '对应的算法评分实验ID',
    `sn_code`             varchar(48)         NOT NULL DEFAULT '' COMMENT '实验台设备号',
    `status`              varchar(20)         NOT NULL COMMENT '状态: running-处理中, success-处理成功, error-处理失败',
    `need_upload`         tinyint(1)          NOT NULL DEFAULT 0 COMMENT '是否需要上传',
    `video_upload_status` varchar(20)         NOT NULL DEFAULT 0 COMMENT '视频上传状态: waiting-等待处理，uploading-进行中, uploaded-已上传， submitted-已提交, error-异常',
    `need_merge_video`    tinyint(1)          NOT NULL DEFAULT 0 COMMENT '是否需要视频合流',
    `merge_task_id`       bigint(20)          NULL COMMENT '实验评分任务ID',
    `video_merge_status`  varchar(20)         NULL COMMENT '视频合流状态: waiting-等待处理，merging-进行中, merged-已合流， uploading-已上传，uploaded-已上传，submitted-已提交, error-异常',
    `need_ai_score`       tinyint(1)          NOT NULL DEFAULT 0 COMMENT '是否需要AI评分',
    `score_task_id`       bigint(20)          NULL COMMENT '实验评分任务ID',
    `score_task_status`   varchar(20)         NULL COMMENT '实验评分任务状态: waiting-等待处理，scoring-进行中, scored-已合流，submitted-已提交, error-异常',
    `req_json`            varchar(4000)       NOT NULL DEFAULT '' COMMENT '请求json',
    `created_time`        datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`        datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '实验处理任务';


-- 实验视频信息表，包括实验的原始多角度mp4视频，以及合流后的m3u8视频
create table if not exists `biz_experiment_video`
(
    `id`                 bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `task_id`            bigint(20) unsigned NOT NULL COMMENT '任务ID',
    `type`               varchar(10) NOT NULL COMMENT '视频类型：original-原始视频，merged_m3u8-合流后文件',
    `camera_location`    varchar(20) NOT NULL DEFAULT '' COMMENT '摄像头位置，如果是原始视频 top1, side1, front1, front2',
    `file_path`          varchar(100) NOT NULL COMMENT '文件地址',
    `private_url`        varchar(100) NOT NULL COMMENT '内网访问的url',
    `public_url`         varchar(100) NOT NULL COMMENT '外部访问的url',
    `upload_task_id`     bigint(20)  unsigned NOT NULL DEFAULT 0  COMMENT '上传任务ID',
    `upload_task_status` varchar(20)   NOT NULL COMMENT '上传任务状态: uploading-进行中，success-已提交, error-异常',
    `created_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    index idx_task_id (`task_id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '实验视频信息';


-- 实验评分结果，对应每个实验评分的详细得分点记录
create table if not exists `biz_experiment_score_point_result`
(
    `id`                bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `task_id`           bigint(20) unsigned NOT NULL COMMENT '任务ID',
    `operation_id`      bigint(20) NOT NULL COMMENT '操作id',
    `point_id`          bigint(20) NOT NULL COMMENT '得分点id',
    `value`             decimal(10, 1) NULL COMMENT '分值',
    `created_time`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    index idx_task_id (`task_id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '实验得分点评分结果';



-- ----------------------------
-- Table structure for algo_score_point_evidence
-- ----------------------------
create table if not exists `biz_experiment_score_evidence`
(
    `id`                  bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `task_id`             bigint(20) NOT NULL COMMENT '评分任务id',
    `point_id`            bigint(20) NOT NULL COMMENT '得分点id',
    `type`                varchar(20) NOT NULL COMMENT '凭证类型：pic, video, text',
    `value`               varchar(200) NULL COMMENT '凭证内容',
    `file_path`           varchar(100) NOT NULL COMMENT '文件地址',
    `public_url`          varchar(100) NOT NULL COMMENT '外部访问的url',
    `upload_task_id`      bigint(20)  unsigned NOT NULL DEFAULT 0  COMMENT '上传任务ID',
    `upload_task_status`  varchar(20) NOT NULL COMMENT '上传任务状态: uploading-进行中，success-已提交, error-异常',
    `created_time`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    primary key (id),
    index idx_task_id (`task_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '得分点评分凭证';

-- 文件管理表


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

-- 流媒体录制表

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

-- 视频处理表


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
    PRIMARY KEY (`id`),
    INDEX idx_task_id(`task_id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8 COLLATE = utf8_general_ci;

