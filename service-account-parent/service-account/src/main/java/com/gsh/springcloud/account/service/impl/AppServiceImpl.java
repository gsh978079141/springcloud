package com.gsh.springcloud.account.service.impl;

import com.gsh.springcloud.account.converter.AppMapperConverter;
import com.gsh.springcloud.account.request.AppCreateReq;
import com.gsh.springcloud.account.request.AppUpdateReq;
import com.gsh.springcloud.account.response.AppListResp;
import com.gsh.springcloud.account.service.AppService;
import com.gsh.springcloud.account.service.KeycloakService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AppServiceImpl extends KeycloakService implements AppService {

  @Resource
  AppMapperConverter appMapperConverter;

  @Override
  public void create(AppCreateReq req) {
    realmResource().clients().create(appMapperConverter.convert2entity(req));
  }

  @Override
  public AppListResp listAll() {
    return new AppListResp(
            appMapperConverter.convert2responses(realmResource().clients().findAll())
    );
  }

  @Override
  public void updateById(String id, AppUpdateReq req) {
    realmResource().clients().get(id).update(appMapperConverter.convert2entity(req));
  }

  @Override
  public void deleteById(String id) {
    realmResource().clients().get(id).remove();
  }


}
