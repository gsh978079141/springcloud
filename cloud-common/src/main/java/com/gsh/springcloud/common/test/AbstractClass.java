package com.gsh.springcloud.common.test;

/**
 * @program: springcloud
 * @description: 抽象类
 * @author: Gsh
 * @create: 2020-01-03 17:16
 **/
public abstract class AbstractClass {
  private int a = 1;

  protected void normalFun() {
    System.out.println("normalFun");
  }

  /**
   * 抽象方法
   */
  public abstract void abstractFun();
}
