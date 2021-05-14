package com.gsh.springcloud.account.util;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class UserAttributesUtil {

  public static String getValueFromAttrs(Map<String, List<String>> attributesMap, String attrName) {
    if (MapUtils.isEmpty(attributesMap) || StringUtils.isBlank(attrName)) {
      return StringUtils.EMPTY;
    }
    List<String> values = attributesMap.get(attrName);
    if (CollectionUtils.isEmpty(values)) {
      return StringUtils.EMPTY;
    }
    return values.get(0);

  }

  public static List<String> listValuesFromAttrs(Map<String, List<String>> attributesMap,
                                                 String attrName) {
    if (MapUtils.isEmpty(attributesMap) || StringUtils.isBlank(attrName)) {
      return Lists.newArrayList();
    }
    List<String> values = attributesMap.get(attrName);
    return CollectionUtils.isEmpty(values) ? Lists.newArrayList() : values;
  }

  public static String getRolePrefix(Long schoolId) {
    return "[".concat(schoolId.toString()).concat("]");
  }


}
