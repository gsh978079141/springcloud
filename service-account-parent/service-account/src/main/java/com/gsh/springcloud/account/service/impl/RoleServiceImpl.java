package com.gsh.springcloud.account.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.gsh.springcloud.account.constant.AuthDataChangeActionEnum;
import com.gsh.springcloud.account.constant.AuthorizationConstants;
import com.gsh.springcloud.account.constant.ClientConstants;
import com.gsh.springcloud.account.converter.PermissionConverter;
import com.gsh.springcloud.account.converter.RoleConverter;
import com.gsh.springcloud.account.dto.*;
import com.gsh.springcloud.account.exception.RoleNotFoundException;
import com.gsh.springcloud.account.keycloak.KeycloakCache;
import com.gsh.springcloud.account.keycloak.KeycloakHandlerService;
import com.gsh.springcloud.account.request.RoleCreateReq;
import com.gsh.springcloud.account.request.RoleUpdateReq;
import com.gsh.springcloud.account.response.*;
import com.gsh.springcloud.account.service.AuthChangePubService;
import com.gsh.springcloud.account.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.representations.idm.authorization.ScopePermissionRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

  @Resource
  private RoleConverter roleConverter;

  @Resource
  private KeycloakCache keycloakCache;

  @Autowired
  private KeycloakHandlerService keycloakHandlerService;

  @Resource
  private AuthChangePubService authChangePubService;

  @Resource
  PermissionConverter permissionConverter;

  @Override
  public ExistenceResp checkExistence(String clientId, String name) {
    if (StringUtils.isNotBlank(clientId)) {
      String policyName = AuthorizationConstants.POLICY_PREFIX.concat(name);
      Set<String> existingPolicies = keycloakHandlerService.getExistingPolicies(clientId);
      return new ExistenceResp(
              existingPolicies.stream().filter(item -> item.equals(policyName)).findFirst().isPresent(), "name", name);
    }
    ;

    return new ExistenceResp(true);
  }

  @Override
  public void create4client(String clientId, RoleCreateReq req) {
    keycloakHandlerService.createRoleAndRolePolicy4Client(clientId, req);
    RoleDto role = keycloakHandlerService.getRoleByName(clientId, req.getName());
    keycloakCache.updateRole(clientId, role);

    updateBindPermissions(clientId, role.getName(), req.getPermissionIds());
  }

  @Override
  public RoleListResp listAll4client(String clientId) {
    List<RoleDto> list = keycloakCache.listAllRoles(clientId);
    return new RoleListResp(list);
  }

  @Override
  public void update4client(String clientId, String id, RoleUpdateReq req) {
    RoleDto dto = getRoleDtoById(clientId, id);
    dto.setDescription(req.getDescription());
    keycloakHandlerService.updateRole(clientId, dto);
    RoleDto role = getRoleDtoById(clientId, id);
    role.setDescription(req.getDescription());
    keycloakCache.updateRole(clientId, role);

    updateBindPermissions(clientId, role.getName(), req.getPermissionIds());
  }

  @Override
  public void delete4client(String clientId, String id) {
    RoleDto dto = getRoleDtoById(clientId, id);
    keycloakHandlerService.deleteRole(clientId, dto);
    pubRoleDataChange(clientId, AuthDataChangeActionEnum.DELETE_ROLE, dto.getName(), Lists.newArrayList());
    //delete from role&permission cache
    keycloakCache.deleteRole(clientId, dto);
  }

  @Override
  public RoleDetailResp detail4client(String clientId, String id) {
    RoleDto roleDto = getRoleDtoById(clientId, id);
    if (log.isDebugEnabled()) {
      log.debug("role attributes:{}", JSON.toJSONString(roleDto.getAttributes()));
    }
    return buildRoleDetailResp(clientId, roleDto);
  }

  private RoleDetailResp buildRoleDetailResp(String clientId, RoleDto roleDto) {
    Set roleSet = Sets.newHashSet();
    roleSet.add(roleDto.getName());
    List<PermissionDto> permissions = keycloakCache.listPermissionsOfRoles(clientId, roleSet);
    RoleDetailResp resp = roleConverter.dto2detailResp(roleDto);
    resp.setAssociatedPermissions(permissions);
    return resp;
  }

  @Override
  public RoleDetailResp detail4clientByName(String clientId, String name) {
    RoleDto roleDto = getRoleDtoById(clientId, name);
    return buildRoleDetailResp(clientId, roleDto);
  }

  @Override
  public RoleResp getByName(String clientId, String name) {
    List<RoleDto> roleDtos = keycloakCache.listAllRoles(clientId);
    boolean existIncache = roleDtos.stream().filter(item -> item.getName().equals(name)).findFirst().isPresent();
    RoleDto roleDto;
    if (existIncache) {
      roleDto = roleDtos.stream().filter(item -> item.getName().equals(name)).findFirst().get();
    } else {
      roleDto = keycloakHandlerService.getRoleByName(clientId, name);
    }
    if (Objects.isNull(roleDto)) {
      throw new RoleNotFoundException(name);
    }
    return roleConverter.dto2resp(roleDto);
  }

  @Override
  public List<RoleDto> listByClientId(String clientId) {
    return keycloakCache.listAllRoles(clientId);
  }

  @Override
  public RolePermissionsDto getRolePermissionsById(String clientId, String id) {
    RoleDto roleDto = getRoleDtoById(clientId, id);
    if (log.isDebugEnabled()) {
      log.debug("role attributes:{}", JSON.toJSONString(roleDto.getAttributes()));
    }
    Set<String> roleSet = Sets.newHashSet();
    roleSet.add(roleDto.getName());
    List<PermissionDto> permissions = keycloakCache.listPermissionsOfRoles(clientId, roleSet);
    RolePermissionsDto result = roleConverter.dto2detail(roleDto);
    result.setAssociatedPermissions(permissions);
    return result;
  }

  @Override
  public RoleExistDto existInClient(String clientId, String name) {
    if (StringUtils.isNotBlank(clientId)) {
      String policyName = AuthorizationConstants.POLICY_PREFIX.concat(name);
      Set<String> existingPolicies = keycloakHandlerService.getExistingPolicies(clientId);
      return RoleExistDto.builder().exist(existingPolicies.stream().anyMatch(item -> item.equals(policyName)))
              .fieldName("name").value(name).build();
    }
    return RoleExistDto.builder().exist(true).build();
  }

  @Override
  public void create(RoleUpdateDto dto) {
    String clientId = dto.getClientId();
    keycloakHandlerService.createRoleAndRolePolicy4Client(clientId, roleConverter.dto2req(dto));
    RoleDto role = keycloakHandlerService.getRoleByName(clientId, dto.getName());
    keycloakCache.updateRole(clientId, role);
    updateBindPermissions(clientId, role.getName(), dto.getPermissionIds());
  }

  @Override
  public void update(RoleUpdateDto req) {
    String clientId = req.getClientId();
    RoleDto dto = getRoleDtoById(clientId, req.getId());
    dto.setDescription(dto.getDescription());
    keycloakHandlerService.updateRole(clientId, dto);
    RoleDto role = getRoleDtoById(clientId, dto.getId());
    role.setDescription(req.getDescription());
    keycloakCache.updateRole(clientId, role);
    updateBindPermissions(clientId, role.getName(), req.getPermissionIds());
  }

  @Override
  public void delete(String clientId, String id) {
    RoleDto dto = getRoleDtoById(clientId, id);
    keycloakHandlerService.deleteRole(clientId, dto);
    pubRoleDataChange(clientId, AuthDataChangeActionEnum.DELETE_ROLE, dto.getName(), Lists.newArrayList());
    keycloakCache.deleteRole(clientId, dto);
  }

  @Override
  public RoleInUseDto checkUsing(String clientId, String id) {
    RoleDto role = getRoleDtoById(clientId, id);
    boolean inUse = keycloakHandlerService.checkRoleInUse(clientId, role.getName());
    return RoleInUseDto.builder().inUse(inUse).name(role.getName()).build();
  }

  @Override
  public RoleResp getById(String clientId, String id) {
    return roleConverter.dto2resp(getRoleDtoById(clientId, id));
  }

  private RoleDto getRoleDtoById(String clientId, String roleId) {
    List<RoleDto> roles = keycloakCache.listAllRoles(clientId);
    return roles.stream().filter(item -> item.getId().equals(roleId)).findFirst()
            .orElseThrow(() -> new RoleNotFoundException(roleId));
  }

  private void updateBindPermissions(String clientId, String roleName, List<String> permissionIds) {
    List<PermissionDto> permissions = keycloakCache.listAllPermissions(clientId);
    List<RoleDto> roleDtoList = keycloakCache.listRolesByNames(clientId, Sets.newHashSet(roleName));
    if (CollectionUtils.isNotEmpty(roleDtoList)) {
      RoleDto roleDto = roleDtoList.get(0);

      RoleDetailResp resp = buildRoleDetailResp(ClientConstants.ID_MANAGEMENT, roleDto);
      List<String> oldPermissionIds = resp.getAssociatedPermissions()
              .stream().map(PermissionDto::getId).collect(Collectors.toList());
      List<String> bindPermissionIds = Lists.newArrayList(permissionIds);
      List<String> unbindPermissionIds = Lists.newArrayList(oldPermissionIds);
      //找出此次新增的权限
      bindPermissionIds.removeAll(oldPermissionIds);
      //找出此次去除的权限
      unbindPermissionIds.removeAll(permissionIds);
      if (CollectionUtils.isNotEmpty(bindPermissionIds)) {
        List<ScopePermissionRepresentation> bindPermissionRepresentations = permissionConverter.dto2entity(permissions).stream()
                .filter(item -> bindPermissionIds.contains(item.getId()))
                .collect(Collectors.toList());
        List<String> pList = keycloakHandlerService.bindingRoleAndPermissions(clientId, roleDto, bindPermissionRepresentations);
        log.info("{} roleName: {} binding permissionName :{},binding permissionIds :{} ", "OPR", roleDto.getName(), pList, bindPermissionIds);
      }
      if (CollectionUtils.isNotEmpty(unbindPermissionIds)) {
        List<ScopePermissionRepresentation> unbindPermissionRepresentations = permissionConverter.dto2entity(permissions).stream()
                .filter(item -> unbindPermissionIds.contains(item.getId()))
                .collect(Collectors.toList());
        keycloakHandlerService.unbindingRoleAndPermissions(clientId, roleDto, unbindPermissionRepresentations);
      }
      List<String> newPermissionNames = permissions.stream().filter(item -> permissionIds.contains(item.getId())).map(PermissionDto::getName).collect(Collectors.toList());
      // send role and permisson change message to rabbitmq
      pubRoleDataChange(clientId, AuthDataChangeActionEnum.UPDATE_ROLE, roleDto.getName(), newPermissionNames);
      //add or update role&permissons to cache
      keycloakCache.updateRolePermissions(clientId, roleDto.getName(), new HashSet<>(newPermissionNames));
    }
  }

  @Override
  public RoleInUseResp checkInUse(String clientId, String id) {
    RoleDto role = getRoleDtoById(clientId, id);
    boolean inUse = keycloakHandlerService.checkRoleInUse(clientId, role.getName());
    return new RoleInUseResp(inUse, role.getName());
  }

  private void pubRoleDataChange(String clientId, AuthDataChangeActionEnum action, String roleName, List<String> permissions) {
    RoleChangeDto roleChangeDto = RoleChangeDto.builder().
            roleName(roleName)
            .permissions(permissions)
            .build();
    authChangePubService.pubRoleDataChange(clientId, action, roleChangeDto);

  }

}
