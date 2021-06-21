package com.gsh.springcloud.starter.mq.mqtt;

import cn.hutool.core.util.ClassUtil;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * @author gsh
 */
@Component
public class EventRegister {
  private EventRegister() {

  }

  private static EventEmitter emitter = new EventEmitter();

  public static void emit(String event, List<Object> params) throws Exception {
    emitter.emit(event, params);
  }

  @SuppressWarnings("ALL")
  public static void regist(String packageName, Class methodAnnotation, Class classAnnotation) throws Exception {
    packageName = "com.gsh.springcloud";
    // packageName例如：com.gsh.springcloud.starter.mq.mqtt
//        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "/"));
//        if (scannedUrl == null) {
//            throw new IllegalArgumentException();
//        }
//        File scannedFile = new File(scannedUrl.getFile());
//        List<Class<?>> classes = ClassUtil.getClass(scannedFile, packageName);
    Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(packageName, classAnnotation);
    for (Class<?> cls : classes) {
      if (cls.isInterface()) {
        continue;
      }
      Annotation classAnnotationObj = cls.getAnnotation(classAnnotation);
      if (classAnnotationObj != null) {
        Object handler = cls.newInstance();
        Method[] methods = cls.getMethods();

        for (Method method : methods) {
          Annotation methodAnnoationObj = method.getAnnotation(methodAnnotation);

          if (methodAnnoationObj != null) {
            Method valueMethod = methodAnnoationObj.annotationType().getDeclaredMethod("value");
            String value = (String) valueMethod.invoke(methodAnnoationObj);

            if (value != null && !"".equals(value)) {
              emitter.on(value, params -> {
                method.invoke(handler, params);
              });
            }
          }
        }
      }
    }
  }
}
