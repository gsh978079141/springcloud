package com.gsh.springcloud.order.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @program: springcloud
 * @description: 测试类
 * @author: Gsh
 * @create: 2020-05-27 15:32
 **/
public class Test {
  /**
   * JDK动态代理 测试
   *
   * @param args
   */
  public static void main(String[] args) {
    Car car = new Car();
    Class<? extends Car> cls = car.getClass();
    InvocationHandler h = new TimeHandler(car);
    //newProxyInstance入参：类加载器-实现接口-handle
    //返回值 为动态代理类返回的对象
    Moveable moveAble = (Moveable) Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), h);
    moveAble.move();
    moveAble.jiayou();
  }
}
