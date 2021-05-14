package com.gsh.springcloud.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author gsh
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {

  @Builder.Default
  private String code = ResultCodeConstants.SUCCESS;

  @Builder.Default
  private String message = ResultCodeConstants.SUCCESS;

  public boolean isSuccess() {
    return ResultCodeConstants.SUCCESS.equals(code);
  }

}
