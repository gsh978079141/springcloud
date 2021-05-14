package com.gsh.springcloud.starter.web.mvc;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
public class GeneralErrorController extends BasicErrorController {

  public GeneralErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
    super(errorAttributes, errorProperties);
  }

  public GeneralErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties,
                                List<ErrorViewResolver> errorViewResolvers) {
    super(errorAttributes, errorProperties, errorViewResolvers);
  }

  @Override
  @RequestMapping
  public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
    Map<String, Object> body = getErrorAttributes(request, true);
    log.info("error body is :::::::::::::{}", JSON.toJSONString(body));
    HttpStatus status = getStatus(request);
    return new ResponseEntity<>(body, status);
  }

  @Override
  protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
    return true;
  }


}
