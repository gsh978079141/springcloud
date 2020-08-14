package com.gsh.springcloud.order.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @program: springcloud
 * @description: 时间代理类
 * @author: Gsh
 * @create: 2020-05-27 15:27
 **/
public class TimeHandler implements InvocationHandler {

  private Object target;

  public TimeHandler(Object target) {
    this.target = target;
  }

  /**
   * @param proxy  被代理的对象
   * @param method 被代理对象的方法
   * @param args   被代理对象的方法的参数
   * @return 被代理方法的返回值
   * @throws Throwable
   */
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    long startTime = System.currentTimeMillis();
    System.out.println("时间开始");
    method.invoke(target);
    long endTime = System.currentTimeMillis();
    System.out.println("时间结束，耗时：" + (endTime - startTime) + "毫秒");
    return null;
  }
}
