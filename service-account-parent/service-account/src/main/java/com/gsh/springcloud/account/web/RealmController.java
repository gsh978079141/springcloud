package com.gsh.springcloud.account.web;

import com.gsh.springcloud.account.client.RealmClinet;
import com.gsh.springcloud.common.vo.JsonResult;
import org.keycloak.representations.idm.RealmRepresentation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/realms")
public class RealmController implements RealmClinet {
  @Override
  public JsonResult<List<RealmRepresentation>> findAllRealms() {

    return null;
  }

  @Override
  public JsonResult<RealmRepresentation> findByName(String realmName) {
    return null;
  }
}
