package com.gsh.springcloud.account.converter;


import com.google.common.collect.Lists;
import com.gsh.springcloud.account.dto.UserDto;
import com.gsh.springcloud.account.request.UserCreateReq;
import com.gsh.springcloud.account.request.UserUpdateReq;
import com.gsh.springcloud.account.response.UserResp;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public class UserConverter {

  public static UserRepresentation convert2entity(@NotNull UserCreateReq req) {
    // parameters handle
    if (StringUtils.isBlank(req.getInitPassword())) {
      req.setInitPassword(req.getLoginUsername());
    }
    // build entity object
    UserRepresentation entity = new UserRepresentation();
    entity.setUsername(req.getLoginUsername());
    entity.setLastName(req.getLastName());
    entity.setFirstName(req.getFirstName());
    entity.setEmail(req.getEmail());
    entity.setEnabled(true);
    // password setting
    CredentialRepresentation credential = new CredentialRepresentation();
    credential.setType(CredentialRepresentation.PASSWORD);
    credential.setValue(req.getInitPassword());
    credential.setTemporary(false);
    entity.setCredentials(Arrays.asList(credential));
    // other fields
    entity.setRequiredActions(Lists.newArrayList());
    entity.setAttributes(req.getAttributesMap());
    entity.setRealmRoles(req.getInitRealmRoles());
    entity.setClientRoles(req.getInitClientRoles());
    return entity;
  }

  public static UserRepresentation convert2entity(@NotNull UserUpdateReq req) {
    UserRepresentation entity = new UserRepresentation();
    entity.setUsername(req.getUserName());
    entity.setFirstName(req.getFirstName());
    entity.setLastName(req.getLastName());
    entity.setEmail(req.getEmail());
    entity.setAttributes(req.getAttributesMap());
    entity.setClientRoles(req.getClientRolesMap());
    entity.setRealmRoles(req.getRealmRoles());
    return entity;
  }


  public static UserDto entity2dto(@NotNull UserRepresentation entity) {
    UserDto user = new UserDto();
    user.setId(entity.getId());
    user.setFirstName(entity.getFirstName());
    user.setLastName(entity.getLastName());
    user.setUsername(entity.getUsername());
    user.setEmail(entity.getEmail());
    user.setAttributesMap(entity.getAttributes());
    user.setClientRolesMap(entity.getClientRoles());
    user.setRealmRoles(entity.getRealmRoles());
    return user;
  }

  public static UserResp entity2resp(@NotNull UserRepresentation entity) {
    UserResp resp = new UserResp();
    resp.setId(entity.getId());
    resp.setFirstName(entity.getFirstName());
    resp.setLastName(entity.getLastName());
    resp.setUsername(entity.getUsername());
    resp.setEmail(entity.getEmail());
    resp.setAttributesMap(entity.getAttributes());
    resp.setClientRolesMap(entity.getClientRoles());
    resp.setRealmRoles(entity.getRealmRoles());
    return resp;
  }

  public static List<UserDto> entity2dto(@NotNull List<UserRepresentation> users) {
    return users.stream().map(UserConverter::entity2dto)
            .collect(Collectors.toList());
  }
}
