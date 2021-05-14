//package com.gsh.springcloud.common.utils;
//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.core.toolkit.StringPool;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.InjectionConfig;
//import com.baomidou.mybatisplus.generator.config.*;
//import com.baomidou.mybatisplus.generator.config.po.TableInfo;
//import com.baomidou.mybatisplus.generator.config.rules.DateType;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//public class MybatisAutoGenerator {
//
//  private static final String DRIVER = "com.mysql.jdbc.Driver";
//  private static final String URL = "jdbc:mysql://localhost:3306/springcloud?useUnicode=true&characterEncoding=utf8";
//  private static final String SCHEMANAME = "springcloud";
//  private static final String USERNAME = "root";
//  private static final String PASSWORD = "123";
//  /**
//   * 要生成的项目包的根路径
//   */
//  private static final String PARENT = "com.gsh.springcloud.order";
//  /**
//   * 要执行的项目
//   */
//  private static final String EXEC_PROJECT_PATH = "\\service-order";
//  private static final String ENTIT_YPATH = "\\cloud-common";
//
//  public static void main(String[] args) {
//    // 代码生成器
//    AutoGenerator mpg = new AutoGenerator();
//    // 全局配置
//    GlobalConfig gc = new GlobalConfig();
//    String projectPath = System.getProperty("user.dir");
//    gc.setOutputDir(projectPath + EXEC_PROJECT_PATH + "\\src\\main\\java");
//    gc.setAuthor("gsh");
//    gc.setOpen(false);
//    //每次运行进行覆盖操作
//    gc.setFileOverride(true);
//    //java mapper类命名后缀
//    gc.setMapperName("%sDao");
//    //mysql timestamp由java.util.Date类型映射
//    gc.setDateType(DateType.ONLY_DATE);
//
//    // gc.setSwagger2(true); 实体属性 Swagger2 注解
//    mpg.setGlobalConfig(gc);
//
//    // 数据源配置
//    DataSourceConfig dsc = new DataSourceConfig();
//    dsc.setUrl(URL);
//    // dsc.setSchemaName("public");
//    dsc.setDriverName(DRIVER);
//    dsc.setUsername(USERNAME);
//    dsc.setPassword(PASSWORD);
//    dsc.setSchemaName(SCHEMANAME);
//    dsc.setDbType(DbType.MYSQL);
//    mpg.setDataSource(dsc);
//
//    // 包配置
//    PackageConfig pc = new PackageConfig();
//    //设置文件的包名
////        pc.setModuleName("order");
//    pc.setParent("");
//    pc.setEntity("com.gsh.springcloud.common.entity");
//    pc.setMapper("com.gsh.springcloud.order.dao");
//    //设置不同类文件生成的路径
//    HashMap<String, String> pathMap = new HashMap<>();
//    pathMap.put(ConstVal.ENTITY, projectPath + "/src/main/java/com/gsh/springcloud/cloud-common/entity");
//    pathMap.put(ConstVal.MAPPER, projectPath + "/src/main/java/com/czx/igateway/manage/dao/mysql/plus");
//    pc.setPathInfo(pathMap);
//    mpg.setPackageInfo(pc);
//
//    // 策略配置
//    StrategyConfig strategy = new StrategyConfig();
//    strategy.setNaming(NamingStrategy.underline_to_camel);
//    strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//    //需要生成的表
//    strategy.setInclude("orders,user".split(","));
//
//    // 配置模板
//    TemplateConfig templateConfig = new TemplateConfig();
//    //不生成如下类型模板
//    templateConfig.setController(null);
//    templateConfig.setService(null);
//    templateConfig.setServiceImpl(null);
////        templateConfig.setMapper(null);
//
//    mpg.setTemplate(templateConfig);
//
//    //// 如果模板引擎是 freemarker
//    //String templatePath = "/templates/mapper.xml.ftl";
//    //如果模板引擎是 velocity
//    String mapperXmlPath = "/templates/mapper.xml.vm";
//    String mapperJavaPath = "/templates/mapper.java.vm";
//    String entityPath = "/templates/entity.java.vm";
//
//
//    // 自定义配置
//    InjectionConfig cfg = new InjectionConfig() {
//      @Override
//      public void initMap() {
//        // to do nothing
//      }
//    };
//
//    // 自定义输出配置
//    List<FileOutConfig> focList = new ArrayList<>();
//    // 自定义配置优先于默认配置生效
//    focList.add(new FileOutConfig(entityPath) {
//      @Override
//      public String outputFile(TableInfo tableInfo) {
//        // 自定义Entity文件名跟生成路径
//        return projectPath + ENTIT_YPATH + "\\src\\main\\java\\com\\gsh\\springcloud\\common\\entity\\" + tableInfo.getEntityName() + StringPool.DOT_JAVA;
//      }
//    });
//
//    focList.add(new FileOutConfig(mapperXmlPath) {
//      @Override
//      public String outputFile(TableInfo tableInfo) {
//        // 自定义xml 文件名和生成路径
//        return projectPath + "\\service-order\\src\\main\\resources\\mapper\\" + tableInfo.getEntityName() + StringPool.DOT_XML;
//      }
//    });
//
//    focList.add(new FileOutConfig(mapperJavaPath) {
//      @Override
//      public String outputFile(TableInfo tableInfo) {
//        // 自定义Dao类文件名和生成路径
//        return projectPath + EXEC_PROJECT_PATH + "\\src\\main\\java\\com\\gsh\\springcloud\\order\\dao\\" + tableInfo.getEntityName() + "Dao" + StringPool.DOT_JAVA;
//      }
//    });
//
//    cfg.setFileOutConfigList(focList);
//    mpg.setCfg(cfg);
//    mpg.setStrategy(strategy);
//    mpg.execute();
//  }
//
//}
