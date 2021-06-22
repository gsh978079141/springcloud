package com.gsh.springcloud.account.converter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gsh.springcloud.account.dto.UserDetailDto;
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
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gsh
 */
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
    Map<String, List<String>> attributes = Maps.newHashMap();
    if (req.getAttributes() != null && req.getAttributes().size() > 0) {
      req.getAttributes().forEach((k, v) -> attributes.put(k, Lists.newArrayList(v)));
    }
    entity.setAttributes(attributes);
    entity.setClientRoles(req.getInitClientRoles());
    return entity;
  }

  public static UserRepresentation convert2entity(@NotNull UserUpdateReq req) {
    UserRepresentation entity = new UserRepresentation();
    entity.setFirstName(req.getFirstName());
    entity.setLastName(req.getLastName());
    entity.setEmail(req.getEmail());
    Map<String, List<String>> attributes = Maps.newHashMap();
    if (req.getAttributes() != null && req.getAttributes().size() > 0) {
      req.getAttributes().forEach((k, v) -> attributes.put(k, Lists.newArrayList(v)));
    }
    entity.setAttributes(attributes);
    entity.setClientRoles(req.getClientRolesMap());
    return entity;
  }


  public static UserDto convert2dto(@NotNull UserRepresentation entity) {
    UserDto user = new UserDto();
    user.setId(entity.getId());
    user.setFirstName(entity.getFirstName());
    user.setLastName(entity.getLastName());
    user.setUsername(entity.getUsername());
    user.setEmail(entity.getEmail());
    Map<String, String> attributes = Maps.newHashMap();
    if (entity.getAttributes() != null) {
      entity.getAttributes().forEach((k, v) -> {
        attributes.put(k, v.get(0));
      });
    }
    user.setAttributes(attributes);
    return user;
  }

  public static UserDetailDto entity2dto(@NotNull UserRepresentation entity) {
    UserDetailDto user = new UserDetailDto();
    user.setId(entity.getId());
    user.setFirstName(entity.getFirstName());
    user.setLastName(entity.getLastName());
    user.setUsername(entity.getUsername());
    user.setEmail(entity.getEmail());
    Map<String, String> attributes = Maps.newHashMap();
    if (entity.getAttributes() != null) {
      entity.getAttributes().forEach((k, v) -> {
        attributes.put(k, v.get(0));
      });
    }
    user.setAttributes(attributes);
    user.setAttributesMap(entity.getAttributes());
    user.setClientRolesMap(entity.getClientRoles());
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

  public static List<UserDetailDto> entity2dto(@NotNull List<UserRepresentation> users) {
    return users.stream().map(UserConverter::entity2dto)
            .collect(Collectors.toList());
  }

}
