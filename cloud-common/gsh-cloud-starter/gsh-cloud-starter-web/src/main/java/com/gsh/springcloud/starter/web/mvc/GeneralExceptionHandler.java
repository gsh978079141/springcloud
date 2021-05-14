package com.gsh.springcloud.starter.web.mvc;

import com.gsh.springcloud.common.exception.*;
import com.gsh.springcloud.common.utils.ExceptionUtil;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.util.*;

@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler {

  @Resource
  MessageSource messageSource;

  @Value("${spring.application.name}")
  private String serviceName;

  public GeneralExceptionHandler() {
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<?> handleException(HttpServletRequest request, Exception ex) {
    log.error("There is an exception happening when sending request to url >>>>>>>>>>>> " + request
            .getRequestURL(), ex);
    boolean fromFeignClient = this.requestFromFeign(request);
    if (AbstractBaseException.class.isAssignableFrom(ex.getClass())) {
      return this.handleBaseException(fromFeignClient, (AbstractBaseException) ex);
    } else if (MethodArgumentNotValidException.class.isAssignableFrom(ex.getClass())) {
      return this.handleValidationException(fromFeignClient, (MethodArgumentNotValidException) ex);
    } else if (RetryableException.class.isAssignableFrom(ex.getClass())) {
      return this.handleRetryableException(fromFeignClient, (RetryableException) ex);
    } else {
      return ex.getClass().getName()
              .equals("org.springframework.security.access.AccessDeniedException") ? this
              .handleBaseException(fromFeignClient,
                      new OAuthAccessDeniedException(ExceptionUtil.getStackTraceInfo(ex)))
              : this.handleOtherExceptions(fromFeignClient, ex);
    }
  }

  private ResponseEntity<AbstractBaseException> handleBaseException(boolean fromFeignClient,
                                                                    AbstractBaseException ex) {
    if (ex.isNeedTranslation()) {
      ex.setMessage(this.translateMessage(fromFeignClient, ex.getCode(), ex.getArguments()));
    }

    ex.setDetail(ExceptionUtil.getStackTraceInfo(ex));
    return ResponseEntity.status(this.extractHttpStatus(ex.getStatus())).body(ex);
  }

  private ResponseEntity<?> handleOtherExceptions(boolean fromFeignClient, Exception ex) {
    PropertyDescriptor errorCodePd = BeanUtils.getPropertyDescriptor(ex.getClass(), "errorCode");
    PropertyDescriptor paramsPd = BeanUtils.getPropertyDescriptor(ex.getClass(), "params");
    String message = "";
    UnexpectedException ue = new UnexpectedException();

    try {
      if (Objects.nonNull(errorCodePd)) {
        String code = (String) errorCodePd.getReadMethod().invoke(ex);
        if (StringUtils.isNotBlank(code)) {
          ue.setCode(code);
        }

        if (Objects.isNull(paramsPd)) {
          message = this.translateMessage(fromFeignClient, code);
        } else {
          Object params = paramsPd.getReadMethod().invoke(ex);
          if (!Objects.isNull(params) && params instanceof Object[]) {
            message = this.translateMessage(fromFeignClient, code,
                    Arrays.asList((Object[]) ((Object[]) params)));
          } else {
            message = this.translateMessage(fromFeignClient, code);
          }
        }
      } else {
        message = this
                .translateMessage(fromFeignClient, CommonErrorEnum.INTERNAL_SERVER_ERROR.getCode());
      }
    } catch (Exception var9) {
      log.warn("exception happening when resolving exception", var9);
    }

    ue.setMessage(StringUtils.isBlank(message) ? ue.getCode() : message);
    ue.setDetail(ExceptionUtil.getStackTraceInfo(ex));
    return ResponseEntity.status(ue.getStatus()).body(ue);
  }

  private ResponseEntity<ServiceUnavailableException> handleRetryableException(
          boolean fromFeignClient, RetryableException ex) {
    ServiceUnavailableException sue = new ServiceUnavailableException();
    sue.setDetail(ExceptionUtil.getStackTraceInfo(ex));
    sue.setMessage(this.translateMessage(fromFeignClient, sue.getCode()));
    return ResponseEntity.status(sue.getStatus()).body(sue);
  }

  private ResponseEntity<FormValidationException> handleValidationException(boolean fromFeignClient,
                                                                            MethodArgumentNotValidException ex) {
    BindingResult br = ex.getBindingResult();
    List<ObjectError> errors = br.getAllErrors();
    Map<String, String> errorMap = new HashMap();
    Iterator var6 = errors.iterator();

    while (var6.hasNext()) {
      ObjectError oe = (ObjectError) var6.next();
      if (!Objects.isNull(oe.getArguments()) && oe.getArguments().length != 0) {
        DefaultMessageSourceResolvable dmsr = (DefaultMessageSourceResolvable) oe.getArguments()[0];
        errorMap.put(dmsr.getCode(), this.messageSource
                .getMessage(oe.getDefaultMessage(), (Object[]) null, oe.getDefaultMessage(),
                        LocaleContextHolder.getLocale()));
      }
    }

    FormValidationException fve = new FormValidationException();
    fve.setMessage(this.translateMessage(fromFeignClient, fve.getCode()));
    fve.setData(errorMap);
    fve.setDetail(ExceptionUtil.getStackTraceInfo(ex));
    return ResponseEntity.status(fve.getStatus()).body(fve);
  }

  private int extractHttpStatus(int status) {
    String sts = status + "";
    sts = sts.substring(0, 3);
    return Integer.valueOf(sts);
  }

  private boolean requestFromFeign(HttpServletRequest request) {
    Enumeration headers = request.getHeaders("User-Agent-Feign");

    do {
      if (!headers.hasMoreElements()) {
        return false;
      }
    } while (!"Java/".equals(headers.nextElement()));

    return true;
  }

  private String translateMessage(boolean fromFeignClient, String code, Object... arguments) {
    String msg = code;
    if (fromFeignClient) {
      return code;
    } else {
      try {
        msg = this.messageSource.getMessage(code, arguments, LocaleContextHolder.getLocale());
      } catch (Exception ex) {
        log.warn("exception happened when translating error message", ex);
      }

      return msg;
    }
  }

  private String translateMessage(boolean fromFeignClient, String code) {
    if (fromFeignClient) {
      return code;
    } else {
      try {
        return this.messageSource
                .getMessage(code, (Object[]) null, LocaleContextHolder.getLocale());
      } catch (Exception ex) {
        log.warn("exception happened when translating error message", ex);
        return code;
      }
    }
  }
}
