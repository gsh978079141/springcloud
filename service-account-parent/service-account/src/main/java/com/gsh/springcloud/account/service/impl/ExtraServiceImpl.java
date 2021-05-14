package com.gsh.springcloud.account.service.impl;

import com.google.common.collect.Maps;
import com.gsh.springcloud.account.constant.AuthorizationConstants;
import com.gsh.springcloud.account.constant.ClientConstants;
import com.gsh.springcloud.account.dto.ClientAuthorizationDataDto;
import com.gsh.springcloud.account.exception.ClientNotFoundException;
import com.gsh.springcloud.account.keycloak.KeycloakHandlerService;
import com.gsh.springcloud.account.service.ExtraService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.ScopePermissionResource;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;
import org.keycloak.representations.idm.authorization.RolePolicyRepresentation;
import org.keycloak.representations.idm.authorization.ScopePermissionRepresentation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: EDU_DBP_APP
 * @description:
 * @author: Gsh
 * @create: 2020-09-12 09:37
 **/
@Service
@Slf4j
public class ExtraServiceImpl implements ExtraService {

  @Resource
  KeycloakHandlerService keycloakHandlerService;


  @Override
  public ClientAuthorizationDataDto exportAuthorizationData(String realmName, String clientId, Set<String> targetRoles) {
    log.info("load client data, realmName:{}, clientId: {}", realmName, clientId);
    if (!ClientConstants.CACHED_CLIENTS.contains(clientId)) {
      throw new ClientNotFoundException(clientId);
    }
    return keycloakHandlerService.getClientData(realmName, clientId, targetRoles);
  }


  @Override
  public void importAuthorizationData(ClientAuthorizationDataDto data) {
    keycloakHandlerService.importClientAuthorizationData(data);
  }

  public void bindPermissions(ClientResource clientResource, List<ScopePermissionRepresentation> scopePermissionRepresentations, PolicyRepresentation policyRepresentation) {
    Map<String, ScopePermissionRepresentation> permMap = scopePermissionRepresentations.stream().collect(Collectors.toMap(ScopePermissionRepresentation::getId, o -> o));
    this.getPermissionResourceMap(clientResource).forEach((k, v) -> {
      List<PolicyRepresentation> associatedPolicies = v.associatedPolicies();
      associatedPolicies.add(policyRepresentation);
      ScopePermissionRepresentation representation = permMap.get(k);
      representation.setPolicies(associatedPolicies.stream().map(PolicyRepresentation::getId).collect(Collectors.toSet()));
      v.update(representation);
    });
  }

  public List<ScopePermissionRepresentation> getAllScopePermissions(ClientResource clientResource) {

    // 按照名称查找permission
//    clientResource.authorization().permissions().scope().findByName()
    // 查找所有scope
//    clientResource.authorization().scopes().scopes();
//    List<ResourceRepresentation> resourceRepresentations = clientResource.authorization().resources().resources();

    Map<String, ScopePermissionResource> prMap = this.getPermissionResourceMap(clientResource);
    List<ScopePermissionRepresentation> scopePermissions = prMap.values().stream()
            .map(ScopePermissionResource::toRepresentation)
            .map(item -> {
              item.setResources(prMap.get(item.getId()).resources().stream()
                      .map(ResourceRepresentation::getId)
                      .collect(Collectors.toSet()));
              return item;
            })
            .collect(Collectors.toList());
    return scopePermissions;
  }


  public Map<String, ScopePermissionResource> getPermissionResourceMap(ClientResource clientResource) {
    Map<String, ScopePermissionResource> prMap = Maps.newHashMap();
    clientResource.authorization().policies().policies().stream()
            .filter(i -> AuthorizationConstants.SCOPE.equals(i.getType()))
            .map(PolicyRepresentation::getId)
            .forEach(item -> {
              prMap.put(item, clientResource.authorization().permissions().scope().findById(item));
            });
    return prMap;
  }

