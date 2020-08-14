package com.gsh.springcloud.order.design.singleton;

/**
 * @program: springcloud
 * @description: 单例模式-测试类
 * @author: Gsh
 * @create: 2020-05-27 17:02
 **/
public class Test {
  public static void main(String[] args) {
    //饿汉模式
    HungrySingleton h1 = HungrySingleton.getInstance();
    HungrySingleton h2 = HungrySingleton.getInstance();
    System.out.println(h1 == h2);
    //懒汉模式
    LazySingleton l1 = LazySingleton.getInstance();
    LazySingleton l2 = LazySingleton.getInstance();
    System.out.println(l1 == l2);

  }
}
