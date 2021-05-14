package com.gsh.springcloud.account.response;

import com.google.common.collect.Maps;
import com.gsh.springcloud.account.dto.ResourceDto;
import com.gsh.springcloud.common.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResourceMapResp extends BaseResponse {

  private Map<String, List<ResourceDto>> map = Maps.newHashMap();


}
