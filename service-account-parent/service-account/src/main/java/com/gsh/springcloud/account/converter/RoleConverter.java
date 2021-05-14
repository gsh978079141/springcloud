package com.gsh.springcloud.account.converter;

import com.gsh.springcloud.account.dto.RoleDto;
import com.gsh.springcloud.account.dto.RolePermissionsDto;
import com.gsh.springcloud.account.dto.RoleUpdateDto;
import com.gsh.springcloud.account.request.RoleCreateReq;
import com.gsh.springcloud.account.response.RoleDetailResp;
import com.gsh.springcloud.account.response.RoleResp;
import org.keycloak.representations.idm.RoleRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface RoleConverter {
  @Mappings({
          @Mapping(source = "containerId", target = "clientId")
  })
  RoleDto entity2dto(RoleRepresentation roleRepresentation);

  RoleRepresentation dto2entity(RoleDto dto);

  RoleResp dto2resp(RoleDto role);

  RoleDetailResp dto2detailResp(RoleDto role);


  RoleRepresentation req2entity(RoleCreateReq req);

  RolePermissionsDto dto2detail(RoleDto roleDto);

  RoleCreateReq dto2req(RoleUpdateDto dto);
}
