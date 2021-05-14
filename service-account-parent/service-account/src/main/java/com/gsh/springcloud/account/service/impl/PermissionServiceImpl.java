package com.gsh.springcloud.account.service.impl;

import com.gsh.springcloud.account.converter.PermissionConverter;
import com.gsh.springcloud.account.dto.PermissionDto;
import com.gsh.springcloud.account.exception.RoleNotFoundException;
import com.gsh.springcloud.account.helper.KeycloakHelper;
import com.gsh.springcloud.account.keycloak.KeycloakCache;
import com.gsh.springcloud.account.request.PermissionRoleBindReq;
import com.gsh.springcloud.account.response.PermissionListResp;
import com.gsh.springcloud.account.service.KeycloakService;
import com.gsh.springcloud.account.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.ScopePermissionResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;
import org.keycloak.representations.idm.authorization.ScopePermissionRepresentation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PermissionServiceImpl extends KeycloakService implements PermissionService {

  @Resource
  PermissionConverter permissionConverter;

  @Resource
  KeycloakHelper keycloakHelper;

  @Resource
  KeycloakCache keycloakCache;

  @Override
  public List<PermissionDto> listPermissions(String clientId) {
    return keycloakCache.listAllPermissions(clientId);
  }

  @Override
  public void bindRoles(String clientId, String permissionId, PermissionRoleBindReq req) {
    // load all role from request
    List<RoleRepresentation> roles = keycloakHelper.listAllRolesFromRequest(clientId, req);
    // load all policies
    Map<String, PolicyRepresentation> policyMap = keycloakHelper.loadRolePolicyMap(clientId);
    // associate policies 2 permission
    ScopePermissionResource permissionResource = super.loadPermRes(clientId, permissionId);
    List<PolicyRepresentation> policies = permissionResource.associatedPolicies();
    policies.addAll(
            roles.stream()
                    .filter(item -> policyMap.containsKey(item.getId()))
                    .map(item -> policyMap.get(item.getId()))
                    .collect(Collectors.toList())
    );
    ScopePermissionRepresentation representation = permissionResource.toRepresentation();
    representation.setPolicies(policies.stream().map(PolicyRepresentation::getId).collect(Collectors.toSet()));
    permissionResource.update(representation);
  }

  @Override
  public void unbindRoles(String clientId, String permissionId, PermissionRoleBindReq req) {
    // load all role from request
    List<RoleRepresentation> roles = keycloakHelper.listAllRolesFromRequest(clientId, req);
    // load all policies
    Map<String, PolicyRepresentation> policyMap = keycloakHelper.loadRolePolicyMap(clientId);
    ScopePermissionResource permissionResource = super.loadPermRes(clientId, permissionId);
    List<PolicyRepresentation> associatedPolicies = permissionResource.associatedPolicies();
    List<PolicyRepresentation> unbindPolicies = roles.stream()
            .filter(item -> policyMap.containsKey(item.getId()))
            .map(item -> policyMap.get(item.getId()))
            .collect(Collectors.toList());
    associatedPolicies.removeAll(unbindPolicies);
    ScopePermissionRepresentation representation = permissionResource.toRepresentation();
    representation.setPolicies(associatedPolicies.stream().map(PolicyRepresentation::getId).collect(Collectors.toSet()));
    permissionResource.update(representation);
  }

  @Override
  public PermissionListResp listPermissionsOfUser(String userId, String clientId) {
    Set<String> roles = keycloakHelper.listClientRolesOfUser(userId, clientId);
    List<ScopePermissionRepresentation> permissions = keycloakHelper.listPermissionsOfRoleByRoleNames(clientId, roles.toArray(new String[0]));
    List<PermissionDto> permissionDtoList = permissionConverter.entity2dto(permissions);
    return new PermissionListResp(permissionDtoList);
  }

  @Override
  public PermissionListResp listPermissionsOfRole(String roleId, String clientId) {
    RoleRepresentation roleRepresentation = clientResource(clientId).roles().list().stream()
            .filter(item -> item.getId().equals(roleId))
            .findFirst()
            .orElseThrow(() -> new RoleNotFoundException(roleId));
    List<PermissionDto> list = permissionConverter.entity2dto(
            keycloakHelper.listPermissionsOfRoleByRoleNames(clientId, roleRepresentation.getName()));
    return new PermissionListResp(list);
  }

  @Override
  public PermissionListResp listPermissionsOfRoleByInit(String roleId, String clientId) {
    RoleRepresentation roleRepresentation = clientResource(clientId).roles().list().stream()
            .filter(item -> item.getId().equals(roleId))
            .findFirst()
            .orElseThrow(() -> new RoleNotFoundException(roleId));
    List<PermissionDto> list = permissionConverter.entity2dto(
            keycloakHelper.listPermissionsOfRoleByInit(clientId, roleRepresentation.getName()));
    return new PermissionListResp(list);
  }

}
