package com.gsh.springcloud.common.test;

/**
 * @program: springcloud
 * @description: 类测试
 * @author: Gsh
 * @create: 2020-01-03 17:04
 **/
public class Class1 implements Interface1, Interface2 {

  @Override
  public void test1() {
    System.out.println("test1");

  }

  @Override
  public void test2() {
    System.out.println("test2");
  }


}
