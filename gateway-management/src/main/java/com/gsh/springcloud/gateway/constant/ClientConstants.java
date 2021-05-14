package com.gsh.springcloud.gateway.constant;

import com.google.common.collect.Lists;

import java.util.List;

public interface ClientConstants {

  String ID_MANAGEMENT = "edu-management-bff";

  String ID_TEST = "test-cli";

  List<String> CACHED_CLIENTS = Lists.newArrayList(ID_MANAGEMENT, ID_TEST);
}
