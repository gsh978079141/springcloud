package com.gsh.springcloud.account.response;

import com.gsh.springcloud.account.dto.PermissionDto;
import com.gsh.springcloud.account.dto.ResourceDto;
import com.gsh.springcloud.account.dto.RoleDto;
import com.gsh.springcloud.common.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ExportAuthorizationResp extends BaseResponse {


  private List<ResourceDto> resources;

  private List<PermissionDto> permissions;

  private RoleDto adminRole;

  private PolicyRepresentation adminRolePolicy;

}
