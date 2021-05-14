package com.gsh.springcloud.account.constant;

import com.google.common.collect.Lists;

import java.util.List;

public interface ClientConstants {

  String ID_MANAGEMENT = "gsh-cloud";

  List<String> CACHED_CLIENTS = Lists.newArrayList(ID_MANAGEMENT);
}
