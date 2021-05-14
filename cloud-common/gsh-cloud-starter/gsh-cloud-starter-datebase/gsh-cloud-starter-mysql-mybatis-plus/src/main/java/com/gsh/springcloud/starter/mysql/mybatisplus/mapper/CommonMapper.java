package com.gsh.springcloud.starter.mysql.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author gsh
 */
public interface CommonMapper<T> extends BaseMapper<T> {

  int insertBatchSomeColumn(List<T> list);

}
