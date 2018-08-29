package com.gsh.springcloud.servicezuul.test;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author gsh
 * @Title: TestTask
 * @Package com.gsh.springcloud.servicezuul.test
 * @Description: 启动类加@EnableScheduling 开启定时任务  @Component注解扫描实体，然后方法加@Scheduled开始定时任务
 * fixedRate 3000ms = 3s
 * @date 2018/7/21 21:31
 */
@Component
public class TestTask {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     * cron 表达式网址
     */
//    @Scheduled(fixedRate = 3000)
//    @Scheduled(cron = "4-40 * * * * ? ")
    //定义每过三秒执行任务
    public void reportCurrentTime() {
        System.out.println("现在时间：" + dateFormat.format(new Date()));
    }

}
