package com.gsh.springcloud.gateway.domain;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  private String id;

  private String username;

  private String firstName;

  private String lastName;

  private String email;

  private Map<String, List<String>> clientRolesMap = Maps.newHashMap();

  private Map<String, String> attributes = Maps.newHashMap();

}
