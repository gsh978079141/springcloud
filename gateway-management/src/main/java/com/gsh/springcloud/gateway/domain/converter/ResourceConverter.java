package com.gsh.springcloud.gateway.domain.converter;

import com.google.common.collect.Lists;
import com.gsh.springcloud.gateway.domain.ResourceDto;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;
import org.keycloak.representations.idm.authorization.ScopeRepresentation;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ResourceConverter {

  default List<ResourceDto> entity2dto(List<ResourceRepresentation> resources) {
    return resources.stream().map(this::entity2dto).collect(Collectors.toList());
  }


  default ResourceDto entity2dto(ResourceRepresentation resource) {
    return ResourceDto
            .builder()
            .id(resource.getId())
            .name(resource.getName())
            .displayName(resource.getDisplayName())
            .type(resource.getType())
            .attributes(resource.getAttributes())
            .ownerManagedAccess(resource.getOwnerManagedAccess())
            .scopes(resource.getScopes().stream().map(ScopeRepresentation::getName).collect(Collectors.toSet()))
            .uris(resource.getUris())
            .build();
  }

  default List<ResourceRepresentation> dto2entity(List<ResourceDto> dtoList) {
    List<ResourceRepresentation> resources = Lists.newArrayListWithCapacity(dtoList.size());
    dtoList.forEach(dto -> resources.add(dto2entity(dto)));
    return resources;
  }

  default ResourceRepresentation dto2entity(ResourceDto dto) {
    ResourceRepresentation resource = new ResourceRepresentation();
    resource.setId(dto.getId());
    resource.setName(dto.getName());
    resource.setDisplayName(dto.getDisplayName());
    resource.setType(dto.getType());
    resource.setAttributes(dto.getAttributes());
    resource.setOwnerManagedAccess(dto.getOwnerManagedAccess());
    resource.setUris(dto.getUris());
    return resource;
  }


}
