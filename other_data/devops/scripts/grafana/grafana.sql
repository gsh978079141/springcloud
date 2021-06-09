--学生总数：
SELECT
$__unixEpochTo() AS time,
count(1) AS value
FROM student as a 
WHERE
a.is_deleted=0

--教师总数：
SELECT
$__unixEpochTo() AS time,
count(1) AS value
FROM teacher as a
WHERE
a.is_deleted=0

--学生班级分布:
SELECT
$__unixEpochTo() AS time,
tmp.ct as value ,cs.`name` as metric
from (
SELECT count(s.id) as ct,s.class_id from student s where s.is_deleted=0 group by s.class_id 
) as tmp inner join classes as cs on tmp.class_id=cs.id and cs.is_deleted=0

--考试试卷统计
SELECT
  $__timeGroupAlias(tmp.exam_date, '1h'),
  count(1) as value
from
(SELECT ex.id ,em.exam_date from examinee ex left join exam em on ex.exam_id=em.id where ex.deleted=0 and em.deleted=0
) as tmp
WHERE
  $__timeFilter(tmp.exam_date)
GROUP BY time
ORDER BY
  $__timeGroup(tmp.exam_date, '1h') asc;



select count(1) as value,STR_TO_DATE(tmp.exam_date ,'%Y-%m-%d') as time_sec
from
(SELECT ex.id ,em.exam_date from examinee ex left join exam em on ex.exam_id=em.id where ex.deleted=0 and em.deleted=0
) as tmp
GROUP BY STR_TO_DATE(tmp.exam_date,'%Y-%m-%d') ORDER BY time_sec asc


--实验台统计
SELECT
    $__unixEpochTo() AS time,
    tmp.etc as value ,er.`name` as metric
from (
         SELECT count(et.id) as etc,et.experiment_room_id from experiment_table et  where et.is_deleted=0 group by et.experiment_room_id
     ) as tmp inner join experiment_room as er on tmp.experiment_room_id=er.id and er.is_deleted=0


--算法打分统计
SELECT
  $__timeGroupAlias(sp.created_time, '1h'),
  count(1) as value
FROM  score_point sp
WHERE
  $__timeFilter(sp.created_time)
GROUP BY time
ORDER BY
  $__timeGroup(sp.created_time, '1h') asc;


select count(1) as value,STR_TO_DATE(sp.created_time ,'%Y-%m-%d') as time_sec
from score_point sp GROUP BY STR_TO_DATE(sp.created_time,'%Y-%m-%d') order by  time_sec asc


--算法节点状态
SELECT
    cn.ip as "节点IP",
    case cn.use_status
        when 0 then '不可用'
        when 1 then '可用'
        end AS "节点可用",
    case cn.online_status
        when 0 then '离线'
        when 1 then '在线'
        end AS "在线状态"
FROM compute_node cn

--文件上传统计
SELECT
  $__timeGroupAlias(ut.created_time, '1h'),
  count(1) as value
FROM  upload_task ut
WHERE
  $__timeFilter(ut.created_time)
GROUP BY time
ORDER BY
  $__timeGroup(ut.created_time, '1h') asc;


select count(1) as value,
STR_TO_DATE(ut.created_time ,'%Y-%m-%d') as time_sec
from upload_task ut GROUP BY STR_TO_DATE(ut.created_time,'%Y-%m-%d') ORDER BY time_sec asc

--视频处理统计
SELECT
    $__unixEpochTo() AS time,
    case vpt.`status`
        when 'PROCESS_PENDING' then '等待处理'
        when 'COMPLETE' then '任务完成'
        when 'MERGE_PENDING' then '正在合并视频'
        when 'MERGE_COMPLETE' then '合并视频成功'
        when 'M3U8_TRANSFORM_PENDING' then 'm3u8视频转换中'
        when 'M3U8_TRANSFORM_COMPLETE' then 'm3u8转换成功'
        when 'UPLOADING' then '上传中'
        when 'FAILED' then '任务失败'
        end as metric ,count(1) from video_process_task vpt GROUP BY vpt.`status`;

--视频数量统计
SELECT
  $__timeGroupAlias(vpt.create_time, '1h'),
  count(1)*3 as value
FROM video_process_task vpt
WHERE
  $__timeFilter(vpt.create_time)
GROUP BY time
ORDER BY
  $__timeGroup(vpt.create_time, '1h') asc;

select count(1)*3 as value,
STR_TO_DATE(vpt.create_time ,'%Y-%m-%d') as time_sec
from video_process_task vpt GROUP BY STR_TO_DATE(vpt.create_time,'%Y-%m-%d') ORDER BY time_sec asc


