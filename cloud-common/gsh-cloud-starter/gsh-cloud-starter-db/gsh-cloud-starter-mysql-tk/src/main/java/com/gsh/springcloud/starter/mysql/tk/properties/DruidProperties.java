package com.gsh.springcloud.starter.mysql.tk.properties;


//@Data
//@ConfigurationProperties(DruidProperties.PREFIX)
public class DruidProperties {

    public static final String PREFIX = "spring.datasource";

    private String url;

    private String username;

    private String password;

    private String driverClassName;

    private int initialSize;

    private int minIdle;

    private int maxActive;

    private long maxWait;

    private long timeBetweenEvictionRunsMillis;

    private long minEvictableIdleTimeMillis;

    private String validationQuery;

    private boolean testWhileIdle;

    private boolean testOnBorrow;

    private boolean testOnReturn;

    private boolean poolPreparedStatements;

    private String filters;
}

