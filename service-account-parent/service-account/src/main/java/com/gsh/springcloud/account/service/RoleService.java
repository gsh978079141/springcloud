package com.gsh.springcloud.account.service;


import com.gsh.springcloud.account.dto.*;
import com.gsh.springcloud.account.request.RoleCreateReq;
import com.gsh.springcloud.account.request.RoleUpdateReq;
import com.gsh.springcloud.account.response.*;

import java.util.List;

public interface RoleService {
  @Deprecated
  ExistenceResp checkExistence(String clientId, String name);

  @Deprecated
  void create4client(String clientId, RoleCreateReq req);

  @Deprecated
  RoleListResp listAll4client(String clientId);

  @Deprecated
  void update4client(String clientId, String id, RoleUpdateReq req);

  @Deprecated
  void delete4client(String clientId, String id);

  RoleDetailResp detail4client(String clientId, String id);

  RoleResp getById(String clientId, String id);

  @Deprecated
  RoleInUseResp checkInUse(String clientId, String id);

  RoleDetailResp detail4clientByName(String clientId, String name);

  RoleResp getByName(String clientId, String name);

  List<RoleDto> listByClientId(String clientId);

  RolePermissionsDto getRolePermissionsById(String clientId, String id);

  RoleExistDto existInClient(String clientId, String name);

  void create(RoleUpdateDto dto);

  void update(RoleUpdateDto dto);

  void delete(String clientId, String id);

  RoleInUseDto checkUsing(String clientId, String id);
}
