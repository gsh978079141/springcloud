package com.gsh.springcloud.starter.web.support;

import com.alibaba.fastjson.JSON;
import com.gsh.springcloud.common.exception.AbstractBaseException;
import com.gsh.springcloud.common.exception.CommonExceptionEnum;
import com.gsh.springcloud.common.vo.JsonResult;
import com.gsh.springcloud.starter.web.exception.ServiceException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

/**
 * @author gsh
 */
@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler {

  @Value("${sys.error-handler.return-ok-when-error:false}")
  private boolean returnOkWhenError;

  @Resource
  private MessageSource messageSource;

  public GeneralExceptionHandler() {

  }

  @ExceptionHandler({ServiceException.class})
  public ResponseEntity<?> handleException(HttpServletRequest request, ServiceException ex) {
    log.error("ServiceException, url:[{}], err:{}", request.getRequestURI(), ex.getMessage());
    return ResponseEntity.status(returnOkWhenError ? 200 : ex.getStatus())
            .body(JsonResult.failure(ex.getCode(), ex.getMessage()));
  }


  @ExceptionHandler({BindException.class})
  public ResponseEntity<?> handleException(HttpServletRequest request, BindException ex) {
    List<FieldError> fieldErrors = ex.getFieldErrors();
    log.error("BindException, url:[{}], errors:{}", request.getRequestURI(), JSON.toJSONString(fieldErrors));
    FieldError fieldError = fieldErrors.get(0);
    String errMsg = fieldError.getDefaultMessage();
    if ("typeMismatch".equals(fieldError.getCode())) {
      errMsg = String.format("字段[%s]类型错误", fieldError.getField());
    }
    return ResponseEntity.status(returnOkWhenError ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
            .body(JsonResult.failure(CommonExceptionEnum.DATA_VALIDATION_ERROR.name(), errMsg));
  }


  @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
  public ResponseEntity<?> handleException(HttpServletRequest request, HttpMediaTypeNotSupportedException ex) {
    log.error("HttpMediaTypeNotSupportedException, url:[{}]", request.getRequestURI(), ex);
    return ResponseEntity.status(returnOkWhenError ? HttpStatus.OK : HttpStatus.UNSUPPORTED_MEDIA_TYPE)
            .body(JsonResult.failure(CommonExceptionEnum.UNSUPPORTED_MEDIA_TYPE.getCode(), ex.getMessage()));

  }

  @ExceptionHandler({MethodArgumentTypeMismatchException.class})
  public ResponseEntity<?> handleException(HttpServletRequest request, MethodArgumentTypeMismatchException ex) {
    log.error("MethodArgumentTypeMismatchException, url:[{}]", request.getRequestURI(), ex);
    return ResponseEntity.status(returnOkWhenError ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
            .body(JsonResult.failure(ex.getErrorCode(), ex.getMessage()));
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<?> handleException(HttpServletRequest request, MethodArgumentNotValidException ex) {
    log.error("MethodArgumentNotValidException, url:[{}],  err:{}", request.getRequestURI(), ex.getMessage());
    List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
    String errMsg = CollectionUtils.isEmpty(objectErrors) ? ex.getMessage() : objectErrors.get(0).getDefaultMessage();
    return ResponseEntity.status(returnOkWhenError ? 200 : CommonExceptionEnum.DATA_VALIDATION_ERROR.getStatus())
            .body(JsonResult.failure((CommonExceptionEnum.DATA_VALIDATION_ERROR.getCode()), errMsg));
  }

  @ExceptionHandler({MissingServletRequestPartException.class})
  public ResponseEntity<?> handleException(HttpServletRequest request, MissingServletRequestPartException ex) {
    log.error("MissingServletRequestPartException, url:[{}],  err:{}", request.getRequestURI(), ex.getMessage());
    return ResponseEntity.status(returnOkWhenError ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
            .body(JsonResult.failure(CommonExceptionEnum.DATA_VALIDATION_ERROR.getCode(), ex.getMessage()));

  }


  /**
   * 缺少必须的请求参数 处理
   * example: 查询请求 /students?schoolId=1， schoolId为必填参数， 请求缺少参数 schoolId 时会抛出此异常
   */
  @ExceptionHandler({MissingServletRequestParameterException.class})
  public ResponseEntity<?> handleException(HttpServletRequest request, MissingServletRequestParameterException ex) {
    log.info("MissingServletRequestParameterException, url:[{}], err:{}", request.getRequestURI(), ex.getMessage());
    String message = String.format("请求缺少必须参数: %s(%s)", ex.getParameterName(), ex.getParameterType());
    return ResponseEntity.status(returnOkWhenError ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
            .body(JsonResult.failure((CommonExceptionEnum.DATA_VALIDATION_ERROR.name()), message));
  }


  @ExceptionHandler({HttpMessageNotReadableException.class})
  public ResponseEntity<?> handleException(HttpServletRequest request, HttpMessageNotReadableException ex) {
    log.error("HttpMessageNotReadableException, url:[{}], err:{}", request.getRequestURI(), ex.getMessage());
    String message = "Http消息不可读";
    JsonResult<Object> result = JsonResult.failure(CommonExceptionEnum.DATA_VALIDATION_ERROR.getCode(),
            message);
    return ResponseEntity.status(returnOkWhenError ? 200 : CommonExceptionEnum.DATA_VALIDATION_ERROR.getStatus())
            .body(result);
  }


  @ExceptionHandler({AbstractBaseException.class})
  public ResponseEntity<?> handleException(HttpServletRequest request, AbstractBaseException ex) {
    String message = ex.getMessage();
    log.error("AbstractBaseException:{}", message, ex);
    if (ex.isNeedTranslation()) {
      boolean fromFeignClient = this.requestFromFeign(request);
      message = this.translateMessage(fromFeignClient, ex.getCode(), ex.getArguments());
    }
    String code = ex.getCode();
    return ResponseEntity.status(returnOkWhenError ? 200 : this.extractHttpStatus(ex.getStatus()))
            .body(JsonResult.failure(code, message));
  }

  @ExceptionHandler({RetryableException.class})
  public ResponseEntity<?> handleException(HttpServletRequest request, RetryableException ex) {
    log.warn("RetryableException, url:[{}], err:{}", request.getRequestURI(), ex.getMessage());
    boolean fromFeignClient = requestFromFeign(request);
    String message = this.translateMessage(fromFeignClient, CommonExceptionEnum.SERVICE_UNAVAILABLE.getCode());
    JsonResult<Object> result = JsonResult.failure(CommonExceptionEnum.SERVICE_UNAVAILABLE.getCode(),
            message);
    return ResponseEntity.status(returnOkWhenError ? 200 : CommonExceptionEnum.SERVICE_UNAVAILABLE.getStatus())
            .body(result);
  }


  @ExceptionHandler({Exception.class})
  public ResponseEntity<?> handleException(HttpServletRequest request, Exception ex) {
    log.error("UnExcepted Exception, url:[{}]", request.getRequestURI(), ex);
    boolean fromFeignClient = this.requestFromFeign(request);
    PropertyDescriptor errorCodePd = BeanUtils.getPropertyDescriptor(ex.getClass(), "errorCode");
    PropertyDescriptor paramsPd = BeanUtils.getPropertyDescriptor(ex.getClass(), "params");
    String errCode = CommonExceptionEnum.INTERNAL_SERVER_ERROR.getCode();
    String errMsg = CommonExceptionEnum.INTERNAL_SERVER_ERROR.getCode();
    try {
      if (Objects.nonNull(errorCodePd)) {
        String code = (String) errorCodePd.getReadMethod().invoke(ex);
        if (StringUtils.isNotBlank(code)) {
          errCode = code;
        }
        if (Objects.isNull(paramsPd)) {
          errMsg = this.translateMessage(fromFeignClient, code);
        } else {
          Object params = paramsPd.getReadMethod().invoke(ex);
          if (!Objects.isNull(params) && params instanceof Object[]) {
            errMsg = this.translateMessage(fromFeignClient, code,
                    Arrays.asList((Object[]) params));
          } else {
            errMsg = this.translateMessage(fromFeignClient, code);
          }
        }
      } else {
        errMsg = this
                .translateMessage(fromFeignClient, CommonExceptionEnum.INTERNAL_SERVER_ERROR.getCode());
      }
    } catch (Exception var9) {
      log.warn("exception happening when resolving exception", var9);
    }
    return ResponseEntity.status(returnOkWhenError ? 200 : CommonExceptionEnum.INTERNAL_SERVER_ERROR.getStatus())
            .body(JsonResult.failure(errCode, errMsg));
  }


  private int extractHttpStatus(int status) {
    String sts = status + "";
    sts = sts.substring(0, 3);
    return Integer.parseInt(sts);
  }

  private boolean requestFromFeign(HttpServletRequest request) {
    Enumeration<String> headers = request.getHeaders("User-Agent-Feign");
    do {
      if (!headers.hasMoreElements()) {
        return false;
      }
    } while (!"Java/".equals(headers.nextElement()));
    return false;
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
    }
    try {
      return this.messageSource
              .getMessage(code, null, LocaleContextHolder.getLocale());
    } catch (Exception ex) {
      log.warn("exception happened when translating error message", ex);
      return code;
    }
  }
}
