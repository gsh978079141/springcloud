/* 数据库全名 = edu_foundation
 Date: 19/08/2020 10:57:24
*/

CREATE SCHEMA IF NOT EXISTS `edu_foundation` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
grant all privileges on edu_foundation.* to 'edu'@'%';

USE `edu_foundation`;

-- ----------------------------
-- Table structure for school
-- ----------------------------
CREATE TABLE `school`(
    `id`                    bigint unsigned     NOT NULL AUTO_INCREMENT COMMENT 'id',
    `name`                  varchar(50)         NOT NULL COMMENT '学校名称',
    `area_code`             varchar(50)         NOT NULL COMMENT '区/县',
    `property`              char(11)            NOT NULL COMMENT '办学性质(代码)',
    `stage`                 char(11)            NOT NULL COMMENT '学段(代码)',
    `contact_number_prefix` varchar(11)         NOT NULL DEFAULT '' COMMENT '联系电话-前缀',
    `contact_number`        varchar(11)         NOT NULL DEFAULT '' COMMENT '联系电话',
    `address`               varchar(100)        NOT NULL COMMENT '地址',
    `created_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`            tinyint(1)          NOT NULL DEFAULT '0' COMMENT '是否删除',
     PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '学校表';

-- ----------------------------
-- Table structure for classes
-- ----------------------------
CREATE TABLE `classes`
(
    `id`                    bigint(20) unsigned  NOT NULL AUTO_INCREMENT COMMENT 'id',
    `name`                  varchar(50)          NOT NULL COMMENT '班级名称',
    `school_id`             bigint(20) unsigned  NOT NULL COMMENT '所属学校',
    `school_name`           varchar(50)          NOT NULL COMMENT '学校名称',
    `stage`                 char(11)             NOT NULL COMMENT '学段',
    `grade`                 char(11)             NOT NULL COMMENT '年级',
    `start_year`            smallint(5) unsigned NOT NULL COMMENT '入学年份',
    `remark`                varchar(100)         NOT NULL DEFAULT '' COMMENT '备注',
    `created_time`          datetime             NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`          datetime             NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`            varchar(36)          NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`            varchar(36)          NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`            tinyint(1)           NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '班级表';



-- ----------------------------
-- Table structure for student
-- ----------------------------
CREATE TABLE `student` (
    `id`                bigint(20)      unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `sso_id`            char(36)        DEFAULT NULL COMMENT 'SSO系统ID',
    `school_id`         bigint(20)      unsigned DEFAULT '0' COMMENT '所属学校',
    `class_id`          bigint(20)      unsigned NOT NULL COMMENT '所属班级',
    `name`              varchar(20)     NOT NULL COMMENT '学生姓名',
    `sex`               tinyint(1)      NOT NULL COMMENT '性别',
    `student_code`      varchar(15)     NOT NULL COMMENT '学籍号',
    `remark`            varchar(100)    NOT NULL DEFAULT '' COMMENT '备注',
    `created_time`      datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`      datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`        varchar(36)     NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`        varchar(36)     NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`        tinyint(1)      NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '学生表';

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
CREATE TABLE `teacher` (
    `id`                    bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `sso_id`                char(36)            DEFAULT NULL COMMENT 'SSO系统ID',
    `staff_id`              bigint(20) unsigned NOT NULL COMMENT '教职工ID',
    `school_id`             bigint(20) unsigned DEFAULT NULL COMMENT '所属学校',
    `school_name`           varchar(50)         NOT NULL DEFAULT '' COMMENT '学校名称',
    `name`                  varchar(50)         NOT NULL DEFAULT '' COMMENT '教师名称',
    `sex`                   tinyint(1) unsigned NOT NULL COMMENT '性别，1男0女',
    `stage`                 char(11)            NOT NULL COMMENT '学段',
    `subject`               char(11)            NOT NULL COMMENT '学科(代码)',
    `teacher_level`         varchar(11)         NULL DEFAULT '' COMMENT '教师级别(代码)',
    `mobile_phone`          char(11)            NOT NULL COMMENT '手机号码',
    `identity_number`       varchar(25)         NOT NULL COMMENT '身份证号码',
    `is_class_teacher`      tinyint(1)          NOT NULL DEFAULT '0' COMMENT '是否班主任(1是0否)',
    `remark`                varchar(100)        DEFAULT NULL COMMENT '备注',
    `created_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`            tinyint(1)          NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '教师表';


-- ----------------------------
-- Table structure for p_teacher_class
-- ----------------------------
CREATE TABLE `p_teacher_class` (
    `id`                    bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `teacher_id`            bigint(20) unsigned NOT NULL COMMENT '教师ID',
    `class_id`              bigint(20) unsigned NOT NULL COMMENT '班级ID',
    `created_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`            tinyint(1)          NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci  COMMENT = '教师-班级关系表';


-- ----------------------------
-- Table structure for experiment_room
-- ----------------------------
CREATE TABLE `experiment_room` (
    `id`                    bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `code`                  varchar(50)         NOT NULL DEFAULT '' COMMENT '实验室编码',
    `school_id`             bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '所属学校',
    `school_name`           varchar(50)         NOT NULL DEFAULT '' COMMENT '学校名称',
    `name`                  varchar(50)         NOT NULL COMMENT '实验室名称',
    `subject`               char(11)            NOT NULL COMMENT '学科',
    `room_type`             varchar(50)         DEFAULT NULL,
    `table_allocation`      char(11)            NOT NULL DEFAULT '' COMMENT '分桌方式',
    `total_table_count`     int(3) unsigned     NOT NULL DEFAULT '24' COMMENT '实验台总数量',
    `row_table_count`       int(3) unsigned     NOT NULL DEFAULT '4'  COMMENT '每行实验台数量',
    `table_people_amount`   int(3) unsigned     NOT NULL DEFAULT '2' COMMENT '实验台每桌人数',
    `address`               varchar(50)         NOT NULL COMMENT '地址',
    `created_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`            tinyint(1)          NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '实验室信息表';


-- ----------------------------
-- Table structure for experiment_room_schedule
-- ----------------------------
CREATE TABLE `experiment_room_schedule` (
    `id`                    bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `school_id`             bigint(20) unsigned NOT NULL COMMENT '学校id',
    `experiment_room_id`    bigint(20) unsigned NOT NULL COMMENT '实验室ID',
    `start_time`            datetime(0)         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '开始时间',
    `end_time`              datetime(0)         NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '结束时间',
    `type`                  char(11)            NOT NULL DEFAULT '' COMMENT '日程安排类型: 考试，课程等',
    `description`           varchar(50)         NOT NULL DEFAULT '' COMMENT '日程描述, 课程/考试名称',
    `created_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`            tinyint(1)          NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '实验室日程安排表';




-- ----------------------------
-- Table structure for experiment_table
-- ----------------------------
CREATE TABLE `experiment_table` (
    `id`                    bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `experiment_room_id`    bigint(20) unsigned NOT NULL COMMENT '实验室ID',
    `name`                  varchar(20)         DEFAULT NULL COMMENT '实验台名称',
    `sn_code`               varchar(20)         NOT NULL COMMENT '实验台SN号',
    `mac_address`           varchar(50)         NOT NULL COMMENT '实验台MAC地址',
    `ip_address`            varchar(50)         NOT NULL COMMENT '实验台IP地址',
    `number`                int(3)              NOT NULL COMMENT '序号',
    `seat_number`           int(11)             DEFAULT NULL COMMENT '每桌人数',
    `status`                char(11)            DEFAULT NULL COMMENT '实验台状态',
    `podium`                tinyint(1)          DEFAULT '0' COMMENT '是否为讲台',
    `created_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`            tinyint(1)          NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) ,
  UNIQUE KEY `uk_sn_code` (`sn_code`) COMMENT 'snCode唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '实验台表';


-- ----------------------------
-- Table structure for device
-- ----------------------------
CREATE TABLE `device` (
    `id`                    bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '设备ID',
    `code`                  varchar(20)         NOT NULL DEFAULT '' COMMENT '设备编码',
    `ip_address`            varchar(15)         NOT NULL COMMENT '设备IP地址',
    `type`                  varchar(11)         NOT NULL COMMENT '设备类型',
    `sub_type`              varchar(11)         NOT NULL COMMENT '设备子类型',
    `location`              varchar(10)         NOT NULL DEFAULT '' COMMENT '设备位置',
    `status`                varchar(11)         NOT NULL DEFAULT '' COMMENT '设备状态',
    `user_name`             varchar(10)         NOT NULL DEFAULT '' COMMENT '设备用户名',
    `password`              varchar(10)         NOT NULL DEFAULT '' COMMENT '设备密码',
    `created_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`            tinyint(1)          NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
)  ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '设备表';



-- ----------------------------
-- Table structure for p_table_device
-- ----------------------------
CREATE TABLE `p_table_device` (
    `id`                    bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `device_id`             bigint(20) unsigned NOT NULL COMMENT '设备ID',
    `experiment_table_id`   bigint(20) unsigned NOT NULL COMMENT '实验台ID',
    `created_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`            tinyint(1)          NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
)  ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '实验台-设备关系表';


-- ----------------------------
-- Table structure for teaching_staff
-- ----------------------------
CREATE TABLE `teaching_staff` (
    `id`                        bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `sso_id`                    char(36)            NOT NULL COMMENT 'SSO系统ID',
    `school_id`                 bigint(20) unsigned DEFAULT NULL COMMENT '所属学校ID',
    `name`                      varchar(50)         NOT NULL COMMENT '职工名称',
    `sex`                       tinyint(1)          NOT NULL COMMENT '性别，1-男, 0-女',
    `identity_number`           varchar(25)         NOT NULL COMMENT '身份证号码',
    `is_need_check_password`    tinyint(1) unsigned DEFAULT '0' COMMENT '是否需要验证旧密码',
    `mobile_phone`              char(11)            NOT NULL COMMENT '手机号码',
    `position`                  char(11)            NOT NULL COMMENT '职务',
    `remark`                    char(100)           NOT NULL DEFAULT '' COMMENT '备注',
    `last_login_time`           datetime(0)         DEFAULT NULL COMMENT '上次登录时间',
    `created_time`              datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`              datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`                varchar(36)         NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(36)         NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`                tinyint(1)          NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '教职工表';


-- ----------------------------
-- Table structure for teaching_staff-role
-- ----------------------------
CREATE TABLE `teaching_staff_role` (
    `id`                    bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `client_id`             varchar(40)         DEFAULT '' COMMENT 'client_id',
    `staff_id`              bigint(20) unsigned NOT NULL COMMENT '管理人员ID',
    `role_name`             varchar(20)         NOT NULL COMMENT '角色名称',
    `created_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`            tinyint(1)          NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) 
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '教职工-角色表';

-- ai_experiment, ai_operation, ai_score_point 为当前实验数据，所有实验步骤，得分点。

-- 创建实验时可从当前数据中根据业务需要组合指定的得分点与操作来构建新的版本的实验

-- ----------------------------
-- AI实验表
-- ----------------------------
CREATE TABLE `ai_experiment` (
    `id`                    bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `code`                  varchar(20)         NOT NULL COMMENT '实验代码',
    `name`                  varchar(50)         NOT NULL COMMENT '实验名称',
    `subject`               varchar(11)         NOT NULL COMMENT '学科',
    `status`                smallint(3)         NOT NULL DEFAULT 1 COMMENT '状态：1-正常，2-禁用',
    `current_version_no`    int(3)              NOT NULL COMMENT '当前版本',
    `algo_version`          varchar(20)         NOT NULL COMMENT '对应的算法版本号',
    `sort_num`              int(3)              NOT NULL COMMENT '排序',
    `created_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`            tinyint(1)          NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE ,
  UNIQUE KEY `uk_code` (`code`) USING BTREE COMMENT '实验代码-唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT='AI实验表';


CREATE TABLE `ai_experiment_snapshot` (
    `id`                    bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `experiment_id`         bigint(20) unsigned NOT NULL COMMENT '实验ID',
    `display_name`          varchar(200)        NOT NULL COMMENT '显示名称',
    `version_no`            int(3)              NOT NULL COMMENT '实验版本号', -- 从1开始，每次修改加1
    `algo_version`          varchar(20)         NOT NULL COMMENT '对应的算法版本号',
    `camera_location`       varchar(40)         NOT NULL COMMENT '摄像头位置', -- e.g. front1,side1,top1
    `created_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`            tinyint(1)          NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_experiment_id_version_no` (`experiment_id`, `version_no`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT='AI实验快照表';


CREATE TABLE `ai_operation_snapshot` (
    `id`                    bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `experiment_id`         bigint(20) unsigned NOT NULL COMMENT '实验ID',
    `operation_id`          bigint(20) unsigned NOT NULL COMMENT '操作ID',
    `experiment_version_no` int(3)              NOT NULL COMMENT '实验版本号',
    `name`                  varchar(200)        NOT NULL COMMENT '操作内容',
    `value`                 decimal(5,1)        NOT NULL DEFAULT '1.0' COMMENT '分值',
    `created_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`            tinyint(1)          NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT='AI实验操作快照表';


CREATE TABLE `ai_score_point_snapshot` (
    `id`                    bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `experiment_id`         bigint(20) unsigned NOT NULL COMMENT '实验ID',
    `operation_id`          bigint(20) unsigned NOT NULL COMMENT '操作ID',
    `point_id`              bigint(20) unsigned NOT NULL COMMENT '得分点ID',
    `experiment_version_no` int(3)              NOT NULL COMMENT '实验版本号',
    `name`                  varchar(200)        NOT NULL COMMENT '评分点内容',
    `value`                 decimal(5,1)        NOT NULL DEFAULT '1.0' COMMENT '分值',
    `created_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`          datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`            varchar(36)         NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`            tinyint(1)          NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
)  ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT='AI实验得分点快照表';


-- 算法实验报告表
CREATE TABLE `ai_experiment_question` (
    `id`                    bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `ai_experiment_id`      bigint(20) NOT NULL COMMENT '算法实验ID',
    `question_type`         varchar(15) NOT NULL COMMENT '题目类型',
    `question_no`           smallint(5) NOT NULL COMMENT '题目编号',
    `question_content`      varchar(4000) NOT NULL COMMENT '题目内容',
    `created_time`          datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`          datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`            varchar(36)     NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`            varchar(36)     NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`            tinyint(1)      NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT='实验报告题目';

-- 算法实验报告选项表
CREATE TABLE `ai_experiment_question_option` (
    `id`               bigint         unsigned  NOT NULL AUTO_INCREMENT COMMENT 'id',
    `ai_experiment_id` bigint(20)               NOT NULL COMMENT '算法实验ID',
    `question_id`      bigint(20)               NOT NULL COMMENT '实验题目ID',
    `option_no`        smallint(5)              NOT NULL COMMENT '选项编号',
    `option_content`   varchar(1000)            NOT NULL DEFAULT '' COMMENT '选项内容',
    `is_right`         tinyint(1)      unsigned NOT NULL DEFAULT '0' COMMENT '是否正确选项(选择题)',
    `created_time`     datetime                 NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`     datetime                 NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`       varchar(36)              NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`       varchar(36)              NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`       tinyint(1)               NOT NULL DEFAULT '0' COMMENT '是否删除',
     PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='教学实验报告题目选项';


-- ----------------------------
-- ip_camera 网络摄像头
-- ----------------------------
CREATE TABLE `ip_camera`
(
    `id`                  bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `school_id`           bigint unsigned NOT NULL COMMENT '学校ID',
    `experiment_room_id`  bigint unsigned NOT NULL COMMENT '实验室ID',
    `experiment_table_id` bigint unsigned NOT NULL COMMENT '实验台ID',
    `location`            varchar(10)     NOT NULL DEFAULT '' COMMENT '位置: top1, side1, front1, front2',
    `ip`                  varchar(16)     NOT NULL COMMENT 'IP',
    `stream_port`         int             NOT NULL COMMENT '流端口',
    `admin_port`          int             NOT NULL COMMENT '控制台端口',
    `main_stream_name`    varchar(10)     NOT NULL COMMENT '主码流名称',
    `minor_stream_name`   varchar(10)     NOT NULL COMMENT '子码流名称',
    `username`            varchar(16)     NOT NULL DEFAULT '' COMMENT '用户名',
    `password`            varchar(16)     NOT NULL DEFAULT '' COMMENT '密码',
    `status`              varchar(16)     NOT NULL DEFAULT 'unknown' COMMENT '设备状态',
    `created_time`        datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`        datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`          varchar(36)     NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`          varchar(36)     NOT NULL DEFAULT '' COMMENT '更新者',
    `is_deleted`          tinyint(1)      NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT = '网络摄像头';

ALTER TABLE `ip_camera`
    ADD INDEX idx_school_id_room_id (school_id asc, experiment_room_id asc);
ALTER TABLE `ip_camera`
    ADD INDEX idx_experiment_table_id (experiment_table_id asc);

