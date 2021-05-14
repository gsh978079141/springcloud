package com.gsh.springcloud.starter.mysql.tk.tk;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author gsh
 */
public interface CommonMapper<T> extends Mapper<T>, MySqlMapper<T> {


}
