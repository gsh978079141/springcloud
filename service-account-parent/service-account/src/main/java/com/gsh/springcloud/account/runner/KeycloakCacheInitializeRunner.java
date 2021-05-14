package com.gsh.springcloud.account.runner;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.gsh.springcloud.account.config.support.keycloak.KeycloakAdminProperties;
import com.gsh.springcloud.account.constant.AuthorizationConstants;
import com.gsh.springcloud.account.constant.ClientConstants;
import com.gsh.springcloud.account.service.KeycloakService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.ScopePermissionResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;
import org.keycloak.representations.idm.authorization.ScopePermissionRepresentation;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * keycloak cache初始化
 *
 * @author jun
 */
@Component
@Slf4j
public class KeycloakCacheInitializeRunner implements ApplicationRunner {


  @Resource
  KeycloakAdminProperties keycloakAdminProperties;

  @Resource
  Keycloak keycloakInstance;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    RealmResource rr = keycloakInstance.realm(keycloakAdminProperties.getManagedRealm());
    ClientsResource csr = rr.clients();
    Map<String, ClientRepresentation> cachedClientMap = csr.findAll(true).stream()
            .filter(item -> ClientConstants.CACHED_CLIENTS.contains(item.getClientId()))
            .map(item -> {
              log.info("cached client id is::::{}", item.getClientId());
              return item;
            })
            .collect(Collectors.toMap(ClientRepresentation::getClientId, Function.identity()));

    Map<String, ClientResource> cachedClientResourceMap = Maps.newHashMap();
    Map<String, Map<String, ResourceRepresentation>> cachedResourceMap = Maps.newHashMap();
    Map<String, Map<String, ScopePermissionResource>> cachedPermissionResourceMap = Maps.newHashMap();
    Map<String, List<ScopePermissionRepresentation>> cachedPermissionRepresentationMap = Maps.newHashMap();
    cachedClientMap.forEach((k, v) -> {
      ClientResource cr = csr.get(v.getId());
      if (Objects.nonNull(cr)) {
        log.info("current client id is:[{}]", cr.toRepresentation().getClientId());
        cr.roles().list().forEach(r -> {
          // TODO 获取role的attributes一直为null， 需要排查
          log.info("role, name:[{}], [{}], attributes is null :[{}]", r.getName(), JSON.toJSONString(r), r.getAttributes() == null);
        });

        cachedClientResourceMap.put(k, cr);
        cachedResourceMap.put(k,
                cr.authorization().resources().resources()
                        .stream().collect(Collectors.toMap(ResourceRepresentation::getId, Function.identity())));


        Map<String, ScopePermissionResource> prMap = Maps.newHashMap();
        cr.authorization().policies().policies().stream()
                .filter(i -> AuthorizationConstants.SCOPE.equals(i.getType()))
                .map(PolicyRepresentation::getId)
                .forEach(item -> {
                  prMap.put(item, cr.authorization().permissions().scope().findById(item));
                });


        cachedPermissionResourceMap.put(k, prMap);
        cachedPermissionRepresentationMap.put(k,
                prMap.values().stream()
                        .map(ScopePermissionResource::toRepresentation)
                        .map(item -> {
                          item.setResources(prMap.get(item.getId()).resources().stream()
                                  .map(ResourceRepresentation::getId)
                                  .collect(Collectors.toSet()));

                          item.setPolicies(prMap.get(item.getId()).associatedPolicies().stream()
                                  .map(PolicyRepresentation::getId)
                                  .collect(Collectors.toSet()));
                          return item;
                        })
                        .collect(Collectors.toList()));
      }
    });
    KeycloakService.initCache(rr, cachedClientResourceMap, cachedPermissionResourceMap, cachedResourceMap, cachedPermissionRepresentationMap);
    log.info("cached clients are:::::{}", cachedClientResourceMap.keySet());
  }
}
