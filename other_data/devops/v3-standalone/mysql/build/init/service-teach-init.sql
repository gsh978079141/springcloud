/*
 数据库全名 = edu_teach
 Date: 18/08/2020 14:29:43
*/
CREATE SCHEMA IF NOT EXISTS `edu_teach` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
grant all privileges on edu_teach.* to 'edu'@'%';
USE `edu_teach`;


-- ----------------------------
-- courseware 课件表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `edu_teach`.`courseware`
(
    `id`                bigint(20) unsigned    NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `school_id`         bigint(20) unsigned    NOT NULL COMMENT '学校id',
    `name`              varchar(50)            NOT NULL COMMENT '课件名称',
    `type`              char(11)               NOT NULL COMMENT '课件类型',
    `subject`           char(11)               NOT NULL COMMENT '学科',
    `stage`             char(11)               NOT NULL COMMENT '学段',
    `grade`             char(11)               NOT NULL COMMENT '年级',
    `size`              decimal(5, 2) unsigned NOT NULL COMMENT '课件大小/MB',
    `file_path`         varchar(200)           NULL     DEFAULT '' COMMENT '课件在服务器保存路径',
    `file_url`          varchar(200)           NOT NULL COMMENT '课件远程访问URL',
    `open_type`         char(11)               NOT NULL COMMENT '公开类型',
    `downloaded_times`  int(5) unsigned                 DEFAULT '0' COMMENT '下载次数',
    `is_allow_download` tinyint(1) unsigned             DEFAULT '0' COMMENT '是否允许下载',
    `creator_id`        bigint(20) unsigned    NOT NULL COMMENT '发布者教职工id',
    `created_time`      datetime(0)            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`      datetime(0)            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`        varchar(36)                     DEFAULT NULL COMMENT '创建者',
    `updated_by`        varchar(36)                     DEFAULT NULL COMMENT '更新者',
    `is_deleted`        tinyint(1) unsigned    NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='课件表';

-- ----------------------------
-- 表名称 =  course
-- ----------------------------
CREATE TABLE IF NOT EXISTS `edu_teach`.`course`
(
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `school_id` bigint(20) unsigned NOT NULL COMMENT '学校id',
  `name` varchar(50) NOT NULL COMMENT '课程名称',
  `experiment_room_id` bigint(20) unsigned NOT NULL COMMENT '实验室ID',
  `experiment_room_schedule_id` bigint(20) unsigned NOT NULL COMMENT '实验室日程ID',
  `class_id` bigint(20) unsigned NOT NULL COMMENT '授课班级',
  `subject` varchar(11) NOT NULL COMMENT '学科',
  `stage` char(11) NOT NULL COMMENT '学段',
  `grade` char(11) NOT NULL COMMENT '年级',
  `teacher_id` bigint(20) unsigned NOT NULL COMMENT '授课教师',
  `creator_id` bigint(20) unsigned NOT NULL COMMENT '创建人ID',
  `school_timetable_id` bigint(20) unsigned NOT NULL COMMENT '学校作息时间id',
  `lesson_no` smallint(5) NOT NULL COMMENT '节次编号',
  `start_time` datetime NOT NULL COMMENT '课程开始时间',
  `end_time` datetime NOT NULL COMMENT '课程结束时间',
  `course_date` datetime NOT NULL COMMENT '课程日期',
  `status` varchar(11) NOT NULL DEFAULT '' COMMENT '课程状态',
  `explanation` varchar(300) NOT NULL DEFAULT '' COMMENT '说明',
  `row_table_count` int(3) unsigned DEFAULT '0' COMMENT '每行实验台数量',
  `total_table_count` int(3) unsigned DEFAULT '0' COMMENT '实验台总数量',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_by` varchar(36) NOT NULL DEFAULT '' COMMENT '创建者',
  `updated_by` varchar(36) NOT NULL DEFAULT '' COMMENT '更新者',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='课程表';


-- ----------------------------
-- p_course_courseware 课程-课件关系表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `edu_teach`.`p_course_courseware`
(
    `id`            bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `course_id`     bigint(20) UNSIGNED NOT NULL COMMENT '课程ID',
    `courseware_id` bigint(20) UNSIGNED NOT NULL COMMENT '课件ID',
    `created_time`  datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`  datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`    varchar(36)         NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`    varchar(36)         NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`    tinyint(1)          NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '课程_课件关系表';


-- ----------------------------
-- experiment 实验表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `edu_teach`.`experiment`
(
    `id`               bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `name`             varchar(50)  NOT NULL COMMENT '实验名称',
    `subject`          varchar(15)  NOT NULL COMMENT '学科',
    `stage`            varchar(15)  NOT NULL COMMENT '学段',
    `grade`            varchar(15)  NOT NULL DEFAULT '' COMMENT '年级',
    `explanation`      varchar(100) NOT NULL DEFAULT '' COMMENT '说明',
    `material_text_id` bigint(20)   NOT NULL DEFAULT 0 COMMENT '实验器材长文本id',
    `creator_sso_id`   char(36)     NOT NULL COMMENT '创建人ID',
    `created_time`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`       varchar(36)  NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`       varchar(36)  NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`       tinyint(1)   NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '实验表';


-- ----------------------------
-- p_course_courseware 课程-实验关系表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `edu_teach`.`p_course_experiment`
(
    `id`            bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `course_id`     bigint(20) UNSIGNED NOT NULL COMMENT '课程ID',
    `experiment_id` bigint(20) UNSIGNED NOT NULL COMMENT '实验ID',
    `created_time`  datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`  datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`    varchar(36)         NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`    varchar(36)         NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`    tinyint(1)          NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '课程_实验关系表';



CREATE TABLE IF NOT EXISTS `edu_teach`.`long_text`
(
    `id`           bigint(20)  NOT NULL AUTO_INCREMENT COMMENT 'id',
    `content`      longtext    NOT NULL COMMENT '内容',
    `created_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`   varchar(36) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`   varchar(36) NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`   tinyint(1)  NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '文本信息表';

CREATE TABLE IF NOT EXISTS `teaching_experiment`
(
    `id`               bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `name`             varchar(50)  NOT NULL COMMENT '实验名称',
    `subject`          varchar(15)  NOT NULL COMMENT '学科',
    `stage`            varchar(15)  NOT NULL COMMENT '学段',
    `grade`            varchar(15)  NOT NULL COMMENT '年级',
    `open_type`        varchar(12)  NOT NULL COMMENT '开放类型',
    `explanation`      varchar(300) NOT NULL DEFAULT '' COMMENT '说明',
    `material_text_id` bigint(20)   NOT NULL COMMENT '实验器材准备说明长文本id',
    `content_text_id`  bigint(20)   NOT NULL COMMENT '实验内容长文本id',
    `ai_experiment_id` bigint(20)   NOT NULL DEFAULT 0 COMMENT 'AI实验ID',
    `school_id`        bigint(20)   NOT NULL COMMENT '所属学校ID',
    `creator_id`       bigint(20)   NOT NULL COMMENT '创建教职工ID',
    `created_time`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`       varchar(36)  NULL     DEFAULT NULL COMMENT '创建者',
    `updated_by`       varchar(36)  NULL     DEFAULT NULL COMMENT '更新者',
    `is_deleted`       tinyint(1)   NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '教学实验表';

CREATE TABLE IF NOT EXISTS `teaching_experiment_question`
(
    `id`               bigint(20)    NOT NULL AUTO_INCREMENT COMMENT 'id',
    `experiment_id`    bigint(20)    NOT NULL COMMENT '教学实验ID',
    `question_type`    varchar(15)   NOT NULL COMMENT '题目类型',
    `question_no`      smallint(5)   NOT NULL COMMENT '题目编号',
    `question_content` varchar(4000) NOT NULL COMMENT '题目内容',
    `created_time`     datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`     datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`       varchar(36)   NULL     DEFAULT NULL COMMENT '创建者',
    `updated_by`       varchar(36)   NULL     DEFAULT NULL COMMENT '更新者',
    `is_deleted`       tinyint(1)    NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT = '教学-实验报告题目';

CREATE TABLE IF NOT EXISTS `teaching_experiment_question_option`
(
    `id`             bigint(20)          NOT NULL AUTO_INCREMENT COMMENT 'id',
    `experiment_id`  bigint(20)          NOT NULL COMMENT '教学实验ID',
    `question_id`    bigint(20)          NOT NULL COMMENT '实验题目ID',
    `option_no`      smallint(5)         NOT NULL COMMENT '选项编号',
    `option_content` varchar(1000)       NOT NULL DEFAULT '' COMMENT '选项内容',
    `is_right`       tinyint(1) unsigned NOT NULL DEFAULT 0 COMMENT '是否正确选项(选择题)',
    `created_time`   datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`   datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`     varchar(36)         NULL     DEFAULT NULL COMMENT '创建者',
    `updated_by`     varchar(36)         NULL     DEFAULT NULL COMMENT '更新者',
    `is_deleted`     tinyint(1) unsigned NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT = '教学-实验报告-题目选项';

CREATE TABLE IF NOT EXISTS `teaching_experiment_operation`
(
    `id`                     bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `teaching_experiment_id` bigint(20) unsigned NOT NULL COMMENT '实验ID',
    `operation_name`         varchar(50)         NOT NULL COMMENT '操作要求',
    `operation_value`        decimal(5, 1)       NOT NULL DEFAULT '1.0' COMMENT '分值',
    `operation_no`           smallint(5)         NOT NULL COMMENT '编号',
    `created_time`           datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`           datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`             varchar(36)                  DEFAULT NULL COMMENT '创建者',
    `updated_by`             varchar(36)                  DEFAULT NULL COMMENT '更新者',
    `is_deleted`             tinyint(1)          NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='教学实验-自定义操作表';

CREATE TABLE IF NOT EXISTS `teaching_experiment_point`
(
    `id`                               bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `teaching_experiment_id`           bigint(20) unsigned NOT NULL COMMENT '实验ID',
    `teaching_experiment_operation_id` bigint(20) unsigned NOT NULL COMMENT '操作要求ID',
    `point_name`                       varchar(50)         NOT NULL COMMENT '评分标准',
    `point_value`                      decimal(5, 1)       NOT NULL DEFAULT '1.0' COMMENT '分值',
    `point_no`                         smallint(5)         NOT NULL COMMENT '编号',
    `created_time`                     datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`                     datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`                       varchar(36)                  DEFAULT NULL COMMENT '创建者',
    `updated_by`                       varchar(36)                  DEFAULT NULL COMMENT '更新者',
    `is_deleted`                       tinyint(1)          NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='教学实验-自定义评分点表';

CREATE TABLE IF NOT EXISTS `teaching_experiment_record`
(
    `id`                     bigint(20)          NOT NULL AUTO_INCREMENT COMMENT 'id',
    `teaching_experiment_id` bigint(20) unsigned NOT NULL COMMENT '实验ID',
    `course_id`              bigint(20) unsigned NOT NULL COMMENT '课程ID',
    `is_teaching`            tinyint(1) unsigned NOT NULL COMMENT '是否教学实验，教学实验由老师操作',
    `operator_id`            bigint(20) unsigned NOT NULL COMMENT '操作者ID, student_id 或 teaching_staff_id',
    `table_id`               bigint(20) unsigned NOT NULL COMMENT '实验台ID',
    `remark`                 varchar(500)        NOT NULL DEFAULT '' COMMENT '操作评分-说明',
    `end_time`               datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '实验结束时间',
    `created_time`           datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`           datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`             varchar(36)         NULL     DEFAULT NULL COMMENT '创建者',
    `updated_by`             varchar(36)         NULL     DEFAULT NULL COMMENT '更新者',
    `is_deleted`             tinyint(1)          NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '教学实验记录表';

CREATE TABLE IF NOT EXISTS `teaching_experiment_record_answer`
(
    `id`                            bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `teaching_experiment_record_id` bigint(20) unsigned NOT NULL COMMENT '实验记录ID',
    `question_id`                   bigint(20) unsigned NOT NULL COMMENT 'teaching_experiment_question表ID',
    `option_id`                     bigint(20) unsigned NOT NULL COMMENT 'teaching_experiment_question_option表ID',
    `answer`                        varchar(200)        NOT NULL DEFAULT '' COMMENT '回答',
    `ai_experiment_point_id`        bigint(20)          NOT NULL DEFAULT 0 COMMENT 'AI实验得分点的ID',
    `created_time`                  datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`                  datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`                    varchar(36)                  DEFAULT NULL COMMENT '创建者',
    `updated_by`                    varchar(36)                  DEFAULT NULL COMMENT '更新者',
    `is_deleted`                    tinyint(1)          NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='教学实验记录-实验报告答案表';

CREATE TABLE IF NOT EXISTS `teaching_experiment_record_score`
(
    `id`                               bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `teaching_experiment_record_id`    bigint(20) unsigned NOT NULL COMMENT '实验记录ID',
    `teaching_experiment_operation_id` bigint(20) unsigned NOT NULL COMMENT '操作要求ID',
    `teaching_experiment_point_id`     bigint(20) unsigned NOT NULL COMMENT '评分点ID',
    `ai_value`                         decimal(5, 1)       NOT NULL DEFAULT '1.0' COMMENT 'AI评分值',
    `ai_result`                        varchar(200)        NOT NULL DEFAULT '' COMMENT 'AI评分结果描述',
    `operate_pic_url`                  varchar(200)        NOT NULL DEFAULT '' COMMENT 'AI评分操作截图url',
    `teacher_value`                    decimal(5, 1)       NOT NULL DEFAULT '1.0' COMMENT '教师评分值',
    `remark`                           varchar(200)        NOT NULL DEFAULT '' COMMENT '教师评分说明',
    `created_time`                     datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`                     datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`                       varchar(36)                  DEFAULT NULL COMMENT '创建者',
    `updated_by`                       varchar(36)                  DEFAULT NULL COMMENT '更新者',
    `is_deleted`                       tinyint(1)          NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='教学实验记录-实验评分明细表';

CREATE TABLE IF NOT EXISTS `teaching_experiment_record_video`
(
    `id`                            bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `teaching_experiment_record_id` bigint(20) unsigned NOT NULL COMMENT '实验记录ID',
    `url`                           varchar(2000)       NOT NULL COMMENT '视频地址',
    `location`                      varchar(20)         NOT NULL COMMENT '摄像头位置',
    `created_time`                  datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`                  datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`                    varchar(36)                  DEFAULT NULL COMMENT '创建者',
    `updated_by`                    varchar(36)                  DEFAULT NULL COMMENT '更新者',
    `is_deleted`                    tinyint(1)          NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='教学实验记录-实验视频表';



CREATE TABLE IF NOT EXISTS `course_record`
(
    `id`              bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `course_id`       bigint(20) unsigned NOT NULL COMMENT '课程ID',
    `operator_id`     bigint(20) unsigned NOT NULL COMMENT '操作人id',
    `table_id`        bigint(20) unsigned NOT NULL COMMENT '实验台ID',
    `table_number`    int(3) unsigned     NOT NULL COMMENT '实验台编号',
    `is_record_video` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否录制视频',
    `is_podium`       tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否为讲台',
    `created_time`    datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`    datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`      varchar(36)         NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`      varchar(36)         NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`      tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='教学课程记录';

CREATE TABLE IF NOT EXISTS `school_timetable`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`         varchar(10)         NOT NULL COMMENT '名称',
    `school_id`    bigint(20)          NOT NULL COMMENT '所属学校ID',
    `start_time`   varchar(10)         NOT NULL COMMENT '开始时间',
    `end_time`     varchar(10)         NOT NULL COMMENT '结束时间',
    `lesson_no`    smallint(5)         NOT NULL COMMENT '节次编号',
    `created_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime                     DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`   varchar(36)                  DEFAULT NULL COMMENT '创建者',
    `updated_by`   varchar(36)                  DEFAULT NULL COMMENT '更新者',
    `is_deleted`   tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) COMMENT ='节次代码表';