package com.gsh.springcloud.starter.web.support;

import com.alibaba.fastjson.JSON;
import com.gsh.springcloud.common.vo.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author gsh
 */
@Slf4j
public class GeneralErrorController extends BasicErrorController {


  public GeneralErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties, List<ErrorViewResolver> errorViewResolvers) {
    super(errorAttributes, errorProperties, errorViewResolvers);
  }

  @Override
  @RequestMapping
  public ResponseEntity error(HttpServletRequest request) {
    Map<String, Object> body = getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE));
    log.info("error body is :::::::::::::{}", JSON.toJSONString(body));
    String message = body.getOrDefault("error", "unknown error!").toString();
    JsonResult result = JsonResult.failure("INTERNAL_SERVER_ERROR", message);
    HttpStatus status = getStatus(request);
    return ResponseEntity.status(status).body(result);
  }

  @Override
  protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
    return true;
  }


}
