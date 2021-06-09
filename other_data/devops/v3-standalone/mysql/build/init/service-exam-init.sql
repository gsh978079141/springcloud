/* 数据库全名 = edu_exam
 Date: 19/08/2020 10:57:24
*/
CREATE SCHEMA IF NOT EXISTS `edu_exam` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
grant all privileges on edu_exam.* to 'edu'@'%';


USE `edu_exam`;

-- ----------------------------
-- 表名称 =  base_question
-- ----------------------------
CREATE TABLE `base_question`
(
    `id`             bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `title`          bigint(20)   NOT NULL COMMENT '标题(长文本ID)',
    `question_type`  varchar(11)  NOT NULL COMMENT '题目类型',
    `sort_num`       int(11)      NULL     DEFAULT NULL COMMENT '排序标识',
    `option_arrange` varchar(11)  NOT NULL COMMENT '选项排列方式(代码)',
    `created_time`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`     varchar(36)  NULL     DEFAULT NULL COMMENT '创建者',
    `updated_by`     varchar(36)  NULL     DEFAULT NULL COMMENT '更新者',
    `deleted`        tinyint(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '题目表';

-- ----------------------------
-- 表名称 =  exam
-- ----------------------------
CREATE TABLE `exam`
(
    `id`                      bigint(20)  NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`                    varchar(50) NOT NULL COMMENT '考试名称(唯一)',
    `exam_batch`              varchar(25) NOT NULL DEFAULT '' COMMENT '考试批次',
    `school_id`               bigint(20)  NOT NULL COMMENT '考点学校',
    `exam_type`               varchar(11) NOT NULL COMMENT '考试类型(代码)',
    `stage`                   varchar(11) NOT NULL DEFAULT '' COMMENT '学段',
    `subject`                 varchar(11) NOT NULL COMMENT '考试学科(代码)',
    `grade`                   varchar(11) NOT NULL COMMENT '年级',
    `grade_review`            tinyint(1)  NOT NULL DEFAULT 1 COMMENT '是否成绩复核',
    `review_teacher_id`       bigint(20)  NULL     DEFAULT NULL COMMENT '复核老师ID',
    `explanation`             bigint(20)  NULL     DEFAULT NULL COMMENT '说明',
    `before_exam_notice`      bigint(20)  NULL     DEFAULT NULL COMMENT '考前须知(长文本ID)',
    `examinee_notice`         bigint(20)  NULL     DEFAULT NULL COMMENT '考生须知(长文本ID)',
    `duration`                int(3)      NULL     DEFAULT 15 COMMENT '考试时长',
    `creator_id`              bigint(20)  NULL     DEFAULT NULL COMMENT '创建人ID',
    `exam_status`             varchar(11) NOT NULL DEFAULT '' COMMENT '考试状态(代码)',
    `exam_times_num`          int(5)      NULL     DEFAULT NULL COMMENT '考场数',
    `question_distribute_way` varchar(11) NULL     DEFAULT NULL COMMENT '考题分配方式',
    `examinee_distribute_way` varchar(11) NULL     DEFAULT NULL COMMENT '考生分配方式',
    `exam_date`               datetime    NULL     DEFAULT NULL COMMENT '考试日期',
    `start_time`              datetime    NULL     DEFAULT NULL COMMENT '考试开始时间',
    `end_time`                datetime    NULL     DEFAULT NULL COMMENT '考试结束时间',
    `question_snapshot`       varchar(11) NULL     DEFAULT NULL COMMENT '考题快照(Json格式存入长文本表)',
    `created_time`            datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`            datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`              varchar(36) NULL     DEFAULT NULL COMMENT '创建者',
    `updated_by`              varchar(36) NULL     DEFAULT NULL COMMENT '更新者',
    `deleted`                 tinyint(1)  NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '考试表';

-- ----------------------------
-- 表名称 =  exam_times
-- ----------------------------
CREATE TABLE `exam_times`
(
    `id`                          bigint(20)          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `exam_id`                     bigint(20)          NOT NULL COMMENT '考试ID',
    `experiment_room_id`          bigint(20)          NOT NULL COMMENT '实验室ID',
    `experiment_room_schedule_id` bigint(20) UNSIGNED NULL     DEFAULT NULL COMMENT '实验室日程ID',
    `times`                       int(2)              NOT NULL DEFAULT 1 COMMENT '场次',
    `due_examinee_number`         int(2)              NULL     DEFAULT 0 COMMENT '应到考生人数',
    `actual_examinee_number`      int(2)              NULL     DEFAULT 0 COMMENT '实到考生人数',
    `exam_date`                   datetime        NULL     DEFAULT NULL COMMENT '考试日期',
    `start_time`                  datetime        NULL     DEFAULT NULL COMMENT '开始时间',
    `end_time`                    datetime        NULL     DEFAULT NULL COMMENT '结束时间',
    `created_time`                datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`                datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`                  varchar(36)         NULL     DEFAULT NULL COMMENT '创建者',
    `updated_by`                  varchar(36)         NULL     DEFAULT NULL COMMENT '更新者',
    `deleted`                     tinyint(1)              NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `version`                     bigint(11) UNSIGNED NULL     DEFAULT 1 COMMENT '版本号',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '考试场次表';

-- ----------------------------
-- 表名称 =  examinee
-- ----------------------------
CREATE TABLE `examinee`
(
    `id`                bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `exam_id`           bigint(20)   NOT NULL COMMENT '考试ID',
    `exam_time_id`      bigint(20)   NOT NULL COMMENT '考场ID',
    `student_id`        bigint(20)   NOT NULL COMMENT '学生ID',
    `exam_number`       varchar(25)  NOT NULL COMMENT '准考证号',
    `seat_number`       int(5)       NULL     DEFAULT NULL COMMENT '座位号',
    `question_group_id` int(11)      NULL     DEFAULT NULL COMMENT '考题组别ID',
    `status`            varchar(20)  NULL     DEFAULT NULL COMMENT '考生状态',
    `type`              varchar(20)  NULL     DEFAULT NULL COMMENT '类别(初试/补考)',
    `remark`            varchar(50)  NULL     DEFAULT NULL COMMENT '备注',
    `created_time`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`        varchar(36)  NULL     DEFAULT NULL COMMENT '创建者',
    `updated_by`        varchar(36)  NULL     DEFAULT NULL COMMENT '更新者',
    `deleted`           tinyint(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '考生表';

-- ----------------------------
-- 表名称 =  examinee_grade
-- ----------------------------
CREATE TABLE `examinee_grade`
(
   `id`                  bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `exam_id`              bigint(20) unsigned NOT NULL COMMENT '考试ID',
  `examinee_id`          bigint(20) unsigned NOT NULL COMMENT '考生ID',
  `ai_total_score`       decimal(10,1) unsigned DEFAULT '0.0' COMMENT '算法打分总分',
  `total_score`          decimal(10,1) unsigned NOT NULL DEFAULT '0.0' COMMENT '总分',
  `review_teacher_id`    bigint(20) unsigned DEFAULT NULL COMMENT '复核老师ID',
  `review_time`          datetime NULL DEFAULT NULL COMMENT '复核时间',
  `score_teacher_id`     bigint(20) unsigned DEFAULT NULL COMMENT '评分教师ID',
  `score_time`           datetime DEFAULT NULL COMMENT '评分时间',
  `created_time`         datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time`         datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_by`           varchar(36) DEFAULT NULL COMMENT '创建者',
  `updated_by`           varchar(36) DEFAULT NULL COMMENT '更新者',
  `is_deleted`           tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
   PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '考生_成绩表(考试答案与情况分析)';

-- ----------------------------
-- 表名称 =  examinee_grade_experiment
-- ----------------------------
CREATE TABLE `examinee_experiment_grade`
(
   `id`                 bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `examinee_id`         bigint(20) unsigned NOT NULL COMMENT '考生ID',
  `experiment_id`       bigint(20) unsigned NOT NULL COMMENT '实验ID',
  `ai_total_score`      decimal(10,1) unsigned DEFAULT '0.0' COMMENT '算法打分总分',
  `total_score`         decimal(10,1) unsigned DEFAULT '0.0' COMMENT '总分',
  `created_time`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_by`          varchar(36) DEFAULT NULL COMMENT '创建者',
  `updated_by`          varchar(36) DEFAULT NULL COMMENT '更新者',
  `is_deleted`          tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '考生成绩实验表';

-- ----------------------------
-- 表名称 =  examinee_grade_experiment
-- ----------------------------
CREATE TABLE `examinee_experiment_score` (
  `id`                  bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `examinee_id`         bigint(20) unsigned NOT NULL COMMENT '考生ID',
  `experiment_id`       bigint(20) unsigned NOT NULL COMMENT '实验ID',
  `score_point_id`      bigint(20) unsigned NOT NULL COMMENT '评分点ID',
  `teacher_score`       decimal(5,1) unsigned DEFAULT '0.0' COMMENT '教师打分',
  `ai_score`            decimal(5,1) unsigned DEFAULT '0.0' COMMENT '算法打分',
  `remark`              varchar(50) DEFAULT '' COMMENT '说明',
  `created_time`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_by`          varchar(36) DEFAULT '' COMMENT '创建者',
  `updated_by`          varchar(36) DEFAULT '' COMMENT '更新者',
  `is_deleted`          tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
)  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '考生实验评分表';

-- ----------------------------
-- 表名称 =  examinee_experiment_score_evidence
-- ----------------------------
CREATE TABLE `examinee_experiment_score_evidence` (
  `id`                  bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `examinee_id`         bigint(20) unsigned NOT NULL COMMENT '考生ID',
  `experiment_id`       bigint(20) unsigned NOT NULL COMMENT '实验ID',
  `score_point_id`      bigint(20) unsigned NOT NULL COMMENT '评分点ID',
  `type`                varchar(50) DEFAULT '' COMMENT '类型: picture,text,video',
  `value`               varchar(200) DEFAULT '' COMMENT '凭证',
  `created_time`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_by`          varchar(36) DEFAULT NULL COMMENT '创建者',
  `updated_by`          varchar(36) DEFAULT NULL COMMENT '更新者',
  `is_deleted`          tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '考生实验评分凭证表';

-- ----------------------------
-- 表名称 =  examinee_experiment_video
-- ----------------------------
CREATE TABLE `examinee_experiment_video` (
  `id`                  bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `examinee_id`         bigint(20) unsigned NOT NULL COMMENT '考生ID',
  `experiment_id`       bigint(20) unsigned NOT NULL COMMENT '实验ID',
  `video_id`            varchar(20) NOT NULL COMMENT '视频标识: top1, side1, front1, front2, merge',
  `video_type`          varchar(20) NOT NULL COMMENT '视频类型: mp4, m3u8',
  `public_url`          varchar(200) NOT NULL DEFAULT '' COMMENT '视频url',
  `status`              varchar(10)  NOT NULL COMMENT '视频状态: not_ready, upload_fail，uploaded',
  `created_time`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_by`          varchar(36) DEFAULT NULL COMMENT '创建者',
  `updated_by`          varchar(36) DEFAULT NULL COMMENT '更新者',
  `is_deleted`          tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '考生实验视频表';

-- ----------------------------
-- 表名称 =  examinee_grade_modify
-- ----------------------------
CREATE TABLE `examinee_grade_modify`
(
    `id`                 bigint(20)    NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `examinee_id`        bigint(20)    NOT NULL COMMENT '考生ID',
    `experiment_id`      bigint(20)    NOT NULL COMMENT '实验ID',
    `score_id`           bigint(20)    NULL     DEFAULT NULL COMMENT '评分ID',
    `modify_teacher_id`  bigint(20)    NULL     DEFAULT NULL COMMENT '修改教师ID',
    `before_grade_value` decimal(5, 1) NULL     DEFAULT NULL COMMENT '修改前分数',
    `after_grade_value`  decimal(5, 1) NULL     DEFAULT NULL COMMENT '修改后分数',
    `remark`             varchar(50)   NULL     DEFAULT NULL COMMENT '备注',
    `modify_time`        datetime  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `created_time`       datetime  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`       datetime  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`         varchar(36)   NULL     DEFAULT NULL COMMENT '创建者',
    `updated_by`         varchar(36)   NULL     DEFAULT NULL COMMENT '更新者',
    `deleted`            tinyint(1)        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- ----------------------------
-- 表名称 =  examinee_report_record
-- ----------------------------
CREATE TABLE `examinee_report_record`
(
    `id`               bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `examinee_id`      bigint(20)   NOT NULL COMMENT '考生ID',
    `base_question_id` bigint(20)   NOT NULL COMMENT '基础考题ID',
    `option_id`        bigint(20)   NULL     DEFAULT NULL COMMENT '选项ID',
    `answer`           varchar(200) NULL     DEFAULT NULL COMMENT '答案',
    `created_time`     datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`     datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `updated_by`       varchar(36)  NULL     DEFAULT NULL COMMENT '更新者',
    `created_by`       varchar(36)  NULL     DEFAULT NULL COMMENT '创建者',
    `deleted`          tinyint(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `sort_num`         int(11)      NOT NULL COMMENT '排序/填空位',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- ----------------------------
-- 表名称 =  experiment
-- ----------------------------
CREATE TABLE `experiment`
(
    `id`                            bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`                          varchar(20)  NOT NULL COMMENT '实验名称',
    `question_group_id`             bigint(20)   NOT NULL COMMENT '考题组别ID',
    `experiment_material_trim_desc` bigint(20)   NULL     DEFAULT NULL COMMENT '实验器材准备说明(长文本ID)',
    `exam_content`                  bigint(20)   NULL     DEFAULT NULL COMMENT '考试内容(长文本ID)',
    `ai_score`                      tinyint(1)       NOT NULL COMMENT '是否算法打分',
    `ai_experiment_id`              bigint(11)   NULL     DEFAULT NULL COMMENT '算法打分实验ID',
    `created_time`                  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`                  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`                    varchar(36)  NULL     DEFAULT NULL COMMENT '创建者',
    `updated_by`                    varchar(36)  NULL     DEFAULT NULL COMMENT '更新者',
    `deleted`                       tinyint(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '考题_组别表';

-- ----------------------------
-- 表名称 =  experiment_report
-- ----------------------------
CREATE TABLE `experiment_report`
(
    `id`               bigint(20)   NOT NULL AUTO_INCREMENT,
    `experiment_id`    bigint(20)   NOT NULL COMMENT '考题组信息ID',
    `base_question_id` bigint(20)   NOT NULL COMMENT '基础题目ID',
    `created_time`     datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`     datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`       varchar(36)  NULL     DEFAULT NULL COMMENT '创建者',
    `updated_by`       varchar(36)  NULL     DEFAULT NULL COMMENT '更新者',
    `deleted`          tinyint(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '实验报告表(考试组别对应的实验报告)';

-- ----------------------------
-- 表名称 =  long_text
-- ----------------------------
CREATE TABLE `long_text`
(
    `id`           bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `title`        varchar(50)  NULL     DEFAULT '' COMMENT '标题',
    `sort_num`     int(2)       NULL     DEFAULT 0 COMMENT '排序标识',
    `content`      longtext     NULL COMMENT '内容',
    `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`   varchar(36)  NULL     DEFAULT NULL COMMENT '创建者',
    `updated_by`   varchar(36)  NULL     DEFAULT NULL COMMENT '更新者',
    `deleted`      tinyint(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '文本信息表';

-- ----------------------------
-- 表名称 =  option
-- ----------------------------
CREATE TABLE `option`
(
    `id`               bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `base_question_id` bigint(20)   NOT NULL COMMENT '基础题目ID',
    `name`             varchar(50)  NOT NULL COMMENT '名称',
    `sort_num`         int(11)      NOT NULL COMMENT '排序',
    `correct_answer`   tinyint(1)   NULL     DEFAULT 0 COMMENT '是否正确答案(1是0否)',
    `created_time`     datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`     datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`       varchar(36)  NULL     DEFAULT NULL COMMENT '创建者',
    `updated_by`       varchar(36)  NULL     DEFAULT NULL COMMENT '更新者',
    `deleted`          tinyint(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '选项表';

-- ----------------------------
-- 表名称 =  p_exam_experiment_room
-- ----------------------------
CREATE TABLE `p_exam_experiment_room`
(
    `id`                      bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `exam_id`                 bigint(20)   NOT NULL COMMENT '考试ID',
    `experiment_room_id`      bigint(20)   NOT NULL COMMENT '实验室ID',
    `created_time`            datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`            datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`              varchar(36)  NULL     DEFAULT NULL COMMENT '创建者',
    `updated_by`              varchar(36)  NULL     DEFAULT NULL COMMENT '更新者',
    `deleted`                 tinyint(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `question_distribute_way` varchar(11)  NULL     DEFAULT NULL COMMENT '考题分配方式',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '考试_实验室关系表';


-- ----------------------------
-- 表名称 =  exam_invigilator
-- ----------------------------
CREATE TABLE `exam_invigilator` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `exam_id` bigint(20) NOT NULL COMMENT '考试ID',
  `exam_time_id` bigint(20) NOT NULL COMMENT '考试场次ID',
  `invigilator_id` bigint(20) NOT NULL COMMENT '监考老师ID',
  `invigilator_name` varchar(50) NOT NULL COMMENT '监考老师姓名',
  `invigilator_mobile` varchar(11) NOT NULL COMMENT '监考老师手机号',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_by` varchar(36) DEFAULT NULL COMMENT '创建者',
  `updated_by` varchar(36) DEFAULT NULL COMMENT '更新者',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_exam_id_exam_time_id` (`exam_id`,`exam_time_id`,`is_deleted`)
)
ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '考试-监考老师信息表';

-- ----------------------------
-- 表名称 =  p_exam_invigilator
-- ----------------------------
CREATE TABLE `p_exam_invigilator`
(
    `id`             bigint(20)          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `exam_id`        bigint(20)          NOT NULL COMMENT '考试ID',
    `exam_time_id`   bigint(20)          NOT NULL COMMENT '考试场次ID',
    `invigilator_id` bigint(20)          NOT NULL COMMENT '监考老师ID',
    `created_time`   datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`   datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`     varchar(36)         NULL     DEFAULT NULL COMMENT '创建者',
    `updated_by`     varchar(36)         NULL     DEFAULT NULL COMMENT '更新者',
    `is_deleted`     tinyint(1) unsigned NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`),
    INDEX `idx_exam_id` (`exam_id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '考试-监考老师表';

-- ----------------------------
-- 表名称 =  p_examinee_question
-- ----------------------------
CREATE TABLE `p_examinee_question`
(
    `id`                bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `table_question_id` bigint(20)   NOT NULL COMMENT '实验台与考题关系表ID',
    `exam_time_id`      bigint(20)   NOT NULL COMMENT '考试场次ID',
    `examinee_id`       bigint(20)   NOT NULL COMMENT '考生ID',
    `created_time`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`        varchar(36)  NULL     DEFAULT NULL COMMENT '创建者',
    `updated_by`        varchar(36)  NULL     DEFAULT NULL COMMENT '更新者',
    `deleted`           tinyint(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '考生_试题关系表';

-- ----------------------------
-- 表名称 =  p_experiment_table_exam_question
-- ----------------------------
CREATE TABLE `p_experiment_table_exam_question`
(
    `id`                  bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `exam_id`             bigint(20)   NOT NULL COMMENT '考试ID',
    `experiment_room_id`  bigint(20)   NOT NULL COMMENT '实验室ID',
    `sn_code`             varchar(20)  NOT NULL DEFAULT '' COMMENT '实验台SNCode',
    `experiment_table_id` bigint(20)   NOT NULL COMMENT '实验台ID',
    `question_group_id`   bigint(20)   NOT NULL COMMENT '考题ID',
    `created_time`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`          varchar(36)  NULL     DEFAULT NULL COMMENT '创建者',
    `updated_by`          varchar(36)  NULL     DEFAULT NULL COMMENT '更新者',
    `deleted`             tinyint(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '实验台_考题关系表(考题分配)';

-- ----------------------------
-- 表名称 =  question
-- ----------------------------
CREATE TABLE `question`
(
    `id`                bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`              varchar(50)  NOT NULL COMMENT '考题名称',
    `school_id`         bigint(20)   NOT NULL COMMENT '考点学校',
    `subject`           varchar(11)  NOT NULL COMMENT '学科',
    `stage`             varchar(11)  NOT NULL COMMENT '学段',
    `grade`             varchar(11)  NOT NULL COMMENT '年级',
    `exam_type`         varchar(11)  NOT NULL COMMENT '考试类型',
    `review`            tinyint(1)   NOT NULL DEFAULT 1 COMMENT '是否审核',
    `review_teacher_id` bigint(20)   NULL     DEFAULT NULL COMMENT '审核人',
    `review_time`       datetime NULL     DEFAULT NULL COMMENT '审核时间',
    `upload_teacher_id` bigint(20)   NOT NULL COMMENT '上传老师',
    `upload_time`       datetime NULL     DEFAULT NULL COMMENT '上传时间',
    `status`            varchar(11)  NOT NULL COMMENT '考题状态(代码)',
    `message`           varchar(50)  NULL     DEFAULT NULL COMMENT '信息',
    `explanation`       varchar(300) NULL     DEFAULT NULL COMMENT '说明',
    `created_time`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`        varchar(36)  NULL     DEFAULT NULL COMMENT '创建者',
    `updated_by`        varchar(36)  NULL     DEFAULT NULL COMMENT '更新者',
    `deleted`           tinyint(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '考题_基础信息表';

-- ----------------------------
-- 表名称 =  question_group
-- ----------------------------
CREATE TABLE `question_group`
(
    `id`           bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `question_id`  bigint(20)   NOT NULL COMMENT '考题ID',
    `code`         varchar(11)  NOT NULL COMMENT '组别名称(代码)',
    `name`         varchar(11)  NULL     DEFAULT NULL COMMENT '组别名称',
    `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`   varchar(36)  NULL     DEFAULT NULL COMMENT '创建者',
    `updated_by`   varchar(36)  NULL     DEFAULT NULL COMMENT '更新者',
    `deleted`      tinyint(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '考题_组别表';

-- ----------------------------
-- 表名称 =  score
-- ----------------------------
CREATE TABLE `score`
(
    `id`            bigint(20)    NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `experiment_id` bigint(20)    NULL     DEFAULT NULL COMMENT '实验ID',
    `operation`     varchar(50)   NOT NULL COMMENT '试题',
    `score_point`   varchar(50)   NOT NULL COMMENT '评分要求',
    `grade_value`   decimal(5, 1) NOT NULL COMMENT '分值',
    `created_time`  datetime  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`  datetime  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `updated_by`    varchar(36)   NULL     DEFAULT NULL COMMENT '更新者',
    `created_by`    varchar(36)   NULL     DEFAULT NULL COMMENT '创建者',
    `deleted`       tinyint(1)        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci;

