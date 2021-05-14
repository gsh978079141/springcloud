package com.gsh.springcloud.account.service;

import com.gsh.springcloud.account.request.AppCreateReq;
import com.gsh.springcloud.account.request.AppUpdateReq;
import com.gsh.springcloud.account.response.AppListResp;

public interface AppService {

  void create(AppCreateReq req);

  AppListResp listAll();

  void updateById(String id, AppUpdateReq req);

  void deleteById(String id);
}
