package com.gsh.springcloud.user.config;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;


@Intercepts({@Signature(
        type = org.apache.ibatis.executor.Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class})})
public class UpdateOperationInterceptor implements Interceptor {

  private Properties properties;

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
    SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
    Object parameter = invocation.getArgs()[1];
    if (Objects.isNull(parameter)) {
      return invocation.proceed();
    }
    if (SqlCommandType.INSERT.equals(sqlCommandType)) {
      if (parameter instanceof DefaultSqlSession.StrictMap) {
        return invocation.proceed();
      } else {
        PropertyDescriptor createdTime = BeanUtils
                .getPropertyDescriptor(parameter.getClass(), "createdTime");
        if (Objects.nonNull(createdTime) && Objects.isNull(createdTime.getReadMethod().invoke(parameter))) {
          createdTime.getWriteMethod().invoke(parameter, new Date());
        }
      }
    } else if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
      PropertyDescriptor updatedTime = BeanUtils
              .getPropertyDescriptor(parameter.getClass(), "updatedTime");
      if (Objects.nonNull(updatedTime) && Objects.isNull(updatedTime.getReadMethod().invoke(parameter))) {
        updatedTime.getWriteMethod().invoke(parameter, new Date());
      }
    }
    return invocation.proceed();
  }

  @Override
  public Object plugin(Object target) {
    if (target instanceof org.apache.ibatis.executor.Executor) {
      return Plugin.wrap(target, this);
    }
    return target;
  }

  @Override
  public void setProperties(Properties properties) {
    this.properties = properties;
  }
}