  public void createRolePolicy(ClientResource clientResource, String roleName) {
    String clientId = clientResource.toRepresentation().getId();
    RolePolicyRepresentation rpr = new RolePolicyRepresentation();
    rpr.setName(AuthorizationConstants.POLICY_PREFIX.concat(roleName));
    rpr.addClientRole(clientId, roleName);
    clientResource.authorization().policies().role().create(rpr);
  }


//
//  @Override
//  public void createResource(ResourceCreateReq resourceCreateReq, String clientId) {
//    ClientResource cr = clientResource(clientId);
//
//    List<ResourceRepresentation> resourceRepresentations = cr.authorization().resources().findByName(resourceCreateReq.getName());
//
//    if (resourceRepresentations != null && resourceRepresentations.size() > 0) {
//      log.info(" resource exits,not create....");
//      return;
//    }
//
//    ResourceRepresentation resourceRepresentation = new ResourceRepresentation();
//    BeanUtils.copyProperties(resourceCreateReq, resourceRepresentation);
//
//    Set<ScopeRepresentation> scopes = new HashSet<>();
//
//    for (String scope : resourceCreateReq.getScopes()) {
//      ScopeRepresentation scopeRepresentation = new ScopeRepresentation();
//      scopeRepresentation.setName(scope);
//      scopes.add(scopeRepresentation);
//    }
//    resourceRepresentation.setScopes(scopes);
//
//    cr.authorization().resources().create(resourceRepresentation);
//
//
//  }

//  @Override
//  public void createPolicie(PolicieCreateReq policieCreateReq, String clientId) {
//
//    RolePolicyRepresentation rolePolicyRepresentationF = clientResource(clientId).authorization().policies().role().findByName(policieCreateReq.getName());
//    if (rolePolicyRepresentationF != null) {
//      log.info("createPolicie skip ,policy name {} exited ", policieCreateReq.getName());
//      return;
//    }
//
//    RolePolicyRepresentation rolePolicyRepresentation = new RolePolicyRepresentation();
//    BeanUtils.copyProperties(policieCreateReq, rolePolicyRepresentation);
//
//    Set<RolePolicyRepresentation.RoleDefinition> roles = new HashSet<>();
//
//    for (String role : policieCreateReq.getRoles()) {
//
//      RolePolicyRepresentation.RoleDefinition roleDefinition = new RolePolicyRepresentation.RoleDefinition();
//      roleDefinition.setId(role);
//      roleDefinition.setRequired(false);
//      roles.add(roleDefinition);
//    }
//    rolePolicyRepresentation.setRoles(roles);
//
//    clientResource(clientId).authorization().policies().role().create(rolePolicyRepresentation);
//
//  }


//
//  @Override
//  public void createPermission(PermissionCreateReq permissionCreateReq, String clientId) {
//
//    try {
//      String id = clientResource(clientId).authorization().permissions().resource().findByName(permissionCreateReq.getName()).getId();
//      if (StringUtils.isNotEmpty(id)) {
//        log.info("---- perm {} exist ,skip ", permissionCreateReq.getName());
//        return;
//      }
//    } catch (Exception e) {
//
//      log.error("createPermission get perm error reateReq.getName() {}", permissionCreateReq.getName());
//    }
//
//    ScopePermissionRepresentation scopePermissionRepresentation = new ScopePermissionRepresentation();
//    BeanUtils.copyProperties(permissionCreateReq, scopePermissionRepresentation);
//    Response response = clientResource(clientId).authorization().permissions().scope().create(scopePermissionRepresentation);
//    log.debug("createPermission response {} ", response.getStatus());
//
//  }
//
//
//  @Override
//  public synchronized void importResources(Map map, String clientId) {
//
//    log.info("importResources client is {} map is {}", clientId, map);
//
//    JSONObject itemJSONObj = JSONObject.parseObject(JSON.toJSONString(map));
//    JSONArray resourceArray = itemJSONObj.getJSONArray("resources");
//
//    log.info("importResources begin resource size {}", resourceArray.size());
//
//    for (int i = 0; i < resourceArray.size(); i++) {
//      if (1 == 1) {
////                continue;
//      }
//      JSONObject resourceObject = resourceArray.getJSONObject(i);
//      log.info("importResources resource name begin  {}", resourceObject.getString("name"));
//      ResourceCreateReq resourceCreateReq = new ResourceCreateReq();
//      resourceCreateReq.setName(resourceObject.getString("name"));
//      resourceCreateReq.setDisplayName(resourceObject.getString("displayName"));
//
//      JSONArray urisArray = resourceObject.getJSONArray("uris");
//      Set<String> urisSet = new HashSet<>();
//
//      for (int n = 0; n < urisArray.size(); n++) {
//        urisSet.add(urisArray.get(n).toString());
//      }
//
//      resourceCreateReq.setUris(urisSet);
//      JSONArray scopeArray = resourceObject.getJSONArray("scopes");
//
//      Set<String> scopeSet = new HashSet<>();
//      if (scopeArray == null) {
//        continue;
//      }
//
//      for (int j = 0; j < scopeArray.size(); j++) {
//        scopeSet.add(scopeArray.getJSONObject(j).getString("name"));
//      }
//
//      resourceCreateReq.setScopes(scopeSet);
//      Map<String, List<String>> attributesMap = JSONObject.toJavaObject(resourceObject.getJSONObject("attributes"), Map.class);
//      resourceCreateReq.setAttributes(attributesMap);
//      log.info("resourceCreateReq {} ", resourceCreateReq.toString());
//
//      createResource(resourceCreateReq, clientId);
//    }
//
//    log.info("resource Create end....");
//
//    JSONArray policeArray = itemJSONObj.getJSONArray("policies");
//
//    log.info("Police Create Begin....");
//
//    for (int m = 0; m < policeArray.size(); m++) {
//
//      if (1 == 1) {
////                continue;
//      }
//
//      JSONObject policeObject = policeArray.getJSONObject(m);
//      JSONObject configObject = policeObject.getJSONObject("config");
//      if (configObject.containsKey("resources")) {
//        //说明是pem 数据
//        continue;
//      }
//      PolicieCreateReq policieCreateReq = new PolicieCreateReq();
//      policieCreateReq.setDecisionStrategy(policeObject.getString("decisionStrategy"));
//      policieCreateReq.setLogic(policeObject.getString("logic"));
//      policieCreateReq.setType(policeObject.getString("type"));
//      policieCreateReq.setName(policeObject.getString("name"));
//
//      JSONArray rolesArray = configObject.getJSONArray("roles");
//      if (rolesArray == null) {
//        log.info("rolesArray is null ,config {} ", configObject.toJSONString());
//        continue;
//      }
//      Set<String> rolesSet = new HashSet<>();
//      for (int p = 0; p < rolesArray.size(); p++) {
//        JSONObject rolesObject = rolesArray.getJSONObject(p);
//
//        String roleId = rolesObject.getString("id");
//        if (roleId.contains("/")) {
//          String[] idArray = roleId.split("/");
//
//          log.info("create role name {} clients {}", idArray[1], idArray[0]);
//          RoleCreateReq roleCreateReq = new RoleCreateReq();
//          //临时修改
//          roleCreateReq.setName("1_" + idArray[1]);
//          roleService.create4client(idArray[0], roleCreateReq);
//
//          rolesSet.add(roleId);
//        } else {
//          continue;
//        }
//      }
//
//      policieCreateReq.setRoles(rolesSet);
//
//      log.info("policieCreateReq bean {} ", policieCreateReq);
//
//      createPolicie(policieCreateReq, clientId);
//
//    }
//
//    log.info("Police Create End....");
//    log.info("Perm Create Begin....");
//
//    ///////perm begin
//    for (int m = 0; m < policeArray.size(); m++) {
//
//      JSONObject policeObject = policeArray.getJSONObject(m);
//      JSONObject configObject = policeObject.getJSONObject("config");
//      if (configObject.containsKey("roles")) {
//        //说明是pol 数据
//        continue;
//      }
//
//      PermissionCreateReq permissionCreateReq = new PermissionCreateReq();
//      permissionCreateReq.setName(policeObject.getString("name"));
//      permissionCreateReq.setDescription(policeObject.getString("description"));
//      JSONArray resourcePermArray = configObject.getJSONArray("resources");
//
//      Set<String> resourcePermSet = new HashSet<>();
//
//      if (resourcePermArray == null) {
//        log.info("resourcePermArray is null ,config {} ", configObject.toJSONString());
//        continue;
//      }
//
//      for (int p = 0; p < resourcePermArray.size(); p++) {
//        resourcePermSet.add(resourcePermArray.getString(p));
//      }
//      permissionCreateReq.setResources(resourcePermSet);
//
//
//      JSONArray scopsPermArray = configObject.getJSONArray("scopes");
//
//      Set<String> scopsPermSet = new HashSet<>();
//      for (int p = 0; p < scopsPermArray.size(); p++) {
//        scopsPermSet.add(scopsPermArray.getString(p));
//      }
//      permissionCreateReq.setScopes(scopsPermSet);
//
//      JSONArray applyPoliciesPermArray = configObject.getJSONArray("applyPolicies");
//
//      Set<String> applyPoliciesPermSet = new HashSet<>();
//      for (int p = 0; p < applyPoliciesPermArray.size(); p++) {
//        applyPoliciesPermSet.add(applyPoliciesPermArray.getString(p));
//      }
//      permissionCreateReq.setPolicies(applyPoliciesPermSet);
//
//      log.info("permissionCreateReq bean {} ", permissionCreateReq);
//
//      try {
//        Thread.sleep(100);
//      } catch (Exception e) {
//        e.printStackTrace();
//      }
//      createPermission(permissionCreateReq, clientId);
//    }
//    log.info("Perm Create End....");
//    log.info("........ ALL DONE.....");
//
//
//  }
}
