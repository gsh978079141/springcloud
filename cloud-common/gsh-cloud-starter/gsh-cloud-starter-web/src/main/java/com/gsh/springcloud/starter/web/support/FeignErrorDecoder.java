package com.gsh.springcloud.starter.web.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gsh.springcloud.common.exception.FeignRequestException;
import com.gsh.springcloud.common.exception.OAuthAccessDeniedException;
import com.gsh.springcloud.starter.web.exception.ServiceException;
import feign.Request.HttpMethod;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * Feign调用HTTP返回响应码错误时候，定制错误的解码
 *
 * @author gsh
 */
@Slf4j
@Configuration
public class FeignErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String methodKey, Response response) {
    String bodyString = response.body().toString();
    log.error("Feign调用异常，status:[{}], 异常methodKey:{}, response body:{}",
            response.status(), methodKey, bodyString);
    switch (HttpStatus.resolve(response.status())) {
      case UNAUTHORIZED:
        return new RetryableException(HttpStatus.UNAUTHORIZED.value(), "疑似access_token过期，即将进行重试",
                HttpMethod.GET,
                new Date(), response.request());
      case FORBIDDEN:
        String message = String.format("status %s reading %s", response.status(), methodKey);
        return new OAuthAccessDeniedException(message);
      default:
        try {
          if (StringUtils.isBlank(bodyString)) {
            return new FeignRequestException();
          }
          JSONObject errorJsonObject = JSON.parseObject(bodyString);
          String code = errorJsonObject.getString("code");
          if (StringUtils.isBlank(code)) {
            return new FeignRequestException();
          }
          String msg = errorJsonObject.getString("message");
          return new ServiceException(code, response.status(), msg);
        } catch (Exception e) {
          e.printStackTrace();
          log.error("call feign client error", e);
          FeignRequestException ex = new FeignRequestException();
          ex.setDetail(e.fillInStackTrace());
          return ex;
        }
    }
  }
}
