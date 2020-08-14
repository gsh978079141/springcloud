package com.gsh.springcloud.order.design.singleton;

/**
 * @program: springcloud
 * @description: 单例模式-饿汉式,线程安全的
 * @author: Gsh
 * @create: 2020-05-27 16:59
 **/
public class HungrySingleton {
  /**
   * 1.将默认的构造方法设置成私有的，外界无法通过new创建实例
   */
  private HungrySingleton() {
  }

  /**
   * 2.创建类的唯一实例
   * 类加载时就已经新建了实例
   */
  private static HungrySingleton instance = new HungrySingleton();

  /**
   * 3.提供一个用于获取示例的方法
   *
   * @return
   */
  public static HungrySingleton getInstance() {
    return instance;
  }
}
