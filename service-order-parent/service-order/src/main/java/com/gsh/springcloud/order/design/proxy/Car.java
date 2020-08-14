package com.gsh.springcloud.order.design.proxy;

import java.util.Random;

/**
 * @program: springcloud
 * @description:
 * @author: Gsh
 * @create: 2020-05-27 14:14
 **/
public class Car implements Moveable {
  @Override
  public void move() {
    try {
      Thread.sleep(new Random().nextInt(1000));
      System.out.println("汽车行驶中。。。。");

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void jiayou() {
    try {
      Thread.sleep(new Random().nextInt(1000));
      System.out.println("汽车加油。。。。");

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
