package com.gsh.springcloud.account.web;

import com.gsh.springcloud.account.client.ExtraClient;
import com.gsh.springcloud.account.dto.ClientAuthorizationDataDto;
import com.gsh.springcloud.account.request.ExportAuthorizationReq;
import com.gsh.springcloud.account.service.ExtraService;
import com.gsh.springcloud.common.vo.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: EDU_DBP_APP
 * @description:
 * @author: Gsh
 * @create: 2020-09-12 09:22
 **/
@RestController
@RequestMapping("/extra")
@Slf4j
public class ExtraController implements ExtraClient {

  @Resource
  ExtraService extraService;

  @Override
  public JsonResult<ClientAuthorizationDataDto> exportAuthorizationData(ExportAuthorizationReq req) {
    return JsonResult.success(extraService.exportAuthorizationData(req.getRealmName(), req.getClientId(), req.getTargetRoles()));
  }


  @Override
  public JsonResult<?> importAuthorizationData(ClientAuthorizationDataDto data) {
    extraService.importAuthorizationData(data);
    return JsonResult.success();
  }

}
