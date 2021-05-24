CREATE SCHEMA IF NOT EXISTS `edu_edge` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `edu_edge`;


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


create table if not exists `algo_task_execute_log`
(
    `id`               bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `task_id`          bigint(20) unsigned NOT NULL COMMENT '实验id',
    `node_id`          bigint(20)          NOT NULL DEFAULT 0 COMMENT '处理节点id',
    `status`           varchar(10)         NOT NULL COMMENT '任务状态：scoring-评分中, error-评分失败, success-评分成功',
    `err_code`         varchar(40)         NOT NULL COMMENT '失败的异常码',
    `err_msg`          varchar(1000)       NOT NULL DEFAULT '' COMMENT '执行失败的错误消息',
    `assign_time`      datetime            NOT NULL COMMENT '分配时间',
    `finish_time`      datetime            NULL COMMENT '完成时间',
    `algo_result_json` varchar(4000)       NOT NULL comment '下发给算法节点的json格式消息',
    `created_time`     datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`     datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '任务执行日志';


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
