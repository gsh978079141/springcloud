package com.gsh.springcloud.starter.web.support;

import com.google.common.collect.ImmutableSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Set;

/**
 * @author gsh
 */
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE - 8)
public class HttpRequestPrinterFilter extends OncePerRequestFilter {

  public static final String SPLIT_STRING_M = "=";
  public static final String SPLIT_STRING_DOT = ", ";
  private static final Set<String> EXCLUDED_SUFFIX_SET =
          ImmutableSet.of(".js", ".ico", ".html", ".png", ".jpg", ".woff2");

  public static String getRequestParams(HttpServletRequest request) {
    StringBuilder sb = new StringBuilder();
    Enumeration<String> enu = request.getParameterNames();
    //获取请求参数
    while (enu.hasMoreElements()) {
      String name = enu.nextElement();
      sb.append(name + SPLIT_STRING_M).append(request.getParameter(name));
      if (enu.hasMoreElements()) {
        sb.append(SPLIT_STRING_DOT);
      }
    }
    return sb.toString();
  }


  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    // 访问静态资源或页面的请求不拦截
    String uri = request.getRequestURI();
    if (uri.startsWith("/webjars/")) {
      return true;
    }
    if (EXCLUDED_SUFFIX_SET.stream().anyMatch(uri::endsWith)) {
      return true;
    }
    return false;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    Long t1 = System.currentTimeMillis();

    // request 的inputStream和response 的outputStream默认情况下是只能读一次， 不可重复读；
    ContentCachingRequestWrapper wrapperRequest = new ContentCachingRequestWrapper(request);
    ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper(response);

    try {
      if (log.isDebugEnabled()) {
        log.debug("HTTP_REQUEST_PRINTER filter begin, [{}][{}]",
                wrapperRequest.getMethod(), wrapperRequest.getRequestURI());
        log.debug("HTTP_REQUEST_PRINTER request data, params:[{}], body:[{}]",
                getRequestParams(wrapperRequest),
                getRequestBody(wrapperRequest));

      }
      // 将请求转发给过滤器链上下一个对象, 这里的下一个指的是下一个filter，如果没有filter那就是请求的资源。
      filterChain.doFilter(wrapperRequest, wrapperResponse);
      Long t2 = System.currentTimeMillis();
      if (log.isDebugEnabled()) {
        log.debug("HTTP_REQUEST_PRINTER filter end, cost [{}] ms, response:[{}][{}]",
                t2 - t1,
                wrapperResponse.getStatus(),
                getResponseBody(wrapperResponse));
      }
    } finally {
      wrapperResponse.copyBodyToResponse();
    }


  }

  private String getRequestBody(ContentCachingRequestWrapper request) {
    ContentCachingRequestWrapper wrapper = WebUtils
            .getNativeRequest(request, ContentCachingRequestWrapper.class);
    if (wrapper == null) {
      return "";
    }
    return getBodyStr(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
  }


  private String getResponseBody(ContentCachingResponseWrapper response) {
    ContentCachingResponseWrapper wrapper = WebUtils
            .getNativeResponse(response, ContentCachingResponseWrapper.class);
    if (wrapper == null) {
      return "";
    }
    return getBodyStr(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
  }

  private String getBodyStr(byte[] buf, String encoding) {
    if (buf.length == 0) {
      return "";
    }
    String payload;
    try {
      payload = new String(buf, 0, buf.length, encoding);
    } catch (UnsupportedEncodingException e) {
      payload = "[unknown]";
    }
    return payload.replaceAll("\\n", "");
  }


}
