package com.gsh.springcloud.common.vo;

import lombok.Data;

/**
 * @author gsh
 */
@Data
public class UploadVO {

  private String fileKey;
  private String uploadToken;
  private String accessURL;

}
