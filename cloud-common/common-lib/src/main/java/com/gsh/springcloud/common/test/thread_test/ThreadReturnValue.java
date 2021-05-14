package com.gsh.springcloud.common.test.thread_test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @program: springcloud
 * @description: 线程 返回值
 * @author: Gsh
 * @create: 2020-01-06 09:47
 **/
public class ThreadReturnValue implements Callable {

  private String msg;

  public ThreadReturnValue(String msg) {
    this.msg = msg;
  }

  @Override
  public Object call() throws Exception {
    Thread.sleep(1000);
    System.out.println(this.msg);
    return this.msg;
  }

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100), new ThreadPoolExecutor.CallerRunsPolicy());
    CompletionService completionService = new ExecutorCompletionService(executor);
    List<Future> list = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      list.add(completionService.submit(new ThreadReturnValue("第" + i + "你好")));
    }
    executor.shutdown();

    System.out.println(list.toArray());
  }

}


