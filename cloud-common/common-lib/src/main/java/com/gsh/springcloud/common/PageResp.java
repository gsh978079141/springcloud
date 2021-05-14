package com.gsh.springcloud.common;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class PageResp<T> extends BaseResponse {

  private Integer pageNum;

  private Integer pageSize;

  private Long total;

  private Integer pages;

  private List<T> list;

  public PageResp() {

  }

  public PageResp(PageInfo<T> pageInfo) {
    this.setCode(ResultCodeConstants.SUCCESS);
    this.setList(pageInfo.getList());
    this.setPageNum(pageInfo.getPageNum());
    this.setPageSize(pageInfo.getPageSize());
    this.setPages(pageInfo.getPages());
    this.setTotal(pageInfo.getTotal());
  }

}
