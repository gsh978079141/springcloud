package com.gsh.springcloud.account.client;

import com.gsh.springcloud.common.vo.JsonResult;
import io.swagger.annotations.Api;
import org.keycloak.representations.idm.RealmRepresentation;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(url = "${feign.url.service-account}", path = "/realms", name = "realmClient")
@Api(tags = "Realm API")
public interface RealmClinet {

  JsonResult<List<RealmRepresentation>> findAllRealms();

  JsonResult<RealmRepresentation> findByName(String realmName);

}
