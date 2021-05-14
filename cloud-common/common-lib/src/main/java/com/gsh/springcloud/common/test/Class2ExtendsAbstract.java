package com.gsh.springcloud.common.test;

/**
 * @program: springcloud
 * @description: 继承抽象类
 * @author: Gsh
 * @create: 2020-01-03 17:43
 **/
public class Class2ExtendsAbstract extends AbstractClass {

  @Override
  public void abstractFun() {
    super.normalFun();
  }
}
