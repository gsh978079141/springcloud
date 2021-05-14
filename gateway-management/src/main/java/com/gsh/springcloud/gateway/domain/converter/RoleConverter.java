package com.gsh.springcloud.gateway.domain.converter;


import com.gsh.springcloud.gateway.domain.RoleDto;
import org.keycloak.representations.idm.RoleRepresentation;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface RoleConverter {

  default RoleDto convert(RoleRepresentation role) {
    return RoleDto.builder()
            .id(role.getId())
            .name(role.getName())
            .description(role.getDescription())
            .attributes(role.getAttributes())
            .clientRole(role.getClientRole())
            .clientId(role.getContainerId())
            .build();
  }

}
