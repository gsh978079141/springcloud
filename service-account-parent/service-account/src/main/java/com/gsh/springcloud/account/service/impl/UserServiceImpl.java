package com.gsh.springcloud.account.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.gsh.springcloud.account.config.support.keycloak.KeycloakAdminProperties;
import com.gsh.springcloud.account.converter.UserConverter;
import com.gsh.springcloud.account.dto.AccessTokenDto;
import com.gsh.springcloud.account.exception.UserCreateFailedException;
import com.gsh.springcloud.account.exception.UserNotFoundException;
import com.gsh.springcloud.account.helper.KeycloakHelper;
import com.gsh.springcloud.account.request.UserCreateBatchReq;
import com.gsh.springcloud.account.request.UserCreateReq;
import com.gsh.springcloud.account.request.UserSearchReq;
import com.gsh.springcloud.account.request.UserUpdateReq;
import com.gsh.springcloud.account.response.ExistenceResp;
import com.gsh.springcloud.account.response.UserListResp;
import com.gsh.springcloud.account.response.UserResp;
import com.gsh.springcloud.account.service.KeycloakService;
import com.gsh.springcloud.account.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl extends KeycloakService implements UserService {

  @Resource
  KeycloakHelper keycloakHelper;

  @Resource
  KeycloakAdminProperties keycloakAdminProperties;

  @Override
  public void create(UserCreateReq req) {
    // 判断是否存在，存在则，检查属性，角色是否已有，没有则创建，有则直接返回
    Optional<UserResp> optionalUser = getByUserName(req.getLoginUsername());
    if (optionalUser.isPresent() && null != req.getInitClientRoles()) {
      addRolesForExistedUser(optionalUser.get(), req.getInitClientRoles());
      return;
    }

    UserRepresentation ur = UserConverter.convert2entity(req);
    log.info("create user, req:{} ", JSON.toJSONString(ur));

    Response resp = null;
    try {
      resp = realmResource().users().create(ur);
      log.info("create user, resp:{} ", JSON.toJSONString(resp));
    } finally {
      if (resp != null) {
        resp.close();
      }
    }

    Response.Status status = resp.getStatusInfo().toEnum();
    if (!Status.CREATED.equals(status)) {
      log.warn("result of invocation about creating user on keycloak, status code::::{}, headers::::{}, body::::{} ",
              resp.getStatusInfo().getStatusCode(), JSON.toJSONString(resp.getHeaders()), JSON.toJSONString(resp.getEntity()));
      throw new UserCreateFailedException(req.getLoginUsername());
    }
    // Assign role to the user
    optionalUser = getByUserName(req.getLoginUsername());
    if (!optionalUser.isPresent()) {
      throw new UserCreateFailedException(req.getLoginUsername());
    }
    String userId = optionalUser.get().getId();

    if (null != ur.getClientRoles() && ur.getClientRoles().size() > 0) {
      bindClientRolesWithNewUser(userId, ur.getClientRoles());
    }

  }

  private void addRolesForExistedUser(UserResp existedUser, Map<String, List<String>> clientRoles) {
    if (clientRoles.isEmpty()) {
      return;
    }
    clientRoles.forEach((clientId, roles) -> {
      Set<String> existedRoles = new HashSet<>(existedUser.getClientRolesMap().getOrDefault(clientId, Lists.newArrayList()));
      Set<String> bindingRoles = new HashSet<>(roles);
      Set<String> realBindingRoles = Sets.intersection(bindingRoles, existedRoles);
      if (CollectionUtils.isNotEmpty(realBindingRoles)) {
        bindClientRolesWithNewUser(existedUser.getId(), clientId, realBindingRoles);
      }
    });
  }


  /**
   * 给新创建用户绑定指定client集合的角色
   *
   * @param userId
   * @param clientRolesMap
   */
  private void bindClientRolesWithNewUser(String userId, Map<String, List<String>> clientRolesMap) {
    clientRolesMap.forEach((clientId, roleNames) ->
            bindClientRolesWithNewUser(userId, clientId, new HashSet<>(roleNames)));
  }

  private void bindClientRolesWithNewUser(String userId, String clientId, Set<String> roleNames) {
    //  绑定该client的相关角色
    ClientRepresentation clientRepresentation = clientResource(clientId).toRepresentation();
    List<RoleRepresentation> roleRepresentations = Lists.newArrayList();
    roleNames.forEach(roleName -> {
      RoleRepresentation roleRepresentation = clientResource(clientId).roles().get(roleName).toRepresentation();
      roleRepresentations.add(roleRepresentation);
    });
    if (CollectionUtils.isNotEmpty(roleRepresentations)) {
      // 给用户添加角色
      realmResource().users().get(userId).roles().clientLevel(clientRepresentation.getId())
              .add(roleRepresentations);
    }
  }


  @Override
  public void update(String id, UserUpdateReq req) {
    realmResource().users().get(id)
            .update(UserConverter.convert2entity(req));
    if (Objects.nonNull(req.getClientRolesMap())) {
      keycloakHelper.bindClientRoles4user(id, req.getClientRolesMap());
    }
  }

  @Override
  public void disable(String id) {
    realmResource().users().get(id);
  }

  @Override
  public void deleteById(String id) {
    realmResource().users().delete(id);
  }

  @Override
  public UserResp getById(String id) {
    UserResource ur = realmResource().users().get(id);
    if (Objects.isNull(ur)) {
      throw new UserNotFoundException();
    }
    return UserConverter.entity2resp(ur.toRepresentation());
  }


  @Override
  public UserListResp searchByPage(UserSearchReq req) {
    return new UserListResp(UserConverter.entity2dto(
            realmResource().users().search(
                    req.getUsername(),
                    req.getFirstName(),
                    req.getLastName(),
                    req.getEmail(),
                    req.calculateStartRow(),
                    req.getPageSize())));
  }

  @Override
  public UserListResp getAllUsers() {
    return new UserListResp(UserConverter.entity2dto(
            realmResource().users().list()));
  }

  @Override
  public void bindRoles4user(String id, List<String> roles) {
    List<RoleRepresentation> rps = realmResource().roles().list().stream()
            .filter(item -> roles.contains(item.getName()))
            .collect(Collectors.toList());
    realmResource().users().get(id).roles().getAll().getRealmMappings().addAll(rps);
  }

  @Override
  public void bindClientRoles4user(String id, String clientId, List<String> roles) {
    ClientResource cr = clientResource(clientId);
    realmResource().users().get(id).roles()
            .clientLevel(cr.toRepresentation().getId())
            .add(cr.roles().list().stream()
                    .filter(item -> roles.contains(item.getName()))
                    .collect(Collectors.toList()));
  }

  @Override
  public void unbindClientRoles4user(String userId, String clientId, List<String> roles) {
    ClientResource cr = clientResource(clientId);
//    realmResource().users().get(id).roles()
//            .clientLevel(cr.toRepresentation().getId())
//            .remove();
    //检查当前用户存在的，需要解除绑定的角色
    List<RoleRepresentation> unbindingRoles = new ArrayList<>();
    realmResource().users().get(userId).roles()
            .clientLevel(cr.toRepresentation().getId()).listAll()
            .forEach(r -> {
              if (roles.contains(r.getName())) {
                unbindingRoles.add(r);
              }
            });
    if (unbindingRoles.size() == 0) {
      return;
    }
    realmResource().users().get(userId).roles()
            .clientLevel(cr.toRepresentation().getId()).remove(unbindingRoles);

  }

  @Override
  public UserResp getByUsername(String username) {
    List<UserRepresentation> urs = realmResource().users().search(username);
    if (CollectionUtils.isEmpty(urs)) {
      throw new UserNotFoundException();
    }
    return UserConverter.entity2resp(urs.get(0));
  }


  @Override
  public Optional<UserResp> getByUserName(String username) {
    List<UserRepresentation> userRepresentations = realmResource().users().search(username);
    if (CollectionUtils.isEmpty(userRepresentations)) {
      return Optional.empty();
    }
    return Optional.of(UserConverter.entity2resp(userRepresentations.get(0)));
  }


  @Override
  public AccessTokenDto getAccessToken(String userName, String password) {
    AccessTokenDto resp = new AccessTokenDto();
    resp.setToken(KeycloakBuilder.builder()
            .realm(keycloakAdminProperties.getManagedRealm())
            .serverUrl(keycloakAdminProperties.getServerUrl())
            .clientId(keycloakAdminProperties.getLoginClientId())
            .clientSecret(keycloakAdminProperties.getLoginClientSecret())
            .username(userName)
            .password(password)
            .grantType(OAuth2Constants.PASSWORD).build().tokenManager().getAccessTokenString());
    return resp;
  }

  @Override
  public void createInBatch(UserCreateBatchReq req) {
    req.getUsers().forEach(item -> create(item));
  }

  @Override
  public ExistenceResp checkExistence(String id, String username, String email) {
    if (StringUtils.isAllBlank(id, username, email)) {
      return new ExistenceResp(false);
    }
    if (StringUtils.isNotBlank(id)) {
      UserResource userResource = realmResource().users().get(id);
      if (Objects.nonNull(userResource)) {
        return new ExistenceResp(true, "id", id);
      }
    }
    if (StringUtils.isNotBlank(username) &&
            realmResource().users().search(username, 0, 1, true).size() > 0) {
      return new ExistenceResp(true, "username", username);
    }
    if (StringUtils.isNotBlank(email) &&
            realmResource().users().search(email, 0, 1, true).size() > 0) {
      return new ExistenceResp(true, "email", email);
    }
    return new ExistenceResp(false);
  }

  @Override
  public UserResp getWithParams(String username, String email) {
    List<UserRepresentation> uus = realmResource().users().search(username, 0, 1);
    if (CollectionUtils.isNotEmpty(uus)) {
      return UserConverter.entity2resp(uus.get(0));
    }
    List<UserRepresentation> eus = realmResource().users().search(email, 0, 1);
    if (CollectionUtils.isNotEmpty(eus)) {
      return UserConverter.entity2resp(eus.get(0));
    }
    throw new UserNotFoundException();
  }

  @Override
  public UserListResp getByIds(String[] ids) {
    Map<String, UserRepresentation> userRepresentationMap = realmResource().users().list().stream().collect(Collectors.toMap(UserRepresentation::getId, o -> o));
    List<UserRepresentation> list = Lists.newArrayList();
    for (String id : ids) {
      if (userRepresentationMap.containsKey(id)) {
        list.add(userRepresentationMap.get(id));
      }
    }
    return new UserListResp(UserConverter.entity2dto(list));
  }

  @Override
  public void resetPassword(String userId, String newPassword) {
    UserResource ur = realmResource().users().get(userId);
    if (Objects.isNull(ur)) {
      throw new UserNotFoundException();
    }
    CredentialRepresentation c = new CredentialRepresentation();
    c.setType(CredentialRepresentation.PASSWORD);
    c.setValue(newPassword);
    ur.resetPassword(c);
  }
}
