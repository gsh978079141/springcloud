package com.gsh.springcloud.account.response;

import com.google.common.collect.Lists;
import com.gsh.springcloud.account.dto.ResourceDetailDto;
import com.gsh.springcloud.common.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceListResp extends BaseResponse {

  private List<ResourceDetailDto> list = Lists.newArrayList();


}
