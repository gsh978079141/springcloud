CREATE SCHEMA IF NOT EXISTS `deepblue_k12_dev` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `deepblue_k12_dev`;

-- ----------------------------
-- Table structure for exam_query
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_query`  (
  `id`                  int(11)         NOT NULL AUTO_INCREMENT,
  `callback_over_time`  datetime(0)     NULL DEFAULT NULL,
  `callback_return`     longtext        NULL,
  `callback_url`        varchar(255)    NULL DEFAULT NULL,
  `create_time`         datetime(0)     NULL DEFAULT NULL,
  `detect_over_time`    datetime(0)     NULL DEFAULT NULL,
  `exam_id`             varchar(64)     NULL DEFAULT NULL,
  `exam_name`           varchar(255)    NULL DEFAULT NULL,
  `failure_cause`       varchar(1000)   NULL DEFAULT NULL,
  `judge_result`        longtext        NULL,
  `logical_over_time`   datetime(0)     NULL DEFAULT NULL,
  `param`               varchar(3000)   NULL DEFAULT NULL,
  `post_result`         varchar(255)    NULL DEFAULT NULL,
  `post_status`         varchar(50)     NULL DEFAULT '',
  `status`              varchar(50)     NULL DEFAULT '',
  `student_id`          varchar(64)     NULL DEFAULT NULL,
  PRIMARY KEY (`id`) ,
  INDEX `examID`(`exam_id`) ,
  INDEX `studentID`(`student_id`) 
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '';


-- ----------------------------
-- Table structure for exam_question
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_question`  (
  `id`              int(11)          NOT NULL AUTO_INCREMENT,
  `answer_info`     varchar(3000)   NULL DEFAULT NULL,
  `experiment_id`   int(11)         NULL DEFAULT NULL,
  `question_id`     int(11)         NULL DEFAULT NULL,
  `title`           varchar(1000)   NULL DEFAULT NULL,
  PRIMARY KEY (`id`) ,
  INDEX `questionId`(`question_id`) ,
  INDEX `experimentId`(`experiment_id`) 
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '考试考题表';


-- ----------------------------
-- Table structure for exam_score_question_id
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_score_question_id`  (
  `score_id`    int(11)     NOT NULL AUTO_INCREMENT,
  `question_id` int(11)     NOT NULL,
  `sort_num`    int(11)     NOT NULL,
  PRIMARY KEY (`score_id`) ,
  INDEX `questionId`(`question_id`) ,
  INDEX `sortNum`(`sort_num`) ,
  INDEX `scoreId`(`score_id`) 
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '打分点-题目对应关系表';

