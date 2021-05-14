package com.gsh.springcloud.account.config;

import com.gsh.springcloud.common.consts.I18nConstants;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Objects;

/**
 * @author gsh
 */
public class LocaleResolver extends AcceptHeaderLocaleResolver {

  @Override
  public Locale resolveLocale(HttpServletRequest request) {
    Locale result = null;
    // http header first
    String headerLocale = request.getHeader(I18nConstants.HEADER_ACCEPT_LOCALE);
    if (!StringUtils.isEmpty(headerLocale)) {
      result = StringUtils.parseLocaleString(headerLocale);
    } else if (Objects.isNull(result)) {
      // check request parameter
      String paraLocale = request.getParameter(I18nConstants.DEFAULT_PARAM_NAME);
      if (!StringUtils.isEmpty(paraLocale)) {
        result = StringUtils.parseLocaleString(paraLocale);
      }
    }
    return Objects.isNull(result) ? Locale.SIMPLIFIED_CHINESE : result;
  }

  @Override
  public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
    this.setDefaultLocale(locale);
  }

}