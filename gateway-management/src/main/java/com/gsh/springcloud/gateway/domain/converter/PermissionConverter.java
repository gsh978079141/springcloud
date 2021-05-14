package com.gsh.springcloud.gateway.domain.converter;

import com.gsh.springcloud.gateway.domain.PermissionDto;
import org.keycloak.representations.idm.authorization.ScopePermissionRepresentation;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface PermissionConverter {

  default List<PermissionDto> convert(List<ScopePermissionRepresentation> permissions) {
    return permissions.stream().map(this::convert).collect(Collectors.toList());
  }

  PermissionDto convert(ScopePermissionRepresentation permission);


}
