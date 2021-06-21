package com.gsh.springcloud.starter.file.constants;

/**
 * @Author: gsh
 * @Date: 2019-03-16 16:46
 */
public enum BucketEnum {
  ARITHMETIC_OSS("arithmetic-oss"),
  PANDABUS_OSS("pandabus-oss"),
  QUIXMART_OSS("quixmart-oss"),
  SMARTGATE_OSS("smartgate-oss");

  String name;

  BucketEnum(String name) {
    this.name = name;
  }
}
