//package com.gsh.springcloud.order.config.swagger;
//
//
//import com.google.common.collect.Lists;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import springfox.documentation.service.Contact;
//
//import java.util.List;
//
///**
// * @author gsh
// */
//@ConfigurationProperties("swagger")
//@Data
//public class SwaggerProperties {
//
//  private String host;
//
//  private ApiInfo apiInfo = new ApiInfo();
//
//  private List<String> ignoreGlobalParameters = Lists.newArrayList();
//
//  private List<GlobalParameter> globalParameters = Lists.newArrayList();
//
//
//  @Setter
//  @Getter
//  public static class ApiInfo {
//
//    private String title;
//
//    private String description;
//
//    private Contact contact;
//
//    private String version;
//  }
//
//
//  @Setter
//  @Getter
//  public static class GlobalParameter {
//
//    private String name;
//
//    private String description;
//
//    private String defaultValue;
//
//    private String paramType;
//
//    private String dataType;
//
//    private boolean required;
//
//  }
//}
