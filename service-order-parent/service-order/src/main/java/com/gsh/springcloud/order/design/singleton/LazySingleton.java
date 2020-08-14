package com.gsh.springcloud.order.design.singleton;

/**
 * @program: springcloud
 * @description: 单例模式-懒汉模式-非线程安全
 * @author: Gsh
 * @create: 2020-05-27 17:26
 **/
public class LazySingleton {
  /**
   * 1.将构造方法私有化，不允许外部直接创建对象
   */
  private LazySingleton() {
  }

  /**
   * 2.声明类的唯一实例
   * 保证 instance 在所有线程中同步
   */
  private static volatile LazySingleton instance;

  /**
   * 3.提供一个用于获取实例的方法
   *
   * @return
   */
  public static LazySingleton getInstance() {
    if (instance == null) {
      instance = new LazySingleton();
    }
    return instance;
  }
}
