CREATE SCHEMA IF NOT EXISTS `edu_edge` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `edu_edge`;

-- biz_experiment_process_task 实验结束后，后续处理任务
create table if not exists `biz_experiment_process_task`
(
    `id`                  bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `source_type`         varchar(20) NOT NULL DEFAULT '' COMMENT '来源类型: exam-考试，course-课程',
    `source_id`           bigint(20) NOT NULL COMMENT '来源ID: 考试或课程ID',
    `source_ext_tag`      varchar(20) NOT NULL DEFAULT '' COMMENT '来源扩展标签：考试时组别号，用于区分初试，补考；教学时填空',
    `is_teaching`    tinyint(1) NOT NULL DEFAULT 0  COMMENT '是否是老师教学，0-不是，1-是',
    `operator_no`         bigint(20) NOT NULL COMMENT '操作人编号: 准考证号/学籍号/用户名',
    `experiment_id`       bigint(20) NOT NULL COMMENT '考试或教学实验ID',
    `ai_experiment_id`    bigint(20) NOT NULL COMMENT '对应的算法评分实验ID',
    `sn_code`             varchar(48) NOT NULL DEFAULT '' COMMENT '实验台设备号',
    `status`              varchar(20) NOT NULL COMMENT '状态: running-处理中, success-处理成功, error-处理失败',
    `need_upload`   tinyint(1) NOT NULL DEFAULT 0  COMMENT '是否需要上传',
    `video_upload_status` varchar(20) NOT NULL DEFAULT 0  COMMENT '视频上传状态: waiting-等待处理，uploading-进行中, uploaded-已上传， submitted-已提交, error-异常',
    `need_merge_video`    tinyint(1) NOT NULL DEFAULT 0  COMMENT '是否需要视频合流',
    `merge_task_id`       bigint(20) NULL COMMENT '实验评分任务ID',
    `video_merge_status`  varchar(20) NULL COMMENT '视频合流状态: waiting-等待处理，merging-进行中, merged-已合流， uploading-已上传，uploaded-已上传，submitted-已提交, error-异常',
    `need_ai_score`       tinyint(1) NOT NULL DEFAULT 0  COMMENT '是否需要AI评分',
    `score_task_id`       bigint(20) NULL COMMENT '实验评分任务ID',
    `score_task_status`   varchar(20) NULL COMMENT '实验评分任务状态: waiting-等待处理，scoring-进行中, scored-已合流，submitted-已提交, error-异常',
    `req_json`            varchar(4000) NOT NULL DEFAULT '' COMMENT '请求json',
    `created_time`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
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
