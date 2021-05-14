package com.gsh.springcloud.gateway.constant;

import com.google.common.collect.Lists;

import java.util.List;

public interface RoleConstants {

  List<String> IGNORE_ROLE_NAMES = Lists.newArrayList("uma_authorization", "offline_access", "uma_protection");

  List<String> STUDENT_DEFAULT_ROLES = Lists.newArrayList("student");

  List<String> TEACHER_DEFAULT_ROLES = Lists.newArrayList("teacher");

  List<String> MANAGER_DEFAULT_ROLES = Lists.newArrayList("manager");

  List<String> BASE_DEFAULT_ROLES = Lists.newArrayList("manager", "teacher", "student");

  String SCHOOL_INIT_ROLE_NAME = "管理员";

}
