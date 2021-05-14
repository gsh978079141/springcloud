package com.gsh.springcloud.account.converter;

import com.gsh.springcloud.account.request.AppCreateReq;
import com.gsh.springcloud.account.request.AppUpdateReq;
import com.gsh.springcloud.account.response.AppResp;
import org.keycloak.representations.idm.ClientRepresentation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppMapperConverter {

  ClientRepresentation convert2entity(AppCreateReq req);

  ClientRepresentation convert2entity(AppUpdateReq req);

  List<AppResp> convert2responses(List<ClientRepresentation> apps);
}
