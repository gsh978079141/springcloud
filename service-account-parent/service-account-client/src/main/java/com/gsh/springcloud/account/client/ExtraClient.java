package com.gsh.springcloud.account.client;

import com.gsh.springcloud.account.dto.ClientAuthorizationDataDto;
import com.gsh.springcloud.account.request.ExportAuthorizationReq;
import com.gsh.springcloud.common.vo.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @author gsh
 */
@FeignClient(url = "${feign.url.service-account}", path = "/extra", name = "extraClient")
@Api(tags = "Extra API")
public interface ExtraClient {


  /**
   * 导出client的权限认证相关数据
   *
   * @param req
   * @return
   */
  @ApiOperation(value = "导出client的权限认证相关数据", nickname = "exportAuthorizationData")
  @PostMapping("/export_client_authorization")
  JsonResult<ClientAuthorizationDataDto> exportAuthorizationData(@Valid @RequestBody ExportAuthorizationReq req);

  /**
   * 导入client的权限认证相关数据
   *
   * @param req
   * @return
   */
  @ApiOperation(value = "导入client的权限认证相关数据", nickname = "importAuthorizationData")
  @PostMapping("/import_client_authorization")
  JsonResult<?> importAuthorizationData(@Valid @RequestBody ClientAuthorizationDataDto req);


}
