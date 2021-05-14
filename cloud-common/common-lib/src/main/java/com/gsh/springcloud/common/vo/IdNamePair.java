package com.gsh.springcloud.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @author gsh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdNamePair implements Serializable {

  private String id;

  private String name;

}
