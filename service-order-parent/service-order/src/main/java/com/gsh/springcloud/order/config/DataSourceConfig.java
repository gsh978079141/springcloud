//package com.gsh.springcloud.order.config;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import io.seata.rm.datasource.DataSourceProxy;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//
///**
// * 数据源配置
// *
// * @author HelloWoodes
// */
//@Configuration
//public class DataSourceConfig {
//
//  //  @Bean
////  @ConfigurationProperties(prefix = "spring.datasource")
////  public DruidDataSource druidDataSource() {
////    return new DruidDataSource();
////  }
//  @Bean
//  public HikariDataSource hikariDataSource() {
//    HikariConfig hikariConfig = new HikariConfig();
//    hikariConfig.setUsername("root");
//    hikariConfig.setPassword("123");
//    hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/springcloud?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC");
//    hikariConfig.setPoolName("Hikari");
//    hikariConfig.setAutoCommit(false);
//    HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
//    return hikariDataSource;
//  }
//
//  /**
//   * 需要将 DataSourceProxy 设置为主数据源，否则事务无法回滚
//   *
//   * @return The default datasource
//   */
//  @Primary
//  @Bean("dataSource")
//  public DataSource dataSource(HikariDataSource hikariDataSource) {
//    return new DataSourceProxy(hikariDataSource);
//  }
//
////  @Primary
////  @Bean
////  public DataSourceProxy dataSourceProxy(DruidDataSource druidDataSource) {
////    return new DataSourceProxy(druidDataSource);
////  }
//
//}
