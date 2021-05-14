package com.gsh.springcloud.common.vo;

import com.gsh.springcloud.common.consts.PageConst;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * @author gsh
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PageQuery {

  @ApiModelProperty(value = "指定页数, 默认值1")
  @Builder.Default
  private Integer pageNum = PageConst.DFT_PAGE_NUM;

  @ApiModelProperty(value = "每页显示的记录数, 默认值1000")
  @Builder.Default
  private Integer pageSize = PageConst.DFT_PAGE_SIZE;

}
