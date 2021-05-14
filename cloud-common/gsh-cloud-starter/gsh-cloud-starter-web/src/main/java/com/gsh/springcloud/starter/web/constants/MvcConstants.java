package com.gsh.springcloud.starter.web.constants;

/**
 * @author Jason Lee
 */

public interface MvcConstants {

  String[] RESOURCE_LOCATIONS = {
          "classpath:/META-INF/resources/",
          "classpath:/resources/",
          "classpath:/static/",
          "classpath:/public/"
  };

  String URL_PATTERN_WEBJARS = "/webjars/**";

  String WEBJARS_RESOURCE_LOCATION = "classpath:/META-INF/resources/webjars/";

  String SWAGGER_RESOURCE_LOCATION = "classpath:/META-INF/resources/";

  String SWAGGER_UI_URI = "doc.html";

  String SWAGGER_UI_3_URI = "swagger-ui/index.html";

  String URL_PATTERN_ALL_MATCH = "/**";

  String PATTERN_ALL_MATCH = "*";

  String[] ALLOW_METHODS = {
          "GET", "POST", "HEAD", "PUT", "DELETE", "OPTIONS"
  };

  String[] ALLOW_HEADERS = {
          "Accept", "Origin", "X-Requested-With", "Content-Type", "Last-Modified"
  };

  String HEADER_COOKIE = "Set-Cookie";

  String I18N_DICT_FIELD_POSTFIX = "Text";

  String DEFAULT_PARAM_NAME = "lang";

  String HEADER_ACCEPT_LOCALE = "X-Accept-Locale";

  String ACCESS_DENIED_CLASS_NAME = "org.springframework.security.access.AccessDeniedException";

}
