package com.gsh.springcloud.starter.web.mvc;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public class AnnotationServletModelAttributeResolver extends ServletModelAttributeMethodProcessor {

  /**
   * A map caching annotation definitions of command objects (@CommandParameter-to-fieldname
   * mappings)
   */
  private ConcurrentMap<Class<?>, Map<String, String>> definitionsCache = Maps.newConcurrentMap();

  public AnnotationServletModelAttributeResolver(boolean annotationNotRequired) {
    super(annotationNotRequired);
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return true;
  }

  @Override
  protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
    ServletRequest servletRequest = request.getNativeRequest(ServletRequest.class);
    ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
    bind(servletRequest, servletBinder);
  }

  @SuppressWarnings("unchecked")
  public void bind(ServletRequest request, ServletRequestDataBinder binder) {
    Map<String, ?> propertyValues = parsePropertyValues(request, binder);
    MutablePropertyValues mpvs = new MutablePropertyValues(propertyValues);
    MultipartRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartRequest.class);
    if (multipartRequest != null) {
      bindMultipart(multipartRequest.getMultiFileMap(), mpvs);
    }

    // two lines copied from ExtendedServletRequestDataBinder
    String attr = HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;
    mpvs.addPropertyValues((Map<String, String>) request.getAttribute(attr));
    binder.bind(mpvs);
  }

  private Map<String, ?> parsePropertyValues(ServletRequest request,
                                             ServletRequestDataBinder binder) {

    // similar to WebUtils.getParametersStartingWith(..) (prefixes not supported)
    Map<String, Object> params = Maps.newTreeMap();
    Assert.notNull(request, "Request must not be null");
    Enumeration<?> paramNames = request.getParameterNames();
    Map<String, String> parameterMappings = getParameterMappings(binder);
    while (paramNames != null && paramNames.hasMoreElements()) {
      String paramName = (String) paramNames.nextElement();
      String[] values = request.getParameterValues(paramName);

      String fieldName = parameterMappings.get(paramName);
      // no annotation exists, use the default - the param name=field name
      if (fieldName == null) {
        fieldName = paramName;
      }

      if (values == null || values.length == 0) {
        // Do nothing, no values found at all.
      } else if (values.length > 1) {
        params.put(fieldName, values);
      } else {
        params.put(fieldName, values[0]);
      }
    }

    return params;
  }

  /**
   * Gets a mapping between request parameter names and field names. If no annotation is specified,
   * no entry is added
   */
  private Map<String, String> getParameterMappings(ServletRequestDataBinder binder) {
    Class<?> targetClass = binder.getTarget().getClass();
    Map<String, String> map = definitionsCache.get(targetClass);
    if (map == null) {
      List<Field> fields = FieldUtils.getAllFieldsList(targetClass);
      map = Maps.newHashMapWithExpectedSize(fields.size());
      for (Field field : fields) {
        ObjectRequestParameter annotation = field.getAnnotation(ObjectRequestParameter.class);
        if (annotation != null && !annotation.value().isEmpty()) {
          map.put(annotation.value(), field.getName());
        }
      }
      definitionsCache.putIfAbsent(targetClass, map);
      return map;
    } else {
      return map;
    }
  }

  /**
   * Copied from WebDataBinder.
   */
  protected void bindMultipart(Map<String, List<MultipartFile>> multipartFiles,
                               MutablePropertyValues mpvs) {
    for (Map.Entry<String, List<MultipartFile>> entry : multipartFiles.entrySet()) {
      String key = entry.getKey();
      List<MultipartFile> values = entry.getValue();
      if (values.size() == 1) {
        MultipartFile value = values.get(0);
        if (!value.isEmpty()) {
          mpvs.add(key, value);
        }
      } else {
        mpvs.add(key, values);
      }
    }
  }
}
