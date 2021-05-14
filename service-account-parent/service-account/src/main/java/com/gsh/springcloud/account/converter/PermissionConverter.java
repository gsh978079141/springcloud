package com.gsh.springcloud.account.converter;

import com.gsh.springcloud.account.dto.PermissionDetailDto;
import com.gsh.springcloud.account.dto.PermissionDto;
import org.keycloak.representations.idm.authorization.ScopePermissionRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface PermissionConverter {

  default List<PermissionDto> entity2dto(List<ScopePermissionRepresentation> permissions) {
    return permissions.stream().map(this::entity2dto).collect(Collectors.toList());
  }

  PermissionDto entity2dto(ScopePermissionRepresentation permission);

  List<ScopePermissionRepresentation> dto2entity(List<PermissionDto> dtos);

  @Mappings({
//          @Mapping(target = "roles", ignore = true),
          @Mapping(target = "resources", ignore = true)
  })
  PermissionDetailDto entity2detailDto(ScopePermissionRepresentation permission);

}
